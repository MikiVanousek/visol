package nl.utwente.di.visol1.resources;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.util.List;

import nl.utwente.di.visol1.dao.GenericDao;
import nl.utwente.di.visol1.dao.ScheduleChangeDao;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.type_adapters.TimestampAdapter;


public class ScheduleChangeResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;

	public ScheduleChangeResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = Integer.parseInt(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ScheduleChange> getScheduleChanges(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		return ScheduleChangeDao.getScheduleChangesByVessel(id, fromTime, toTime);
	}

	@DELETE
	public Response deleteScheduleChanges(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		int i = ScheduleChangeDao.deleteScheduleChanges(id, fromTime, toTime);
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("{date}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getScheduleChangeByDate(@PathParam("date") String date) {
		ScheduleChange schange = ScheduleChangeDao.getScheduleChangeByDate(id, TimestampAdapter.unadapt(date));
		if (schange != null) {
			return Response.status(Response.Status.OK)
				.entity(schange).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("{date}")
	@DELETE
	public Response deleteScheduleChangeByDate(@PathParam("date") String date) {
		int i = ScheduleChangeDao.deleteScheduleChangeByDate(id, TimestampAdapter.unadapt(date));
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}
}
