package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.PortDao;
import nl.utwente.di.visol1.dao.VesselChangeDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Port;
import nl.utwente.di.visol1.models.Vessel;
import nl.utwente.di.visol1.models.VesselChange;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("/vessels")
public class VesselsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVessel(Vessel vessel){
	    Vessel createdVessel = VesselDao.createVessel(vessel);
	    if (createdVessel != null) {
			vessel.setId(createdVessel.getId());
			VesselChange vchange = new VesselChange(vessel, "creation of new vessel");
		    VesselChangeDao.createVesselChange(vchange);
		    return Response.status(Response.Status.OK)
			    .header("Location", "/vessels/" + createdVessel.getId())
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
