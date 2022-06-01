package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Vessel;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

@Path("/vessels")
public class VesselsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Vessel createVessel(JAXBElement<Vessel> vessel){
        return VesselDao.createVessel(vessel);
    }

    @Path("{vessel_id}")
    public VesselResource getVessel(@PathParam("vessel_id") String id) {
        return new VesselResource(uriInfo, request, id);
    }
}
