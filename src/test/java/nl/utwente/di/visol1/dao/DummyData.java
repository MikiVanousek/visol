package nl.utwente.di.visol1.dao;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Port;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.models.Terminal;
import nl.utwente.di.visol1.models.Vessel;
import nl.utwente.di.visol1.models.VesselChange;
import nl.utwente.di.visol1.util.Configuration;

public class DummyData {

	public static final List<Port> PORTS = Arrays.asList(
		new Port(1, "Amsterdam"),
		new Port(2, "New York")
	);
	public static final List<Terminal> TERMINALS = Arrays.asList(
		new Terminal(1, 1, "West"),
		new Terminal(2, 1, "East"),
		new Terminal(3, 2, "North"),
		new Terminal(4, 2, "South")
	);
	public static final List<Berth> BERTHS = Arrays.asList(
		new Berth(1, 1, Time.valueOf("08:30:00"), Time.valueOf("22:00:00"), 20, 10, 5, 5),
		new Berth(2, 1, Time.valueOf("09:15:00"), Time.valueOf("21:15:00"), 15, 50, 6, 4),
		new Berth(5, 1, Time.valueOf("07:45:00"), Time.valueOf("22:15:00"), 18, 14, 5, 5),
		new Berth(4, 1, Time.valueOf("05:15:00"), Time.valueOf("20:15:00"), 17, 29, 4, 6),
		new Berth(5, 1, Time.valueOf("06:15:00"), Time.valueOf("19:50:00"), 16, 18, 5, 4),
		new Berth(6, 4, Time.valueOf("09:50:00"), Time.valueOf("21:45:00"), 14, 14, 5, 5),
		new Berth(7, 4, Time.valueOf("10:15:00"), Time.valueOf("22:15:00"), 12, 15, 5, 7),
		new Berth(8, 4, Time.valueOf("7:45:00"), Time.valueOf("23:00:00"), 18, 8, 4, 4)
	);
	//public Vessel(int id, String name, Timestamp arrival, Timestamp deadline, int containers, double costPerHour, int destination, int length, int width, int depth) {
	public static final List<Vessel> VESSELS = Arrays.asList(
		new Vessel(1, "Dumbarton Castle", Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-24 01:00:00"), 25, 200, 1, 8, 2, 2),
		new Vessel(2, "The Llandudno", Timestamp.valueOf("2022-06-23 04:30:00"), Timestamp.valueOf("2022-06-23 12:30:00"), 22, 430, 1, 7, 4, 3),
		new Vessel(3, "Killeney", Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-23 23:30:00"), 35, 212, 1, 4, 4, 2),
		new Vessel(4, "The Erne", Timestamp.valueOf("2022-06-23 18:15:00"), Timestamp.valueOf("2022-06-24 12:00:00"), 12, 421, 1, 3, 2, 1),
		new Vessel(5, "Bonito", Timestamp.valueOf("2022-06-23 12:00:00"), Timestamp.valueOf("2022-06-23 06:30:00"), 43, 522, 1, 2, 3, 3),
		new Vessel(6, "Blaze", Timestamp.valueOf("2022-06-24 12:15:00"), Timestamp.valueOf("2022-06-24 23:30:00"), 15, 243, 1, 4, 3, 2),
		new Vessel(7, "Acheron", Timestamp.valueOf("2022-06-24 12:15:00"), Timestamp.valueOf("2022-06-23 18:30:00"), 86, 522, 1, 3, 2, 3),
		new Vessel(8, "Bere Castle", Timestamp.valueOf("2022-06-24 18:00:00"), Timestamp.valueOf("2022-06-24 10:00:00"), 56, 364, 1, 10, 4, 3),
		new Vessel(9, "Llandaff", Timestamp.valueOf("2022-06-23 12:30:00"), Timestamp.valueOf("2022-06-25 23:00:00"), 12, 14, 1, 2, 5, 3),
		new Vessel(10, "Tang", Timestamp.valueOf("2022-06-23 17:05:06"), Timestamp.valueOf("2022-06-24 23:15:00"), 12, 412, 1, 1, 2, 2),

		new Vessel(11, "Jahde", Timestamp.valueOf("2022-06-23 12:05:06"), Timestamp.valueOf("2022-06-23 23:05:06"), 24, 12, 3, 25, 25, 25),
		new Vessel(12, "Landudno", Timestamp.valueOf("2022-06-23 13:05:06"), Timestamp.valueOf("2022-06-23 23:05:06"), 13, 12, 3, 36, 25, 26),
		new Vessel(13, "Picton", Timestamp.valueOf("2022-06-24 12:05:06"), Timestamp.valueOf("2022-06-23 23:05:06"), 13, 3.2, 4, 38, 25, 26),
		new Vessel(14, "Etna", Timestamp.valueOf("2022-06-24 13:05:06"), Timestamp.valueOf("2022-06-23 23:05:06"), 45, 0.5, 4, 24, 24, 36),
		new Vessel(15, "Wem", Timestamp.valueOf("2022-06-23 14:05:06"), Timestamp.valueOf("2022-06-23 23:05:06"), 234, 0.01, 4, 14, 24, 64),
		new Vessel(16, "The Cyane", Timestamp.valueOf("2022-06-23 16:05:06"), Timestamp.valueOf("2022-06-23 23:05:06"), 5, 1.2, 4, 17, 12, 53)
	);
	public static final List<Schedule> SCHEDULES = Arrays.asList(
		new Schedule(1, 1, false, Timestamp.valueOf("2022-06-23 13:30:00"), Timestamp.valueOf("2022-06-23 15:30:00")),
		new Schedule(2, 2   , true, Timestamp.valueOf("2022-06-23 06:00:00"), Timestamp.valueOf("2022-06-23 07:30:00")),
		new Schedule(3, 2, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-23 15:30:00")),
		new Schedule(4, 2, false, Timestamp.valueOf("2022-06-23 19:00:00"), Timestamp.valueOf("2022-06-23 21:00:00")),
		new Schedule(5, 3, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-23 14:00:00")),
		new Schedule(6, 3, true, Timestamp.valueOf("2022-06-24 8:00:00"), Timestamp.valueOf("2022-06-24 12:00:00")),
		new Schedule(7, 4, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-24 17:00:00")),
		new Schedule(8, 4, false, Timestamp.valueOf("2022-06-23 19:00:00"), Timestamp.valueOf("2022-06-23 20:00:00")),

		new Schedule(9, 1, true, Timestamp.valueOf("2022-06-23 14:00:00"), Timestamp.valueOf("2022-06-23 17:00:00")),
		new Schedule(10, 3, true, Timestamp.valueOf("2022-06-23 13:30:00"), Timestamp.valueOf("2022-06-23 16:00:00")),

		new Schedule(11, 6, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2000-01-09 23:05:06")),
		new Schedule(12, 6, true, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
		new Schedule(13, 7, false, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
		new Schedule(14, 7, true, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
		new Schedule(15, 8, false, Timestamp.valueOf("2000-01-11 13:05:06"), Timestamp.valueOf("2000-01-11 23:05:06")),
		new Schedule(16, 8, false, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06"))
	);
	public static final List<ScheduleChange> SCHEDULE_CHANGES = Arrays.asList(
		new ScheduleChange(1, Timestamp.valueOf("2000-01-08 13:05:06"), "", 	"{\"vessel\":1,\"manual\":\"false\",\"berth\":1,\"start\":\"2022-05-10T16:32:35Z\",\"expected_end\":\"2022-05-11T16:32:35Z\"}", "testing1"),
		new ScheduleChange(1, Timestamp.valueOf("2001-01-08 13:05:06"), "", 	"{\"vessel\":1,\"manual\":\"false\",\"berth\":1,\"start\":\"2022-05-10T16:32:35Z\",\"expected_end\":\"2022-05-11T16:32:35Z\"}", "testing12"),
		new ScheduleChange(2, Timestamp.valueOf("2002-01-08 13:05:06"), "", 	"{\"vessel\":2,\"manual\":\"false\",\"berth\":1,\"start\":\"2022-05-10T16:32:35Z\",\"expected_end\":\"2022-05-11T16:32:35Z\"}", "testing123"),
		new ScheduleChange(3, Timestamp.valueOf("2003-01-08 13:05:06"), "", 	"{\"vessel\":3,\"manual\":\"false\",\"berth\":1,\"start\":\"2022-05-10T16:32:35Z\",\"expected_end\":\"2022-05-11T16:32:35Z\"}", "testing1234"),
		new ScheduleChange(3, Timestamp.valueOf("2004-01-08 13:05:06"), "", 	"{\"vessel\":3,\"manual\":\"false\",\"berth\":1,\"start\":\"2022-05-10T16:32:35Z\",\"expected_end\":\"2022-05-11T16:32:35Z\"}", "testing12345")
	);

	public static final List<VesselChange> VESSEL_CHANGES = Arrays.asList(new VesselChange(1, Timestamp.valueOf("2000-01-08 13:05:06"), "", 	VESSELS.get(1).toString(), "testing1"),
	                                                                      new VesselChange(2, Timestamp.valueOf("2001-01-08 13:05:06"), "", 	VESSELS.get(2).toString(), "testing12"),
	                                                                      new VesselChange(2, Timestamp.valueOf("2002-01-08 13:05:06"), "", 	VESSELS.get(2).toString(), "testing123"),
	                                                                      new VesselChange(3, Timestamp.valueOf("2003-01-08 13:05:06"), "", 	VESSELS.get(3).toString(), "testing1234"),
	                                                                      new VesselChange(3, Timestamp.valueOf("2004-01-08 13:05:06"), "", 	VESSELS.get(3).toString(), "testing12345")
	);

	public static void createDummyData() {
		for (Port port : PORTS) PortDao.createPort(port);
		for (Terminal terminal : TERMINALS) TerminalDao.createTerminal(terminal);
		for (Berth berth : BERTHS) BerthDao.createBerth(berth);
		for (Vessel vessel : VESSELS) VesselDao.createVessel(vessel);
		for (Schedule schedule : SCHEDULES) ScheduleDao.replaceSchedule(schedule.getVessel(), schedule);
		for (ScheduleChange schange : SCHEDULE_CHANGES) ScheduleChangeDao.createScheduleChange(schange);
		for (VesselChange vchange : VESSEL_CHANGES) VesselChangeDao.createVesselChange(vchange);
	}

	public static void main(String[] args) {
		Configuration.useTestEnvironment(false);
		GenericDao.truncateAllTables();
		createDummyData();
	}
}
