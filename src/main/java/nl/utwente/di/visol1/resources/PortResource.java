package nl.utwente.di.visol1.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import nl.utwente.di.visol1.dao.GenericDao;
import nl.utwente.di.visol1.dao.PortDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.TerminalDao;
import nl.utwente.di.visol1.models.Port;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Terminal;
import nl.utwente.di.visol1.type_adapters.TimestampAdapter;

public class PortResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;

	public PortResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = Integer.parseInt(id);
	}

	@DELETE
	public Response deletePort() {
		int i = PortDao.deletePort(id);
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response replacePort(Port port) {
		int i = PortDao.replacePort(id, port);
		if (i != 0) {
			return Response.status(Response.Status.OK).entity(PortDao.getPort(id)).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPort() {
		Port res = PortDao.getPort(id);
		if (res != null) {
			return Response.status(Response.Status.OK)
				.entity(res).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("/schedules")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer, Map<Integer, List<Schedule>>> getSchedules(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		return ScheduleDao.getSchedulesByPort(id, fromTime, toTime);

	}

	@Path("/terminals")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer, Terminal> getTerminals() {
		return TerminalDao.getTerminalsByPort(id);
	}

	@Path("/performance")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Port getPerformance() {
		//TODO: THIS
		return null;
	}
}
