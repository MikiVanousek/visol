package nl.utwente.di.visol1.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/vessels")
public class VesselsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    //@Produces something
    public void createVessel(){

    }

    @Path("{vessel_id}")
    public VesselResource getVessel(@PathParam("vessel_id") String id) {
        return new VesselResource(uriInfo, request, id);
    }
}
