package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.PortDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.TerminalDao;
import nl.utwente.di.visol1.models.Port;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Terminal;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class PortResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    public PortResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    @DELETE
    public void deletePort(){
        PortDao.deletePort(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void replacePort(Port port){
        PortDao.replacePort(id, port);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Port getPort() {
        return PortDao.getPort(id);
    }

    @Path("/schedules")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Map<Integer, Map<Integer, List<Schedule>>> getSchedules(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
        return ScheduleDao.getSchedulesByPort(id, from, to);

    }

    @Path("/terminals")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Terminal> getTerminals(){
        return TerminalDao.getTerminalsByPort(id);
    }

}
