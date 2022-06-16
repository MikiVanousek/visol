package nl.utwente.di.visol1.models;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;

import nl.utwente.di.visol1.type_adapters.TimestampAdapter;
import nl.utwente.di.visol1.util.EncryptionUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ModelTest {

	@Test
	void marshalTest() throws JAXBException {
		File directory = new File("./marshaller");
		try {
			if (directory.exists()) {
				System.out.println("Directory already exists!");
				if (Files.walk(directory.toPath())
					.sorted(Comparator.reverseOrder())
					.map(Path::toFile)
					.allMatch(File::delete)) {
					System.out.println("Deleted directory!");
				} else {
					System.err.println("Could not delete directory!");
					return;
				}
			}

			if (directory.mkdirs()) {
				System.out.println("Created directory!");
			} else {
				System.err.println("Could not create directory!");
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		JAXBContext context = JAXBContext.newInstance(Port.class, Terminal.class, Berth.class, Vessel.class, Schedule.class, User.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Unmarshaller unmarshaller = context.createUnmarshaller();

		Port port = new Port();
		port.setId(1);
		port.setName("Han-kong");
		marshaller.marshal(port, new File(directory, "port-1.xml"));
		Port rePort = (Port) unmarshaller.unmarshal(new File(directory, "port-1.xml"));
		assertEquals(port, rePort);

		Terminal terminal = new Terminal();
		terminal.setId(1);
		terminal.setName("fat");
		terminal.setPort(port);
		marshaller.marshal(terminal, new File(directory, "terminal-1.xml"));
		Terminal reTerminal = (Terminal) unmarshaller.unmarshal(new File(directory, "terminal-1.xml"));
		assertEquals(terminal, reTerminal);

		Berth berth = new Berth();
		berth.setId(1);
		berth.setUnloadSpeed(4.2);
		berth.setTerminal(terminal);
		berth.setOpen(Time.valueOf("08:00:00"));
		berth.setWidth(30);
		berth.setDepth(20);
		berth.setLength(250);
		marshaller.marshal(berth, new File(directory, "berth-1.xml"));
		Berth reBerth = (Berth) unmarshaller.unmarshal(new File(directory, "berth-1.xml"));
		assertEquals(berth, reBerth);

		Vessel vessel = new Vessel();
		vessel.setId(1);
		vessel.setName("BigBoatHan");
		vessel.setContainers(12);
		vessel.setDestination(terminal.getId());
		vessel.setArrival(new TimestampAdapter().unmarshal("2022-06-16T09:14:43Z"));
		vessel.setDeadline(Timestamp.valueOf(LocalDateTime.now().plusDays(2).plusMinutes(24).withNano(0)));
		vessel.setWidth(20);
		vessel.setDepth(15);
		vessel.setLength(80);
		marshaller.marshal(vessel, new File(directory, "vessel-1.xml"));
		Vessel reVessel = (Vessel) unmarshaller.unmarshal(new File(directory, "vessel-1.xml"));
		assertEquals(vessel, reVessel);

		Schedule schedule = new Schedule();
		schedule.setVessel(vessel.getId());
		schedule.setBerth(berth.getId());
		schedule.setManual(true);
		schedule.setStart(Timestamp.valueOf(LocalDateTime.now().plusHours(1).plusMinutes(24).withNano(0)));
		schedule.setExpectedEnd(Timestamp.valueOf(LocalDateTime.now().plusHours(2).plusMinutes(57).withNano(0)));
		marshaller.marshal(schedule, new File(directory, "schedule-1-1.xml"));
		Schedule reSchedule = (Schedule) unmarshaller.unmarshal(new File(directory, "schedule-1-1.xml"));
		assertEquals(schedule, reSchedule);

		User user = new User();
		user.setName("Han");
		user.setEmail("han@hankong.com");
		EncryptionUtil.EncryptedPassword password = EncryptionUtil.encryptPassword("Password123");
		user.setPasswordHash(password.hash);
		user.setSaltAndPepper(password.saltAndPepper);
		user.setRole(Role.AUTHORITY);
		user.setPort(port);
		user.setTerminalId(null);
		marshaller.marshal(user, new File(directory, "user-han.xml"));
		User reUser = (User) unmarshaller.unmarshal(new File(directory, "user-han.xml"));
		assertEquals(user, reUser);
	}
}
