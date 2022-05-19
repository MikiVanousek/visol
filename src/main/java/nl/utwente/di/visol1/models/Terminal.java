package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@XmlRootElement
public class Terminal {
    int id;
    Time open;
    Time close;
    Port port;

    public Terminal(int id, Time open, Time close, Port port) {
        this.id = id;
        this.open = open;
        this.close = close;
        this.port = port;
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

    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }
}
