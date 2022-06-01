package nl.utwente.di.visol1.models;

import org.postgresql.util.MD5Digest;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Comparator;

public class ModelTest {
    public static void main(String[] args) {
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

        try {
            JAXBContext context = JAXBContext.newInstance(Port.class, Terminal.class, Berth.class, Vessel.class, Schedule.class, User.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Port port = new Port();
            port.setId(1);
            port.setName("Han-kong");
            marshaller.marshal(port, new File(directory, "port-1.xml"));

            Terminal terminal = new Terminal();
            terminal.setId(1);
            terminal.setOpen(new Time((7 * 60 + 30) * 60 * 1000));
            terminal.setClose(Time.valueOf("21:00:00"));
            terminal.setPort(port);
            marshaller.marshal(terminal, new File(directory, "terminal-1.xml"));

            Berth berth = new Berth();
            berth.setId(1);
            berth.setUnloadSpeed(4.2);
            berth.setTerminal(terminal);
            berth.setOpen(Time.valueOf("08:00:00"));
            berth.setWidth(30);
            berth.setDepth(20);
            berth.setLength(250);
            marshaller.marshal(berth, new File(directory, "berth-1.xml"));

            Vessel vessel = new Vessel();
            vessel.setId(1);
            vessel.setName("BigBoatHan");
            vessel.setContainerAmount(12);
            vessel.setCost(3.2);
            vessel.setDestinationTerminal(terminal);
            vessel.setEta(Timestamp.valueOf(LocalDateTime.now().plusHours(1)));
            vessel.setDeadline(Timestamp.valueOf(LocalDateTime.now().plusDays(2).plusMinutes(24)));
            vessel.setWidth(20);
            vessel.setDepth(15);
            vessel.setLength(80);
            marshaller.marshal(vessel, new File(directory, "vessel-1.xml"));

            Schedule schedule = new Schedule();
            schedule.setVessel(vessel);
            schedule.setBerth(berth);
            schedule.setManual(true);
            schedule.setStart(Timestamp.valueOf(LocalDateTime.now().plusHours(1).plusMinutes(24)));
            schedule.setFinish(Timestamp.valueOf(LocalDateTime.now().plusHours(2).plusMinutes(57)));
            marshaller.marshal(schedule, new File(directory, "schedule-1-1.xml"));

            User user = new User();
            user.setName("Han");
            user.setEmail("han@hankong.com");
            user.setPasswordHash("Password123");
            user.setRole(Role.AUTHORITY);
            user.setPort(port);
            user.setTerminalId(null);
            marshaller.marshal(user, new File(directory, "user-han.xml"));
        } catch (JAXBException exception) {
            exception.printStackTrace();
        }
    }
}
