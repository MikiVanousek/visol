package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Vessel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;


public class VesselResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    public VesselResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    @DELETE
    public void deleteVessel(){
        VesselDao.deleteVessel(id);
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void replaceVessel(Vessel vessel){
        VesselDao.replaceVessel(id, vessel);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vessel getVessel() {
        return VesselDao.getVessel(id);
    }

    @Path("/schedule")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Schedule getSchedule() {
        return ScheduleDao.getScheduleByVessel(id);
    }

    @Path("/schedule")
    @DELETE
    public void deleteSchedule(){

    }

    @Path("/schedule")
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Schedule replaceSchedule(Schedule schedule){
        return ScheduleDao.replaceSchedule(id, schedule);
    }
}
