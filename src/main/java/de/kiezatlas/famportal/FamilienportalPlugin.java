package de.kiezatlas.famportal;

import de.kiezatlas.famportal.service.FamilienportalService;

import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.osgi.PluginActivator;
import de.deepamehta.core.service.ResultList;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;



@Path("/famportal")
@Consumes("application/json")
@Produces("application/json")
public class FamilienportalPlugin extends PluginActivator implements FamilienportalService {

    @GET
    @Path("/geoobject/category/{id}")
    @Override
    public ResultList<RelatedTopic> getGeoObjectsByCategory(@PathParam("id") long famportalCategoryId) {
        return dms.getTopic(famportalCategoryId, false).getRelatedTopics("dm4.core.aggregation", "dm4.core.child",
            "dm4.core.parent", "dm4.kiezatlas.geo_object", false, false, 0);
    }
}
