package nl.utwente.di.visol1.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import nl.utwente.di.visol1.dao.GenericDao;
import nl.utwente.di.visol1.dao.ScheduleChangeDao;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.type_adapters.TimestampAdapter;

@Path("/changes/schedules")
public class ScheduleChangesResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer, List<ScheduleChange>> getScheduleChanges(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		return ScheduleChangeDao.getScheduleChanges(fromTime, toTime);
	}

	@Path("{vessel_id}")
	public ScheduleChangeResource getScheduleChange(@PathParam("vessel_id") String id) {
		return new ScheduleChangeResource(uriInfo, request, id);
	}
}
