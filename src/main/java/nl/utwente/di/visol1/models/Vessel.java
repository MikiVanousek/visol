package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@XmlRootElement
public class Vessel {
    private int id;
    private String name;
    private int containerAmount;
    private double cost;
    private Terminal destinationTerminal; //make promise
    private Timestamp eta;
    private Timestamp deadline;
    private int width;
    private int length;
    private int depth;

    public Vessel(int id, String name, int containerAmount, double cost, Terminal destinationTerminal, Timestamp eta, Timestamp deadline, int width, int length, int depth) {
        this.id = id;
        this.name = name;
        this.containerAmount = containerAmount;
        this.cost = cost;
        this.destinationTerminal = destinationTerminal;
        this.eta = eta;
        this.deadline = deadline;
        this.width = width;
        this.length = length;
        this.depth = depth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContainerAmount() {
        return containerAmount;
    }

    public void setContainerAmount(int containerAmount) {
        this.containerAmount = containerAmount;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Terminal getDestinationTerminal() {
        return destinationTerminal;
    }

    public void setDestinationTerminal(Terminal destinationTerminal) {
        this.destinationTerminal = destinationTerminal;
    }

    public Timestamp getEta() {
        return eta;
    }

    public void setEta(Timestamp eta) {
        this.eta = eta;
    }

    public Timestamp getDeadline() {
        return deadline;
    }

    public void setDeadline(Timestamp deadline) {
        this.deadline = deadline;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
