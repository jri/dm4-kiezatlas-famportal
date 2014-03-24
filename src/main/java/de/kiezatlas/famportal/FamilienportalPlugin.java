package de.kiezatlas.famportal;

import de.kiezatlas.famportal.service.FamilienportalService;

import de.deepamehta.plugins.facets.service.FacetsService;
import de.deepamehta.plugins.facets.model.FacetValue;

import de.deepamehta.core.AssociationDefinition;
import de.deepamehta.core.osgi.PluginActivator;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.core.service.annotation.ConsumesService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import java.util.List;



@Path("/famportal")
@Consumes("application/json")
@Produces("application/json")
public class FamilienportalPlugin extends PluginActivator implements FamilienportalService {

    // ------------------------------------------------------------------------------------------------------- Constants

    private static final String FAMPORTAL_CATEGORY_URI       = "famportal.category";
    private static final String FAMPORTAL_CATEGORY_FACET_URI = "famportal.category.facet";

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private FacetsService facetsService;

    // -------------------------------------------------------------------------------------------------- Public Methods



    // ************************************
    // *** FacetsService Implementation ***
    // ************************************



    @PUT
    @Path("/category/{id}")
    @Override
    public void assignToFamportalCategory(@PathParam("id") long categoryId,
                                          @QueryParam("geo_object_id") List<Long> geoObjectIds) {
        // Prerequisite: categories are modeled per 1) Aggregation Def, 2) Cardinality Many
        FacetValue value = new FacetValue(FAMPORTAL_CATEGORY_URI).addRef(categoryId);
        for (long geoObjectId : geoObjectIds) {
            facetsService.updateFacet(geoObjectId, FAMPORTAL_CATEGORY_FACET_URI, value, null);  // clientState=null
        }
    }

    @DELETE
    @Path("/category/{id}")
    @Override
    public void removeFromFamportalCategory(@PathParam("id") long categoryId,
                                            @QueryParam("geo_object_id") List<Long> geoObjectIds) {
        // Prerequisite: categories are modeled per 1) Aggregation Def, 2) Cardinality Many
        FacetValue value = new FacetValue(FAMPORTAL_CATEGORY_URI).addDeletionRef(categoryId);
        for (long geoObjectId : geoObjectIds) {
            facetsService.updateFacet(geoObjectId, FAMPORTAL_CATEGORY_FACET_URI, value, null);  // clientState=null
        }
    }



    // ****************************
    // *** Hook Implementations ***
    // ****************************



    @Override
    @ConsumesService("de.deepamehta.plugins.facets.service.FacetsService")
    public void serviceArrived(PluginService service) {
        facetsService = (FacetsService) service;
    }

    @Override
    public void serviceGone(PluginService service) {
        facetsService = null;
    }
}
