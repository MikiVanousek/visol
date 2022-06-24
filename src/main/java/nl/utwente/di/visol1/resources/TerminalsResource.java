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

import nl.utwente.di.visol1.dao.TerminalDao;
import nl.utwente.di.visol1.models.Terminal;

@Path("/terminals")
public class TerminalsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@POST
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response createTerminal(@Context HttpServletRequest request, Terminal terminal) {
		Terminal createdTerminal = TerminalDao.createTerminal(terminal);
		if (createdTerminal != null) {
			return Response.status(Response.Status.CREATED)
				.header("Location", request.getRequestURI() + "/" +  createdTerminal.getId())
				.entity(createdTerminal).build();
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}

	}

	@Path("{terminal_id}")
	public TerminalResource getTerminal(@PathParam("terminal_id") String id) {
		return new TerminalResource(uriInfo, request, id);
	}
}
