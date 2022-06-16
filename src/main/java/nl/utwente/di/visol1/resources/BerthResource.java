package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.GenericDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.util.List;

public class BerthResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    public BerthResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    @DELETE
    public Response deleteBerth(){
        int i = BerthDao.deleteBerth(id);
		if (i != 0){
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
    }


    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response replaceBerth(Berth berth){
        int i = BerthDao.replaceBerth(id, berth);
	    if (i != 0){
		    return Response.status(Response.Status.OK).entity(BerthDao.getBerth(id)).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBerth() {

			Berth res = BerthDao.getBerth(id);
	    if (res!= null) {
		    return Response.status(Response.Status.OK)
			    .entity(res).build();
	    } else {
		    return Response.status(Response.Status.NOT_FOUND).build();
	    }
    }

    @Path("/schedules")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Schedule> getSchedules(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
	      if (from == null) from = GenericDao.MIN_TIME;
				if(to == null) to = GenericDao.MAX_TIME;

	    return ScheduleDao.getSchedulesByBerth(id, from, to);

    }
}
