package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.PortDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Port;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Vessel;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
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
    public Response deleteVessel(){
		int i = VesselDao.deleteVessel(id);
	    if (i != 0){
		    return Response.status(Response.Status.NO_CONTENT).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }

    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response replaceVessel(Vessel vessel){
	    int i = VesselDao.replaceVessel(id, vessel);
	    if (i != 0){
		    return Response.status(Response.Status.OK).entity(VesselDao.getVessel(id)).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVessel() {
			Vessel res = VesselDao.getVessel(id);
	    if (res!= null) {
		    return Response.status(Response.Status.OK)
			    .entity(res).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }
    }

    @Path("/schedule")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSchedule() {
	    Schedule schedule = ScheduleDao.getScheduleByVessel(id);
	    if (schedule != null) {
		    return Response.status(Response.Status.OK)
			    .entity(schedule).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }
    }

    @Path("/schedule")
    @DELETE
    public Response deleteSchedule(){
			int i = ScheduleDao.deleteScheduleByVessel(id);
	    if (i != 0){
		    return Response.status(Response.Status.NO_CONTENT).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }

    }

    @Path("/schedule")
    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Schedule replaceSchedule(Schedule schedule){
        return ScheduleDao.replaceSchedule(id, schedule);
    }
}
