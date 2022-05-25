package nl.utwente.di.visol1.models;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.sql.Time;

public class ModelTest {
    public static void main(String[] args) {
        File directory = new File("./marshaller");
        if (directory.exists()) {
            System.out.println("Directory already exists!");
        } else {
            if (directory.mkdirs()) {
                System.out.println("Created directory!");
            } else {
                System.err.println("Could not create directory!");
                return;
            }
        }

        Port port = new Port();
        port.setId(1);
        port.setName("Han-kong");

        try {
            JAXBContext context = JAXBContext.newInstance(Port.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(port, new File(directory, "port1.xml"));
        } catch (JAXBException exception) {
            exception.printStackTrace();
        }

        Terminal terminal = new Terminal();
        terminal.setId(1);
        terminal.setOpen(new Time((7 * 60 + 30) * 60 * 1000));
        terminal.setClose(Time.valueOf("21:00:00"));
        terminal.setPort(port);

        try {
            JAXBContext context = JAXBContext.newInstance(Terminal.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(terminal, new File(directory, "terminal1.xml"));
        } catch (JAXBException exception) {
            exception.printStackTrace();
        }

        Berth berth = new Berth();
        berth.setId(1);
        berth.setUnloadSpeed(4.2);
        berth.setTerminal(terminal);
        berth.setOpen(Time.valueOf("08:00:00"));
        berth.setWidth(30);
        berth.setDepth(20);
        berth.setLength(250);

        try {
            JAXBContext context = JAXBContext.newInstance(Berth.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(berth, new File(directory, "berth1.xml"));
        } catch (JAXBException exception) {
            exception.printStackTrace();
        }

        // TODO Vessel, Schedule, User
    }
}
