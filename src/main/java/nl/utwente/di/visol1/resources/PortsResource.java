package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.PortDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Port;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.util.List;

@Path("/ports")
public class PortsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Port createPort(Port port){
        return PortDao.createPort(port);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Port> getPorts() {
        return PortDao.getPorts();
    }

    @Path("{port_id}")
    public PortResource getPort(@PathParam("port_id") String id) {
        return new PortResource(uriInfo, request, id);
    }
}
