package nl.utwente.di.visol1.resources;

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
    String id;
    public TerminalResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @DELETE
    public void deleteTerminal(){

    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void replaceTerminal(JAXBElement<Terminal> terminal){

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Terminal getTerminal() {
        return null;
    }

    @Path("/schedules")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Map<Integer, List<Schedule>> getSchedules(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
        return null;
    }

    @Path("/berths")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Berth> getBerths(){
        return null;
    }

    @Path("/schedules/calculate")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Map<Integer, List<Schedule>> getOptimalSchedules(Map<Integer, List<Schedule>> oldSchedule){
        return null;
    }
}
