package nl.utwente.di.visol1.resources;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import java.sql.Timestamp;

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.ScheduleChangeDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.dao.VesselChangeDao;
import nl.utwente.di.visol1.dao.VesselDao;
import nl.utwente.di.visol1.models.Schedule;
import nl.utwente.di.visol1.models.ScheduleChange;
import nl.utwente.di.visol1.models.Vessel;
import nl.utwente.di.visol1.models.VesselChange;
import nl.utwente.di.visol1.optimise.OptimiseSchedule;


public class VesselResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;

	public VesselResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = Integer.parseInt(id);
	}

	@DELETE
	public Response deleteVessel() {
		int i = VesselDao.deleteVessel(id);
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}

	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response replaceVessel(@Context HttpServletRequest request, Vessel vessel) {
		String reason = request.getHeader("reason");
		Vessel oldvessel = VesselDao.getVessel(id);
		int i = VesselDao.replaceVessel(id, vessel);
		if (i == -1) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} else if (i == 0) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			vessel.setId(id);
			VesselChange vchange = new VesselChange(vessel, reason);
			vchange.setOldVessel(oldvessel);
			VesselChangeDao.createVesselChange(vchange);
			return Response.status(Response.Status.OK).entity(VesselDao.getVessel(id)).build();
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getVessel() {
		Vessel res = VesselDao.getVessel(id);
		if (res != null) {
			return Response.status(Response.Status.OK)
				.entity(res).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("/schedule")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSchedule() {
		Schedule schedule = ScheduleDao.getScheduleByVessel(id);
		if (schedule != null) {
			return Response.status(Response.Status.OK)
				.entity(schedule).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}
	}

	@Path("/schedule")
	@DELETE
	public Response deleteSchedule() {
		int i = ScheduleDao.deleteScheduleByVessel(id);
		if (i != 0) {
			return Response.status(Response.Status.NO_CONTENT).build();
		} else {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

	}

	@Path("/schedule")
	@PUT
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response replaceSchedule(@Context HttpServletRequest request, Schedule schedule) {
		String reason = request.getHeader("reason");
		Schedule oldSchedule = ScheduleDao.getScheduleByVessel(id);
		if(schedule.isManual()) {

		} else {

		}
		Schedule createdSchedule = ScheduleDao.replaceSchedule(id, schedule);
		if (createdSchedule != null) {
			schedule.setVessel(id);
			ScheduleChange schange = new ScheduleChange(schedule, reason);
			schange.setOldSchedule(oldSchedule);
			ScheduleChangeDao.createScheduleChange(schange);
			return Response.status(Response.Status.OK).entity(createdSchedule).build();
		} else {
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		}
	}
}
