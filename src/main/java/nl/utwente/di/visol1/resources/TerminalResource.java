package nl.utwente.di.visol1.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.GenericDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.TerminalDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Performance;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.Terminal;
import nl.utwente.di.visol1.models.Vessel;
import nl.utwente.di.visol1.optimise.OptimiseSchedule;
import nl.utwente.di.visol1.type_adapters.TimestampAdapter;

public class TerminalResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;

	public TerminalResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = Integer.parseInt(id);
	}

	@DELETE
	public Response deleteTerminal() {
		int i = TerminalDao.deleteTerminal(id);
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response replaceTerminal(Terminal terminal) {
		int i = TerminalDao.replaceTerminal(id, terminal);
		if (i == -1) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} else if (i == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.status(Response.Status.OK).entity(TerminalDao.getTerminal(id)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTerminal() {

		Terminal res = TerminalDao.getTerminal(id);
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
	public Map<Integer, List<Schedule>> getSchedules(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		return ScheduleDao.getSchedulesByTerminal(id, fromTime, toTime);
	}

	@Path("/berths")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer, Berth> getBerths() {
		return BerthDao.getBerthsByTerminal(id);
	}

	@Path("/schedules/optimise")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOptimalSchedules(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		OptimiseSchedule.optimisePlanning(fromTime, toTime, id);
		return Response.status(Response.Status.OK).build();
	}

	@Path("/performance")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Performance getPerformance(@QueryParam("from") String from, @QueryParam("to") String to) {
		Timestamp fromTime = TimestampAdapter.unadapt(from);
		Timestamp toTime = TimestampAdapter.unadapt(to);
		if (fromTime == null) fromTime = GenericDao.MIN_TIME;
		if (toTime == null) toTime = GenericDao.MAX_TIME;
		Map<Integer, Vessel> vesselMap = VesselDao.getVesselsByTerminal(id, fromTime, toTime);
		int unscheduledVessels = VesselDao.getUnscheduledVesselsByTerminal(id, fromTime, toTime);
		int scheduledVessels = vesselMap.size() - unscheduledVessels;
		double totalCost = 0;
		for (int i : vesselMap.keySet()){
			Schedule schedule = ScheduleDao.getScheduleByVessel(i);
			if(schedule == null) continue;
			double hours = (schedule.getExpectedEnd().getTime() - schedule.getStart().getTime()) / 3600000.0;
			totalCost += hours * vesselMap.get(i).getCostPerHour();
		}

		return new Performance(totalCost, scheduledVessels, unscheduledVessels);
	}

	@Path("/vessels")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer, Vessel> getVessels(@QueryParam("deadline_after") String deadline_after, @QueryParam("arrival_before") String arrival_before) {
		Timestamp deadlineAfter = TimestampAdapter.unadapt(deadline_after);
		Timestamp arrivalBefore = TimestampAdapter.unadapt(arrival_before);
		if (deadlineAfter == null) deadlineAfter = GenericDao.MIN_TIME;
		if (arrivalBefore == null) arrivalBefore = GenericDao.MAX_TIME;
		return VesselDao.getVesselsByTerminal(id, deadlineAfter, arrivalBefore);
	}

}
