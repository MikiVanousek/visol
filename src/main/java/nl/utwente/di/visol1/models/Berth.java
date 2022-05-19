package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Time;

@XmlRootElement
public class Berth {
    private int id;
    private double unloadSpeed;
    private Terminal terminal; //make promise
    private Time open;
    private Time close;
    private int width;
    private int depth;
    private int length;

    public Berth(int id, double unloadSpeed, Terminal terminal, Time open, Time close, int width, int depth, int length) {
        this.id = id;
        this.unloadSpeed = unloadSpeed;
        this.terminal = terminal;
        this.open = open;
        this.close = close;
        this.width = width;
        this.depth = depth;
        this.length = length;
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

    public Terminal getTerminal() {
        return terminal;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
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
}
