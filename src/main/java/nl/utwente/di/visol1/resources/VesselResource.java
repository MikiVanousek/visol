package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Vessel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.sql.Timestamp;
import java.util.List;

public class VesselResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    String id;
    public VesselResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
    }

    @DELETE
    public void deleteVessel(){

    }

    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void replaceVessel(JAXBElement<Vessel> vessel){

    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vessel getVessel() {
        return null;
    }

    @Path("/schedule")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Schedule> getSchedule() {
        return null;
    }

    @Path("/schedule")
    @DELETE
    public void deleteSchedule(){

    }

    @Path("/schedule")
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void replaceSchedule(JAXBElement<Schedule> schedule){

    }
}
