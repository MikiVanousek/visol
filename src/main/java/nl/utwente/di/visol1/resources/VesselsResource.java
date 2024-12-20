package nl.utwente.di.visol1.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.visol1.dao.VesselChangeDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Vessel;
import nl.utwente.di.visol1.models.VesselChange;

@Path("/vessels")
public class VesselsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response createVessel(@Context HttpServletRequest request, Vessel vessel) {
		Vessel createdVessel = VesselDao.createVessel(vessel);
		if (createdVessel != null) {
			vessel.setId(createdVessel.getId());
			VesselChange vchange = new VesselChange(vessel, "creation of new vessel");
			VesselChangeDao.createVesselChange(vchange);
			return Response.status(Response.Status.CREATED)
				.header("Location", request.getRequestURI() + "/" +  createdVessel.getId())
				.entity(createdVessel).build();
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
	}

	@Path("{vessel_id}")
	public VesselResource getVessel(@PathParam("vessel_id") String id) {
		return new VesselResource(uriInfo, request, id);
	}

}
