package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ScheduleChange {
	private int vessel;
	private Timestamp date;
	@JsonProperty("old")
	@XmlElement(name = "old")
	private String oldSchedule;
	@JsonProperty("new")
	@XmlElement(name = "new")
	private String newSchedule;
	private String reason;

	public ScheduleChange(){
		//Empty Constructor
	}

	public ScheduleChange(int vessel, Timestamp date, String oldSchedule, String newSchedule, String reason) {
		this.vessel = vessel;
		this.date = date;
		this.oldSchedule = oldSchedule;
		this.newSchedule = newSchedule;
		this.reason = reason;
	}

	public ScheduleChange(int vessel, Timestamp date, String oldSchedule, JSONPObject newSchedule, String reason) {
		this.vessel = vessel;
		this.date = date;
		this.oldSchedule = oldSchedule;
		this.newSchedule = newSchedule.toString();
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

	public String getOldSchedule() {
		return oldSchedule;
	}

	public void setOldSchedule(String oldSchedule) {
		this.oldSchedule = oldSchedule;
	}

	public String getNewSchedule() {
		return newSchedule;
	}

	public void setNewSchedule(String newSchedule) {
		this.newSchedule = newSchedule;
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
		ScheduleChange that = (ScheduleChange) o;
		return vessel == that.vessel && date.equals(that.date) && Objects.equals(oldSchedule, that.oldSchedule) && newSchedule.equals(that.newSchedule)
		       && Objects.equals(reason, that.reason);
	}

	@Override
	public int hashCode() {
		return Objects.hash(vessel, date, oldSchedule, newSchedule, reason);
	}
}
