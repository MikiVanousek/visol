package nl.utwente.di.visol1.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.stream.Stream;

import nl.utwente.di.visol1.util.Configuration;

public abstract class GenericDao {
	public static final Timestamp MIN_TIME = new Timestamp(0);
	public static final Timestamp MAX_TIME = Timestamp.valueOf("3000-1-1 23:05:06");
	private static Connection connection;

	protected GenericDao() {}

	public static void truncateAllTables() {
		Stream.of("port", "berth", "terminal", "vessel", "schedule", "employee")
			.map(table -> "TRUNCATE TABLE " + table + " RESTART IDENTITY CASCADE")
			.forEach(s -> {
				try (Update ignored = Update.simple(s)) {
				} catch (SQLException exception) {
					exception.printStackTrace();
				}
			});
	}

	protected static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("org.postgresql.Driver");
			} catch (ClassNotFoundException exception) {
				throw new RuntimeException("Could not find the PostgreSQL driver", exception);
			}

			connection = DriverManager.getConnection(
				String.format(
					"jdbc:postgresql://%s/%s?currentSchema=%s",
					Configuration.Database.URL.get(),
					Configuration.Database.NAME.get(),
					Configuration.Database.SCHEMA.get()
				),
				Configuration.Database.USERNAME.get(),
				Configuration.Database.PASSWORD.get()
			);
		}
		return connection;
	}

	@FunctionalInterface
	interface StatementData {
		void inject(PreparedStatement preparedStatement) throws SQLException;
	}

	protected static class Query implements AutoCloseable {
		private final Connection connection;
		private final Statement statement;
		private final ResultSet resultSet;

		private Query(Connection connection, Statement statement, ResultSet resultSet) {
			this.connection = connection;
			this.statement = statement;
			this.resultSet = resultSet;
		}

		protected static Query simple(String query) throws SQLException {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);
			return new Query(connection, statement, resultSet);
		}

		protected static Query prepared(String query, StatementData statementData) throws SQLException {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statementData.inject(statement);
			ResultSet resultSet = statement.executeQuery();
			return new Query(connection, statement, resultSet);
		}

		public ResultSet getResultSet() {
			return resultSet;
		}

		@Override
		public void close() throws SQLException {
			resultSet.close();
			statement.close();
			connection.close();
		}
	}

	protected static class Update implements AutoCloseable {
		private final Connection connection;
		private final Statement statement;
		private final int rowsChanged;

		private Update(Connection connection, Statement statement, int rowsChanged) {
			this.connection = connection;
			this.statement = statement;
			this.rowsChanged = rowsChanged;
		}

		protected static Update simple(String query) throws SQLException {
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			int rowsChanged = statement.executeUpdate(query);
			return new Update(connection, statement, rowsChanged);
		}

		protected static Update prepared(String query, StatementData statementData) throws SQLException {
			Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statementData.inject(statement);
			int rowsChanged = statement.executeUpdate();
			return new Update(connection, statement, rowsChanged);
		}

		public int getRowsChanged() {
			return rowsChanged;
		}

		@Override
		public void close() throws SQLException {
			statement.close();
			connection.close();
		}
	}
}
