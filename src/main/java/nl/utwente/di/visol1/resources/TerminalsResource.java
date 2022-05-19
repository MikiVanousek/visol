package nl.utwente.di.visol1.resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
@Path("/terminals")
public class TerminalsResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    @POST
    //@Produces something
    public void createTerminal(){

    }

    @Path("{terminal_id}")
    public TerminalResource getTerminal(@PathParam("terminal_id") String id) {
        return new TerminalResource(uriInfo, request, id);
    }
}
