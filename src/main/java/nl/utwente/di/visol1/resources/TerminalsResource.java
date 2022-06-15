package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.TerminalDao;
import nl.utwente.di.visol1.models.Terminal;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

@Path("/terminals")
public class TerminalsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Terminal createTerminal(Terminal terminal){
        return TerminalDao.createTerminal(terminal);
    }

    @Path("{terminal_id}")
    public TerminalResource getTerminal(@PathParam("terminal_id") String id) {
        return new TerminalResource(uriInfo, request, id);
    }
}
