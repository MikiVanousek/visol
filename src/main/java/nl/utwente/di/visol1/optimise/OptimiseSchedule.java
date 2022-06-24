package nl.utwente.di.visol1.optimise;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap;


import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Terminal;
import nl.utwente.di.visol1.models.Vessel;

public class OptimiseSchedule {

	public static void main(String[] args) {
		List<Vessel> vessels = Arrays.asList(
			new Vessel(1, "Dumbarton Castle", Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-24 01:00:00"), 25, 200, 1, 8, 2, 2),
			new Vessel(2, "The Llandudno", Timestamp.valueOf("2022-06-23 04:30:00"), Timestamp.valueOf("2022-06-23 12:30:00"), 22, 430, 1, 7, 4, 3),
			new Vessel(3, "Killeney", Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-23 23:30:00"), 35, 212, 1, 4, 4, 2),
			new Vessel(4, "The Erne", Timestamp.valueOf("2022-06-23 18:15:00"), Timestamp.valueOf("2022-06-24 12:00:00"), 12, 421, 1, 3, 2, 1),
			new Vessel(5, "Bonito", Timestamp.valueOf("2022-06-23 12:00:00"), Timestamp.valueOf("2022-06-23 06:30:00"), 43, 522, 1, 2, 3, 3),
			new Vessel(6, "Blaze", Timestamp.valueOf("2022-06-24 12:15:00"), Timestamp.valueOf("2022-06-24 23:30:00"), 15, 243, 1, 4, 3, 2),
			new Vessel(7, "Acheron", Timestamp.valueOf("2022-06-24 12:15:00"), Timestamp.valueOf("2022-06-23 18:30:00"), 86, 522, 1, 3, 2, 3),
			new Vessel(8, "Bere Castle", Timestamp.valueOf("2022-06-24 18:00:00"), Timestamp.valueOf("2022-06-24 10:00:00"), 56, 364, 1, 10, 4, 3),
			new Vessel(9, "Llandaff", Timestamp.valueOf("2022-06-23 12:30:00"), Timestamp.valueOf("2022-06-25 23:00:00"), 12, 14, 1, 2, 5, 3),
			new Vessel(10, "Tang", Timestamp.valueOf("2022-06-23 17:05:06"), Timestamp.valueOf("2022-06-24 23:15:00"), 12, 412, 1, 1, 2, 2)
			);
		List<Schedule> s = Arrays.asList(
			new Schedule(1, 1, false, Timestamp.valueOf("2022-06-23 13:30:00"), Timestamp.valueOf("2022-06-23 15:30:00")),
			new Schedule(2, 2, true, Timestamp.valueOf("2022-06-23 06:00:00"), Timestamp.valueOf("2022-06-23 07:30:00")),
			new Schedule(3, 2, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-23 15:30:00")),
			new Schedule(4, 2, false, Timestamp.valueOf("2022-06-23 19:00:00"), Timestamp.valueOf("2022-06-23 21:00:00")),
			new Schedule(5, 3, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-23 14:00:00")),
			new Schedule(6, 3, true, Timestamp.valueOf("2022-06-24 8:00:00"), Timestamp.valueOf("2022-06-24 12:00:00")),
			new Schedule(7, 4, false, Timestamp.valueOf("2022-06-23 13:00:00"), Timestamp.valueOf("2022-06-24 17:00:00")),
			new Schedule(8, 4, false, Timestamp.valueOf("2022-06-23 19:00:00"), Timestamp.valueOf("2022-06-23 20:00:00")),

			new Schedule(9, 1, true, Timestamp.valueOf("2022-06-23 14:00:00"), Timestamp.valueOf("2022-06-23 17:00:00")),
			new Schedule(10, 3, true, Timestamp.valueOf("2022-06-23 13:30:00"), Timestamp.valueOf("2022-06-23 16:00:00")),
			new Schedule(10, 4, true, Timestamp.valueOf("2023-06-23 13:30:00"), Timestamp.valueOf("2023-06-23 16:00:00"))
		);
		Map<Integer, List<Schedule>> test1 = new HashMap<>();
		test1.put(1, new ArrayList<>(Arrays.asList(s.get(0))));
		test1.put(2, new ArrayList<>(Arrays.asList(s.get(1), s.get(2), s.get(3))));
		test1.put(3, new ArrayList<>(Arrays.asList(s.get(4), s.get(5))));
		test1.put(4, new ArrayList<>(Arrays.asList(s.get(6), s.get(7), s.get(10))));
		getOptimalPlanning(test1);
	}


	private static final long MINUTE = 1000*60;
	private static final long OFFSET = MINUTE*30;
	private static final long FIFTEEN_MINUTES = MINUTE*15;

	private static final int DUMMY_ID = -1;
	private static final long TIME_LIMIT = MINUTE*60*24*30;//30 days





	public static Map<Integer, List<Schedule>> getOptimalPlanning(Map<Integer, List<Schedule>> oldSchedule) {

		List<Berth> berths = getBerths(oldSchedule);
		List<Vessel> vessels = getAutomaticVessels(oldSchedule);

		Timestamp time = Timestamp.valueOf("2022-06-23 03:00:00");//new Timestamp(System.currentTimeMillis() + OFFSET);
		Map<Integer, List<Schedule>> newSchedule = getNewSchedule(oldSchedule, berths);


		while(true) {
			if(vessels.isEmpty()) break;
			if(impossibleToSchedule(vessels, time)) break;

			Map<Berth, Timestamp> minTimes = getMinimumTimes(newSchedule, berths, time);
			List<Berth> sortedBerthsOnTime = sortBerthsOnMinTime(minTimes);
			time = minTimes.get(sortedBerthsOnTime.get(0));

			for(Berth b : sortedBerthsOnTime) {
				Timestamp firstOpen = minTimes.get(b);
				Timestamp firstClose = getFirstClosedTime(b, newSchedule.get(b.getId()), firstOpen);
				List<Vessel> fittingVessels = fittingVessels(b, vessels, firstOpen, firstClose);
				if(fittingVessels.isEmpty()) {
					newSchedule.get(b.getId()).add(getDummySchedule(b.getId(), firstOpen, firstClose));
					continue;
				}
				Vessel bestVessel = getBestVessel(fittingVessels, b, firstOpen, firstClose);

				vessels.remove(bestVessel);
				newSchedule.get(b.getId()).add(getBestSchedule(bestVessel, b, firstOpen, firstClose));

			}
		}
		return getScheduleWithoutDummies(newSchedule);
	}


	public static  Map<Integer, List<Schedule>> getNewSchedule(Map<Integer, List<Schedule>> oldSchedule, List<Berth> allBerths) {
		Map<Integer, List<Schedule>> newSchedule = removeManuals(oldSchedule);
		for(Berth b : allBerths) {
			if(!oldSchedule.containsKey(b.getId())) newSchedule.put(b.getId(), new ArrayList<>());
		}
		return newSchedule;
	}

	public static List<Berth> getBerths(Map<Integer, List<Schedule>> oldSchedule) {
		for(int k : oldSchedule.keySet()) {
			return new ArrayList<>( BerthDao.getBerthsByTerminal( BerthDao.getBerth(k).getTerminalId() ).values() );
		}
		return null;
	}



	public static List<Berth> sortBerthsOnMinTime(Map<Berth, Timestamp> minTimes) {
		List<Berth> res = new ArrayList<>(minTimes.keySet());
		res.sort(new Comparator<Berth>() {
			@Override
			public int compare(Berth o1, Berth o2) {
				return (int) (minTimes.get(o1).getTime() - minTimes.get(o2).getTime());
			}
		});

		return res;
	}

	public static Vessel getBestVessel(List<Vessel> candidates, Berth b, Timestamp from, Timestamp to) {
		long bestTime = -1;
		Vessel bestVessel = candidates.get(0);
		for(Vessel v : candidates) {
			if(Math.max(from.getTime(), v.getArrival().getTime()) + scheduleTimeInMillis(b, v) - v.getArrival().getTime() > bestTime) {
				bestVessel = v;
			}
		}
		return bestVessel;
	}


	public static Schedule getBestSchedule(Vessel bestVessel, Berth b, Timestamp from, Timestamp to) {
		long start = Math.max(bestVessel.getArrival().getTime(), from.getTime());
		return new Schedule(bestVessel.getId(), b.getId(), false, new Timestamp(start), new Timestamp(start + scheduleTimeInMillis(b, bestVessel)));
	}

	public static Schedule getDummySchedule(int berthid, Timestamp from, Timestamp to) {
		return new Schedule(DUMMY_ID, berthid , false, from, to);
	}


	private static long scheduleTimeInMillis(Berth berth, Vessel vessel) {
		return (long) ((berth.getUnloadSpeed() / vessel.getContainers())*60*60*1000);
	}


	private static Timestamp getFirstOpenTime(Berth berth,List<Schedule> berthSchedule,Timestamp time) {
		Timestamp res = possiblyDelay(time, berth.getOpen(), berth.getClose());
		berthSchedule.sort(Comparator.comparing(Schedule::getStart));
		for(Schedule s : berthSchedule) {
			if(!(new Timestamp(s.getStart().getTime() - FIFTEEN_MINUTES).after(res)) && s.getExpectedEnd().after(res)) {
				res = s.getExpectedEnd();
			}
		}
		return possiblyDelay(new Timestamp(res.getTime() + FIFTEEN_MINUTES), berth.getOpen(), berth.getClose());
	}

	private static Timestamp getFirstClosedTime(Berth berth, List<Schedule> berthSchedule, Timestamp time) {
		berthSchedule.sort(Comparator.comparing(Schedule::getStart));
		for(Schedule s : berthSchedule) {
			if(s.getStart().after(time)) {
				return new Timestamp(s.getStart().getTime() - FIFTEEN_MINUTES);
			}
		}
		Calendar closingTime = Calendar.getInstance();
		closingTime.setTime(time);
		Calendar berthClosingTime = Calendar.getInstance();
		berthClosingTime.setTime(berth.getClose());


		closingTime.set(Calendar.HOUR_OF_DAY, berthClosingTime.get(Calendar.HOUR_OF_DAY));
		closingTime.set(Calendar.MINUTE, berthClosingTime.get(Calendar.MINUTE));
		closingTime.set(Calendar.SECOND, berthClosingTime.get(Calendar.SECOND));

		return new Timestamp(closingTime.getTimeInMillis());
	}


	private static Map<Berth, Timestamp> getMinimumTimes(Map<Integer, List<Schedule>> schedule,List<Berth> berths, Timestamp after) {
		Map<Berth, Timestamp> res = new HashMap<>();
		for(Berth b : berths) {
			res.put(b, getFirstOpenTime(b, schedule.get(b.getId()),after));
		}
		return res;
	}

	private static List<Vessel> fittingVessels(Berth berth, List<Vessel> vessels, Timestamp start, Timestamp end) {
		List<Vessel> res = new ArrayList<>();
		for(Vessel v : vessels) {
			Timestamp vesselEnd = new Timestamp(Math.max(v.getArrival().getTime(), start.getTime()) + scheduleTimeInMillis(berth, v));
			if(berth.fits(v) && v.getArrival().before(end) && vesselEnd.before(end)) {
				res.add(v);
			}
		}
		return res;
	}

	private static boolean impossibleToSchedule(List<Vessel> vessels, Timestamp time) {
		for(Vessel v : vessels) if(!v.getDeadline().after(time)) return true;
		return false;
	}

	private static List<Vessel> getAutomaticVessels(Map<Integer, List<Schedule>> oldSchedule) {
		List<Vessel> vessels = new ArrayList<>();
		for(int k : oldSchedule.keySet()) {
			for(Schedule s : oldSchedule.get(k)) {
				if(!s.isManual()) vessels.add(VesselDao.getVessel(s.getVessel()));
			}
		}
		return vessels;
	}

	private static Map<Integer, List<Schedule>> removeManuals(Map<Integer, List<Schedule>> oldSchedule) {

		for(int k : oldSchedule.keySet()) {
			List<Schedule> toRemove = new ArrayList<>();
			for(Schedule s : oldSchedule.get(k)) {
				if(!s.isManual()) toRemove.add(s);
			}
			oldSchedule.get(k).removeAll(toRemove);
		}
		return oldSchedule;
	}

	private static Map<Integer, List<Schedule>> getScheduleWithoutDummies(Map<Integer, List<Schedule>>  schedule) {
		for(int k : schedule.keySet()) {
			List<Schedule> toRemove = new ArrayList<>();
			for(Schedule s : schedule.get(k)) {
				if(s.getVessel() == DUMMY_ID) toRemove.add(s);
			}
			schedule.get(k).removeAll(toRemove);
		}
		return schedule;
	}

	public static Timestamp possiblyDelay(Timestamp start, Time open, Time close) {
		Calendar arrivalCal = Calendar.getInstance();
		arrivalCal.setTime(start);
		Calendar openCal = Calendar.getInstance();
		openCal.setTime(open);
		Calendar closeCal = Calendar.getInstance();
		closeCal.setTime(close);
		// Set open and close to the day of arrival
		openCal.set(Calendar.YEAR, arrivalCal.get(Calendar.YEAR));
		openCal.set(Calendar.MONTH, arrivalCal.get(Calendar.MONTH));
		openCal.set(Calendar.DAY_OF_MONTH, arrivalCal.get(Calendar.DAY_OF_MONTH));
		closeCal.set(Calendar.YEAR, arrivalCal.get(Calendar.YEAR));
		closeCal.set(Calendar.MONTH, arrivalCal.get(Calendar.MONTH));
		closeCal.set(Calendar.DAY_OF_MONTH, arrivalCal.get(Calendar.DAY_OF_MONTH));
		Calendar resultCal = Calendar.getInstance();
		resultCal.setTime(start);
		// If arrival after close, delay by one day
		if (arrivalCal.after(closeCal)) {
			resultCal.add(Calendar.DATE, 1);
			openCal.add(Calendar.DATE, 1);
		}
		// If arrival before open, delay till open
		if (arrivalCal.before(openCal)) {
			resultCal.set(Calendar.HOUR_OF_DAY, openCal.get(Calendar.HOUR_OF_DAY));
			resultCal.set(Calendar.MINUTE, openCal.get(Calendar.MINUTE));
			resultCal.set(Calendar.SECOND, openCal.get(Calendar.SECOND));
		}
		return new Timestamp(resultCal.getTimeInMillis());
	}

}

