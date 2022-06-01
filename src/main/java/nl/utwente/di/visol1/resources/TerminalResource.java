package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.TerminalDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Terminal;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class TerminalResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    public TerminalResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    @DELETE
    public void deleteTerminal(){
        TerminalDao.deleteTerminal(id);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void replaceTerminal(JAXBElement<Terminal> terminal){
        TerminalDao.replaceTerminal(id, terminal);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Terminal getTerminal() {
        return TerminalDao.getTerminal(id);
    }

    @Path("/schedules")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Map<Integer, List<Schedule>> getSchedules(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
        return ScheduleDao.getSchedulesByTerminal(id, from, to);
    }

    @Path("/berths")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Berth> getBerths(){
        return BerthDao.getBerthsByTerminal(id);
    }

    @Path("/schedules/calculate")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Map<Integer, List<Schedule>> getOptimalSchedules(Map<Integer, List<Schedule>> oldSchedule){
        //TODO: THIS
        return null;
    }
}
