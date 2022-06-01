package nl.utwente.di.visol1.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.models.*;

import java.util.List;
@Path("/berths")
public class BerthsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Berth createBerth(JAXBElement<Berth> berth){
        return BerthDao.createBerth(berth);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Berth> getBerths() {
        return BerthDao.getBerths();
    }

    @Path("{berth_id}")
    public BerthResource getBerth(@PathParam("berth_id") String id) {
        return new BerthResource(uriInfo, request, id);
    }
}
