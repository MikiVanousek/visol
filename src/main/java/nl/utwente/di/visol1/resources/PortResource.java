package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Port;
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

public class PortResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;
    public PortResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @DELETE
    public void deletePort(){

    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void replacePort(JAXBElement<Port> port){

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Port getPort() {
        return null;
    }

    @Path("/schedules")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Map<Integer, Map<Integer, List<Schedule>>> getSchedules(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
        return null;
    }

    @Path("/terminals")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Terminal> getTerminals(){
        return null;
    }

}
