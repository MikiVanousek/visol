package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VesselChange {
	private int vessel;
	private Timestamp date;
	@JsonProperty("old")
	@XmlElement(name = "old")
	private String oldVessel;
	@JsonProperty("new")
	@XmlElement(name = "new")
	private String newVessel;
	private String reason;

	public VesselChange(){
		//Empty Constructor
	}

	public int getVessel() {
		return vessel;
	}

	public void setVessel(int vessel) {
		this.vessel = vessel;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getOldVessel() {
		return oldVessel;
	}

	public void setOldVessel(String oldVessel) {
		this.oldVessel = oldVessel;
	}

	public String getNewVessel() {
		return newVessel;
	}

	public void setNewVessel(String newVessel) {
		this.newVessel = newVessel;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		VesselChange that = (VesselChange) o;
		return vessel == that.vessel && date.equals(that.date) && Objects.equals(oldVessel, that.oldVessel) && newVessel.equals(that.newVessel)
		       && Objects.equals(reason, that.reason);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vessel, date, oldVessel, newVessel, reason);
	}
}
