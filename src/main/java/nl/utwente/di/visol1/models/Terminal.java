package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Terminal {
    private int id;
    private Time open;
    private Time close;
    private int portId;

    public Terminal(int id, Time open, Time close, int portId) {
        this.id = id;
        this.open = open;
        this.close = close;
        this.portId = portId;
    }

    public Terminal() {
        // Empty constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Time getOpen() {
        return open;
    }

    public void setOpen(Time open) {
        this.open = open;
    }

    public Time getClose() {
        return close;
    }

    public void setClose(Time close) {
        this.close = close;
    }

    public int getPortId() {
        return portId;
    }

    public void setPortId(int portId) {
        this.portId = portId;
    }

    public Port getPort() {
        return null; // PortDAO.getPort(portId);
    }

    public void setPort(Port port) {
        this.portId = port.getId();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Terminal)) return false;
        Terminal terminal = (Terminal) o;
        return id == terminal.id && portId == terminal.portId && Objects.equals(open, terminal.open) && Objects.equals(close, terminal.close);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, open, close, portId);
    }
}