package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Schedule {
    private int vesselId;
    private int berthId;
    boolean manual;
    Timestamp start;
    Timestamp finish;

    public Schedule(int vesselId, int berthId, boolean manual, Timestamp start, Timestamp finish) {
        this.vesselId = vesselId;
        this.berthId = berthId;
        this.manual = manual;
        this.start = start;
        this.finish = finish;
    }

    public Schedule() {
        // Empty constructor
    }

    public int getVesselId() {
        return vesselId;
    }

    public void setVesselId(int vesselId) {
        this.vesselId = vesselId;
    }

    public Vessel getVessel() {
        return null; // VesselDao.getById(vesselId);
    }

    public void setVessel(Vessel vessel) {
        this.vesselId = vessel.getId();
    }

    public int getBerthId() {
        return berthId;
    }

    public void setBerthId(int berthId) {
        this.berthId = berthId;
    }

    public Berth getBerth() {
        return null; // BerthDao.getById(berthId);
    }

    public void setBerth(Berth berth) {
        this.berthId = berth.getId();
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getFinish() {
        return finish;
    }

    public void setFinish(Timestamp finish) {
        this.finish = finish;
    }
}
