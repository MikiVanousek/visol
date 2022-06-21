package nl.utwente.di.visol1.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.models.*;

@Path("/berths")
public class BerthsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBerth(Berth berth) {
	    Berth createdBerth = BerthDao.createBerth(berth);
			if (createdBerth != null) {
				return Response.status(Response.Status.CREATED)
					.header("Location", "/rest/berths/" + createdBerth.getId())
					.entity(createdBerth).build();
			} else {
				return Response.status(Response.Status.NOT_ACCEPTABLE).build();
			}
    }

    @Path("{berth_id}")
    public BerthResource getBerth(@PathParam("berth_id") String id) {
        return new BerthResource(uriInfo, request, id);
    }
}
