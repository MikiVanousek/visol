package nl.utwente.di.visol1.models;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@XmlRootElement
public class Schedule {
    private Future<Vessel> vessel;
    private Future<Berth> berth;
    boolean manual;
    Timestamp start;
    Timestamp finish;

    public Schedule(Future<Vessel> vessel, Future<Berth> berth, boolean manual, Timestamp start, Timestamp finish) {
        this.vessel = vessel;
        this.berth = berth;
        this.manual = manual;
        this.start = start;
        this.finish = finish;
    }

    public Vessel getVessel() {
        try {
            // TODO make sure it's only requested once
            return vessel.get();
        } catch (InterruptedException | ExecutionException exception) {
            // TODO
            return null;
        }
    }

    public void setVessel(Vessel vessel) {
        // TODO set in API
        this.vessel = new FutureTask<>(() -> {
            String url = "<root_api_url>/vessels/" + vessel.getId();
            return null; // TODO get from API at url
        });
    }

    public Berth getBerth() {
        try {
            // TODO make sure it's only requested once
            return berth.get();
        } catch (InterruptedException | ExecutionException exception) {
            // TODO
            return null;
        }
    }

    public void setBerth(Berth berth) {
        // TODO set in API
        this.berth = new FutureTask<>(() -> {
            String url = "<root_api_url>/berths/" + berth.getId();
            return null; // TODO get from API at url
        });
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
