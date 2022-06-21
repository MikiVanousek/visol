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
import nl.utwente.di.visol1.dao.VesselChangeDao;
import nl.utwente.di.visol1.models.VesselChange;

@Path("/changes/vessels")
public class VesselChangesResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Map<Integer, List<VesselChange>> getVesselChanges(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
		if (from == null) from = GenericDao.MIN_TIME;
		if(to == null) to = GenericDao.MAX_TIME;
		return VesselChangeDao.getVesselChanges(from, to);
	}

	@Path("{vessel_id}")
	public VesselChangeResource getVesselChange(@PathParam("vessel_id") String id) {
		return new VesselChangeResource(uriInfo, request, id);
	}
}
