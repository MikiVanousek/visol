package nl.utwente.di.visol1.type_adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class TimestampAdapter extends XmlAdapter<String, Timestamp> {
	public static final TimestampAdapter INSTANCE = new TimestampAdapter();
	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX");

	@Override
	public String marshal(Timestamp timestamp) {
		return timestamp == null ? null : formatter.format(timestamp.toLocalDateTime().atOffset(ZoneOffset.UTC));
	}

	@Override
	public Timestamp unmarshal(String value) {
		return value == null ? null : Timestamp.valueOf(formatter.parse(value, LocalDateTime::from));
	}
}
