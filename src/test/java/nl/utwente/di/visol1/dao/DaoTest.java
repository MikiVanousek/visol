package nl.utwente.di.visol1.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Port;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Terminal;
import nl.utwente.di.visol1.models.Vessel;
import nl.utwente.di.visol1.util.Configuration;
import nl.utwente.di.visol1.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class DaoTest {

	static final Timestamp MIN_TIME = new Timestamp(0);
	static final Timestamp MAX_TIME = Timestamp.valueOf("3000-1-1 23:05:06");

	@BeforeEach
	void setup() {
		Configuration.useTestEnvironment(true);
		GenericDao.truncateAllTables();
		DummyData.createDummyData();
	}

	/**
	 * Test if the created data in setup, can be retrieved using the get-methods and matches the dummyData
	 */
	@Test
	void createGetTest() {
		List<Port> ports = new ArrayList<>(PortDao.getPorts().values());
		List<Terminal> terminalsByPort = new ArrayList<>();
		List<Berth> berthsByTerminal = new ArrayList<>();
		List<Schedule> schedulesByVessel = new ArrayList<>();
		List<Schedule> schedulesByBerth = new ArrayList<>();
		List<Schedule> schedulesByTerminal = new ArrayList<>();
		List<Schedule> schedulesByPort = new ArrayList<>();

		for (Port port : DummyData.PORTS) {
			assertEquals(port, PortDao.getPort(port.getId()));
			terminalsByPort.addAll(TerminalDao.getTerminalsByPort(port.getId()).values());
			Map<Integer, Map<Integer, List<Schedule>>> schedules = ScheduleDao.getSchedulesByPort(port.getId(), MIN_TIME, MAX_TIME);
			for (int key : schedules.keySet()) {
				for (int key2 : schedules.get(key).keySet()) {
					schedulesByPort.addAll(schedules.get(key).get(key2));
				}
			}
		}

		for (Terminal terminal : DummyData.TERMINALS) {
			assertEquals(terminal, TerminalDao.getTerminal(terminal.getId()));
			Map<Integer, List<Schedule>> schedules = ScheduleDao.getSchedulesByTerminal(terminal.getId(), MIN_TIME, MAX_TIME);
			for (int key : schedules.keySet()) schedulesByTerminal.addAll(schedules.get(key));
			berthsByTerminal.addAll(BerthDao.getBerthsByTerminal(terminal.getId()).values());
		}

		for (Berth berth : DummyData.BERTHS) {
			Berth b = BerthDao.getBerth(berth.getId());
			assertEquals(berth, BerthDao.getBerth(berth.getId()));
			schedulesByBerth.addAll(ScheduleDao.getSchedulesByBerth(berth.getId(), MIN_TIME, MAX_TIME));
		}

		for (Vessel vessel : DummyData.VESSELS) {
			Vessel v = VesselDao.getVessel(vessel.getId());
			assertEquals(vessel, VesselDao.getVessel(vessel.getId()));
			schedulesByVessel.add(ScheduleDao.getScheduleByVessel(vessel.getId()));
		}

		for (Schedule schedule : DummyData.SCHEDULES) {
			assertEquals(schedule, ScheduleDao.getScheduleByVessel(schedule.getVessel()));
		}

		assertTrue(TestUtil.listEqualsIgnoreOrder(ports, DummyData.PORTS));
		assertTrue(TestUtil.listEqualsIgnoreOrder(terminalsByPort, DummyData.TERMINALS));
		assertTrue(TestUtil.listEqualsIgnoreOrder(berthsByTerminal, DummyData.BERTHS));
		assertTrue(TestUtil.listEqualsIgnoreOrder(schedulesByVessel, DummyData.SCHEDULES));
		assertTrue(TestUtil.listEqualsIgnoreOrder(schedulesByBerth, DummyData.SCHEDULES));
		assertTrue(TestUtil.listEqualsIgnoreOrder(schedulesByTerminal, DummyData.SCHEDULES));
		assertTrue(TestUtil.listEqualsIgnoreOrder(schedulesByPort, DummyData.SCHEDULES));
	}

	@Test
	void deleteTest() {
		List<Port> ports = new ArrayList<>(DummyData.PORTS);
		List<Terminal> terminals = new ArrayList<>(DummyData.TERMINALS);
		List<Berth> berths = new ArrayList<>(DummyData.BERTHS);
		List<Schedule> schedules = new ArrayList<>(DummyData.SCHEDULES);
		List<Vessel> vessels = new ArrayList<>(DummyData.VESSELS);

		schedules.remove(0);
		ScheduleDao.deleteScheduleByVessel(1);
		List<Schedule> schedules2 = new ArrayList<>();
		for (int i = 0; i < terminals.size(); i++) {
			Map<Integer, List<Schedule>> schedulesMap = ScheduleDao.getSchedulesByTerminal(i + 1, MIN_TIME, MAX_TIME);
			for (int key : schedulesMap.keySet()) schedules2.addAll(schedulesMap.get(key));
		}

		vessels.remove(0);
		VesselDao.deleteVessel(1);
		List<Vessel> vessels2 = new ArrayList<>();
		for (int i = 0; i < terminals.size(); i++) {
			vessels2.addAll(VesselDao.getVesselsByTerminal(i + 1).values());
		}

		berths.remove(0);
		BerthDao.deleteBerth(1);
		List<Berth> berths2 = new ArrayList<>();
		for (int i = 0; i < terminals.size(); i++) {
			berths2.addAll(BerthDao.getBerthsByTerminal(i + 1).values());
		}

		terminals.remove(0);
		TerminalDao.deleteTerminal(1);
		List<Terminal> terminals2 = new ArrayList<>();
		for (int i = 0; i < ports.size(); i++) {
			terminals2.addAll(TerminalDao.getTerminalsByPort(i + 1).values());
		}

		ports.remove(0);
		PortDao.deletePort(1);
		List<Port> ports2 = new ArrayList<>(PortDao.getPorts().values());

		assertTrue(TestUtil.listEqualsIgnoreOrder(schedules, schedules2));
		assertTrue(TestUtil.listEqualsIgnoreOrder(vessels, vessels2));
		assertTrue(TestUtil.listEqualsIgnoreOrder(berths, berths2));
		assertTrue(TestUtil.listEqualsIgnoreOrder(terminals, terminals2));
		assertTrue(TestUtil.listEqualsIgnoreOrder(ports, ports2));
}

	@Test
	void replaceTest() {

		Port rport = new Port(1, "test");
		Terminal rterminal = new Terminal(1, 2, "TEST");
		Berth rberth = new Berth(1, 1, null, null, 14, 4, 1, 423);
		Schedule rschedule = new Schedule(1, 2, true, Timestamp.valueOf("2000-01-10 13:05:06"), Timestamp.valueOf("2000-01-11 23:05:06"));
		Vessel rvessel = new Vessel(1, "sfda", Timestamp.valueOf("2000-01-05 23:05:06"), Timestamp.valueOf("2000-01-14 23:05:06"), 41, 2, 2, 35, 23,
		                            46);

		PortDao.replacePort(rport.getId(), rport);
		TerminalDao.replaceTerminal(rterminal.getId(), rterminal);
		BerthDao.replaceBerth(rberth.getId(), rberth);
		ScheduleDao.replaceSchedule(rschedule.getVessel(), rschedule);
		VesselDao.replaceVessel(rvessel.getId(), rvessel);

		assertEquals(rport, PortDao.getPort(rport.getId()));
		assertEquals(rterminal, TerminalDao.getTerminal(rterminal.getId()));
		assertEquals(rberth, BerthDao.getBerth(rberth.getId()));
		assertEquals(rschedule, ScheduleDao.getScheduleByVessel(rschedule.getVessel()));
		assertEquals(rvessel, VesselDao.getVessel(rvessel.getId()));
	}
}
