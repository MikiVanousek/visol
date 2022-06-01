package nl.utwente.di.visol1.resources;

import nl.utwente.di.visol1.dao.BerthDao;
import nl.utwente.di.visol1.dao.ScheduleDao;
import nl.utwente.di.visol1.models.Berth;
import nl.utwente.di.visol1.models.Schedule;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;
import java.sql.Timestamp;
import java.util.List;

public class BerthResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;
    public BerthResource(UriInfo uriInfo, Request request, String id){
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = Integer.parseInt(id);
    }

    @DELETE
    public void deleteBerth(){
        BerthDao.deleteBerth(id);
    }



    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void replaceBerth(JAXBElement<Berth> berth){
        BerthDao.replaceBerth(id, berth);
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Berth getBerth() {
        return BerthDao.getBerth(id);
    }

    @Path("/schedules")
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<Schedule> getSchedules(@QueryParam("from") Timestamp from, @QueryParam("to") Timestamp to){
       return ScheduleDao.getSchedulesByBerth(id, from, to);
    }
}
