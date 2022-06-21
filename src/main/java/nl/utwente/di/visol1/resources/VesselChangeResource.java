package nl.utwente.di.visol1.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.sql.Timestamp;
import java.util.List;

import nl.utwente.di.visol1.dao.GenericDao;
import nl.utwente.di.visol1.dao.VesselChangeDao;
import nl.utwente.di.visol1.models.VesselChange;

public class VesselChangeResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;
	public VesselChangeResource(UriInfo uriInfo, Request request, String id){
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = Integer.parseInt(id);
	}

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces(MediaType.APPLICATION_JSON)
	public Response createVesselChange(VesselChange vchange){
		vchange.setVessel(id);
		VesselChange createdChange = VesselChangeDao.createVesselChange(vchange);
		if (createdChange != null){
			return Response.status(Response.Status.OK).entity(createdChange).build();
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<VesselChange> getVesselChanges(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
		if (from == null) from = GenericDao.MIN_TIME;
		if(to == null) to = GenericDao.MAX_TIME;
		return VesselChangeDao.getVesselChangesByVessel(id, from, to);
	}

	@DELETE
	public Response deleteVesselChanges(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
		if (from == null) from = GenericDao.MIN_TIME;
		if(to == null) to = GenericDao.MAX_TIME;
		int i = VesselChangeDao.deleteVesselChanges(id, from, to);
		if (i != 0){
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("{date}")
	@GET
	public Response getVesselChangeByDate(@PathParam("date") String date) {
		VesselChange vchange = VesselChangeDao.getVesselChangeByDate(id, Timestamp.valueOf(date));
		if (vchange != null) {
			return Response.status(Response.Status.OK)
				.entity(vchange).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("{date}")
	@DELETE
	public Response deleteVesselChangeByDate(@PathParam("date") String date) {
		int i = VesselChangeDao.deleteVesselChangeByDate(id, Timestamp.valueOf(date));
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
