package nl.utwente.di.visol1.optimise;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;


import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Vessel;

public class OptimiseSchedule {
	private static final int OFFSET = 1000*60*30;

	public static Map<Integer, List<Schedule>> getOptimalPlanning(Map<Integer, List<Schedule>> oldSchedule) {
		Timestamp time = new Timestamp(System.currentTimeMillis() + OFFSET);
		return null;
	}

    private static boolean fits(Berth berth, Vessel vessel) {
		return true;
    }

	private static int scheduleTime(Berth berth, Vessel vessel) {
		return 0;
	}

	private static Timestamp getFirstOpenTime(Berth berth, Timestamp time) {
		return null;
	}

	private static Timestamp getFirstClosedTime(Berth berth, Timestamp time) {
		return null;
	}


	private static List<AbstractMap<Berth, Timestamp>> getMinimumTimes(List<Berth> berths, Timestamp after) {

		return null;
	}

	private static List<Vessel> fittingVessels(Berth berth, Timestamp start, Timestamp end) {
		return null;
	}

	private static boolean impossibleToSchedule(List<Vessel> vessels) {
		return false;
	}

	private static List<Vessel> getAutomaticVessels(Map<Integer, List<Schedule>> oldSchedule) {
		return null;
	}

	private static Map<Integer, List<Schedule>> removeManuals(Map<Integer, List<Schedule>> oldSchedule) {
		return null;
	}

	private static Map<Integer, List<Schedule>> getScheduleWithoutDummies(Map<Integer, List<Schedule>>  schedule) {
		return null;
	}
}

