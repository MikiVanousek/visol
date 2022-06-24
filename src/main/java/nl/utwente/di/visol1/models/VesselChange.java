package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VesselChange {
	private int vessel;
	private Timestamp date;
	@JsonProperty("old")
	@XmlElement(name = "old")
	private Vessel oldVessel;
	@JsonProperty("new")
	@XmlElement(name = "new")
	private Vessel newVessel;
	private String reason;

	public VesselChange(){
		//Empty Constructor
	}

	public VesselChange(Vessel vessel, String reason){
		//Constructor to call with replacing or creating a vessel
		this.vessel = vessel.getId();
		this.oldVessel = new Vessel();
		this.newVessel = vessel;
		this.reason = reason;
	}

	public VesselChange(int vessel, Timestamp date, String oldVessel, String newVessel, String reason) {
		ObjectMapper mapper = new ObjectMapper();
		this.vessel = vessel;
		this.date = date;
		try {
			if (oldVessel == null || oldVessel.equals("")) {
				this.oldVessel = new Vessel();
			} else {
				this.oldVessel = mapper.readValue(oldVessel, Vessel.class);
			}
			this.newVessel = mapper.readValue(newVessel, Vessel.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.reason = reason;
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

	public Vessel getOldVessel() {
		return oldVessel;
	}

	public void setOldVessel(Vessel oldVessel) {
		this.oldVessel = oldVessel;
	}

	public Vessel getNewVessel() {
		return newVessel;
	}

	public void setNewVessel(Vessel newVessel) {
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
