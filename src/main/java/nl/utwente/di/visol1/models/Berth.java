package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Berth {
    private int id;
    private double unloadSpeed;
    private int terminalId;
    private Time open;
    private Time close;
    private int width;
    private int depth;
    private int length;

    public Berth(int id, double unloadSpeed, int terminalId, Time open, Time close, int width, int depth, int length) {
        this.id = id;
        this.unloadSpeed = unloadSpeed;
        this.terminalId = terminalId;
        this.open = open;
        this.close = close;
        this.width = width;
        this.depth = depth;
        this.length = length;
    }

    public Berth() {
        // Empty constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getUnloadSpeed() {
        return unloadSpeed;
    }

    public void setUnloadSpeed(double unloadSpeed) {
        this.unloadSpeed = unloadSpeed;
    }

    public int getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(int terminalId) {
        this.terminalId = terminalId;
    }

    public Terminal getTerminal() {
        return null; // TerminalDao.getById(terminalId);
    }

    public void setTerminal(Terminal terminal) {
        this.terminalId = terminal.getId();
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean fits(Vessel vessel) {
        return vessel.getWidth() < getWidth() && vessel.getDepth() < vessel.getDepth() && vessel.getLength() < getLength();
    }
}
