package nl.utwente.di.visol1.optimise;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.ScheduleChangeDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.models.Vessel;

public class OptimiseSchedule {

	public static void main(String[] args) {
		optimisePlanning(Timestamp.valueOf("2022-06-23 01:00:00"), Timestamp.valueOf("2022-06-25 13:30:00"), 1);
	}


	private static final long MINUTE = 1000*60;
	private static final long OFFSET = MINUTE*30;
	private static final long FIFTEEN_MINUTES = MINUTE*15;

	private static final int DUMMY_ID = -1;
	private static final long TIME_LIMIT = MINUTE*60*24*30;//30 days
	private static final Timestamp MAX_TIME = Timestamp.valueOf("3000-1-1 23:05:06");


	public static Map<Integer, List<Schedule>> optimisePlanning(Timestamp from, Timestamp to, int terminalId) {

		List<Berth> berths = new ArrayList<>( BerthDao.getBerthsByTerminal(terminalId).values() );

		Map<Integer, List<Schedule>> oldSchedule = ScheduleDao.getSchedulesByTerminal(terminalId, from, to);
		List<Vessel> vessels = getAutomaticVessels(oldSchedule);


		Map<Integer, List<Schedule>> newSchedule = getNewSchedule(oldSchedule, berths);


		while(true) {
			if(vessels.isEmpty()) break;
			if(impossibleToSchedule(vessels, from)) break;

			Map<Berth, Timestamp> minTimes = getMinimumTimes(newSchedule, berths, from);
			List<Berth> sortedBerthsOnTime = sortBerthsOnMinTime(minTimes);
			from = minTimes.get(sortedBerthsOnTime.get(0));

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

		newSchedule =  getScheduleWithoutDummies(newSchedule);
		updateDatabase(newSchedule);
		return newSchedule;

	}

	/*public static Schedule scheduleAutomatic(Vessel vessel) {
		Timestamp start = new Timestamp(Math.max(System.currentTimeMillis(), vessel.getArrival().getTime());
		Map<Integer, List<Schedule>> schedule = ScheduleDao.getSchedulesByTerminal(vessel.getDestination(), start, MAX_TIME);
		List<Berth> berths = new ArrayList<>( BerthDao.getBerthsByTerminal(vessel.getDestination()).values() );
		Map<Berth, Timestamp> minTimes = getMinimumTimes(schedule, berths, start);
		
		
	}*/

	public static void updateDatabase(Map<Integer, List<Schedule>> newSchedule) {
		for(int k : newSchedule.keySet()) {
			for(Schedule s : newSchedule.get(k)) {
				if(!s.isManual()) {
					Schedule oldSchedule = ScheduleDao.getScheduleByVessel(s.getVessel());
					ScheduleDao.replaceSchedule(s.getVessel(), s);
					ScheduleChange schange = new ScheduleChange(s, oldSchedule, "optimise algorithm");
					ScheduleChangeDao.createScheduleChange(schange);
				}
			}
		}
	}


	public static  Map<Integer, List<Schedule>> getNewSchedule(Map<Integer, List<Schedule>> oldSchedule, List<Berth> allBerths) {
		Map<Integer, List<Schedule>> newSchedule = removeManuals(oldSchedule);
		for(Berth b : allBerths) {
			if(!oldSchedule.containsKey(b.getId())) newSchedule.put(b.getId(), new ArrayList<>());
		}
		return newSchedule;
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


	private static Schedule getBestSchedule(Vessel bestVessel, Berth b, Timestamp from, Timestamp to) {
		long start = Math.max(bestVessel.getArrival().getTime(), from.getTime());
		return new Schedule(bestVessel.getId(), b.getId(), false, new Timestamp(start), new Timestamp(start + scheduleTimeInMillis(b, bestVessel)));
	}

	private static Schedule getDummySchedule(int berthid, Timestamp from, Timestamp to) {
		return new Schedule(DUMMY_ID, berthid , false, from, to);
	}


	private static long scheduleTimeInMillis(Berth berth, Vessel vessel) {
		return (long) ((vessel.getContainers() / berth.getUnloadSpeed())*60*60*1000);
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

