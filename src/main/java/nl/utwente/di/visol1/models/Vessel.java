package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Objects;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Vessel {
    private int id;
    private String name;
    private int containers;
    private int destination;
    private Timestamp arrival;
    private Timestamp deadline;
	@XmlElement(name = "cost_per_hour")
	  private double costPerHour;
    private int width;
    private int length;
    private int depth;

    public Vessel(int id, String name, Timestamp arrival, Timestamp deadline, int containers, double costPerHour, int destination, int length, int width, int depth) {
        this.id = id;
        this.name = name;
        this.containers = containers;
        this.destination = destination;
        this.arrival = arrival;
        this.deadline = deadline;
        this.width = width;
        this.length = length;
        this.depth = depth;
				this.costPerHour = costPerHour;
    }

    public Vessel() {
        // Empty constructor
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

	public int getContainers() {
		return containers;
	}

	public void setContainers(int containers) {
		this.containers = containers;
	}

	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public Timestamp getArrival() {
		return arrival;
	}

	public void setArrival(Timestamp arrival) {
		this.arrival = arrival;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public double getCostPerHour() {
		return costPerHour;
	}

	public void setCostPerHour(double costPerHour) {
		this.costPerHour = costPerHour;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Vessel)) return false;

		Vessel vessel = (Vessel) o;
		return id == vessel.id && containers == vessel.containers && destination == vessel.destination
		       && Double.compare(costPerHour, vessel.costPerHour) == 0 && width == vessel.width && length == vessel.length && depth == vessel.depth
		       && Objects.equals(name, vessel.name) && Objects.equals(arrival, vessel.arrival) && Objects.equals(deadline, vessel.deadline);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, containers, destination, arrival, deadline, costPerHour, width, length, depth);
	}
}
