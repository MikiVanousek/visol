package nl.utwente.di.visol1.dao;

import nl.utwente.di.visol1.models.*;
import nl.utwente.di.visol1.util.Configuration;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class DummyData {

    public static void createDummyData() {
        System.out.println("creating ports");
        for(Port port : PORTS)  PortDao.createPort(port);
        System.out.println("creating terminals");
        for(Terminal terminal : TERMINALS) TerminalDao.createTerminal(terminal);
        System.out.println("creating berths");
        for(Berth berth : BERTHS) BerthDao.createBerth(berth);
        System.out.println("creating vessels");
        for(Vessel vessel : VESSELS) VesselDao.createVessel(vessel);
        System.out.println("creating schedules");
        for(Schedule schedule : SCHEDULES) ScheduleDao.replaceSchedule(schedule.getVessel(), schedule);
    }

    public static final List<Port> PORTS = Arrays.asList(
        new Port(1, "one"),
        new Port(2, "two")
    );

    public static final List<Terminal> TERMINALS = Arrays.asList(
        new Terminal(1, 1, "WestTerminal"),
        new Terminal(2, 1, "SouthTerminal"),
        new Terminal(3, 2, "c"),
        new Terminal(4, 2, "d")
    );


    public static final List<Berth> BERTHS = Arrays.asList(
        new Berth(1, 1, null, null, 5.5, 1, 2, 3),
        new Berth(2, 1, null, null, 11.0, 123, 22, 33),
        new Berth(3, 2, null, null, 23.1, 11, 22, 33),
        new Berth(4, 2, null, null, 4.3, 154, 2234, 311),
        new Berth(5, 3, null, null, 5.5, 1213, 2532, 3),
        new Berth(6, 3, null, null, 7, 12, 18, 3),
        new Berth(7, 4, null, null, 3, 19, 122, 323),
        new Berth(8, 4, null, null, 2, 5, 12, 3123123)
    );

	//public Vessel(int id, String name, Timestamp arrival, Timestamp deadline, int containers, double costPerHour, int destination, int length, int width, int depth) {
    public static final List<Vessel> VESSELS= Arrays.asList(
        new Vessel(1, "a", Timestamp.valueOf("2000-01-08 23:05:06"),Timestamp.valueOf("2000-01-12 23:05:06"),1,5.5,1,3,13, 6),
        new Vessel(2, "b", Timestamp.valueOf("2000-01-08 14:05:06"),Timestamp.valueOf("2000-01-13 23:05:06"),1,2,1,421,53,154),
        new Vessel(3, "c", Timestamp.valueOf("2000-01-08 16:05:06"),Timestamp.valueOf("2000-01-13 23:05:06"),23,2,1,31,24,5),
        new Vessel(4, "d", Timestamp.valueOf("2000-01-08 14:05:06"),Timestamp.valueOf("2000-01-13 23:05:06"),5,1.5,1,31,24,64),
        new Vessel(5, "e", Timestamp.valueOf("2000-01-09 12:05:06"),Timestamp.valueOf("2000-01-14 23:05:06"),12,4,2,12,24,24),
        new Vessel(6, "f", Timestamp.valueOf("2000-01-09 15:05:06"),Timestamp.valueOf("2000-01-15 23:05:06"),8,2,2,31,53,42),
        new Vessel(7, "g", Timestamp.valueOf("2000-01-09 18:05:06"),Timestamp.valueOf("2000-01-14 23:05:06"),135,1.2,2,15,36,53),
        new Vessel(8, "h", Timestamp.valueOf("2000-01-09 21:05:06"),Timestamp.valueOf("2000-01-14 23:05:06"),13,41,2,16,24,64),
        new Vessel(9, "i", Timestamp.valueOf("2000-01-07 16:05:06"),Timestamp.valueOf("2000-01-15 23:05:06"),12,14,3,21,63,23),
        new Vessel(10, "j", Timestamp.valueOf("2000-01-07 17:05:06"),Timestamp.valueOf("2000-01-17 23:05:06"),12,412,3,24,26,25),
        new Vessel(11, "k", Timestamp.valueOf("2000-01-07 12:05:06"),Timestamp.valueOf("2000-01-17 23:05:06"),24,12,3,25,25,25),
        new Vessel(12, "l", Timestamp.valueOf("2000-01-07 13:05:06"),Timestamp.valueOf("2000-01-18 23:05:06"),13,12,3,36,25,26),
        new Vessel(13, "m", Timestamp.valueOf("2000-01-07 12:05:06"),Timestamp.valueOf("2000-01-17 23:05:06"),13,3.2,4,38,25,26),
        new Vessel(14, "n", Timestamp.valueOf("2000-01-07 13:05:06"),Timestamp.valueOf("2000-01-16 23:05:06"),45,0.5,4,24,24,36),
        new Vessel(15, "o", Timestamp.valueOf("2000-01-07 14:05:06"),Timestamp.valueOf("2000-01-15 23:05:06"),234,0.01,4,14,24,64),
        new Vessel(16, "p", Timestamp.valueOf("2000-01-07 16:05:06"),Timestamp.valueOf("2000-01-15 23:05:06"),5,1.2,4,17,12,53)
    );

    public static final List<Schedule> SCHEDULES = Arrays.asList(
        new Schedule(1, 1, false, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(2, 1, true, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
        new Schedule(3, 2, false, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(4, 2, false, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
        new Schedule(5, 3, false, Timestamp.valueOf("2000-01-10 13:05:06"), Timestamp.valueOf("2000-01-10 23:05:06")),
        new Schedule(6, 3, true, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(7, 4, false, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
        new Schedule(8, 4, false, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(9, 5, true, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
        new Schedule(10, 5, false, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(11, 6, false, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
        new Schedule(12, 6, true, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(13, 7, false, Timestamp.valueOf("2000-01-09 13:05:06"), Timestamp.valueOf("2000-01-09 23:05:06")),
        new Schedule(14, 7, true, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06")),
        new Schedule(15, 8, false, Timestamp.valueOf("2000-01-11 13:05:06"), Timestamp.valueOf("2000-01-11 23:05:06")),
        new Schedule(16, 8, false, Timestamp.valueOf("2000-01-08 13:05:06"), Timestamp.valueOf("2000-01-08 23:05:06"))
    );

    public static void main(String[] args) {
	    Configuration.useTestEnvironment(true);
        GenericDao.truncateAllTables();
        createDummyData();
    }




}
