package de.kiezatlas.famportal;

import de.kiezatlas.famportal.service.FamilienportalService;

import de.deepamehta.plugins.facets.service.FacetsService;
import de.deepamehta.plugins.facets.model.FacetValue;
import de.deepamehta.plugins.kiezatlas.service.KiezatlasService;

import de.deepamehta.core.AssociationDefinition;
import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.Topic;
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

    private KiezatlasService kiezatlasService;
    private FacetsService facetsService;

    // -------------------------------------------------------------------------------------------------- Public Methods



    // ************************************
    // *** FacetsService Implementation ***
    // ************************************



    @PUT
    @Path("/category/{id}")
    @Override
    public void createAssignments(@PathParam("id") long famportalCategoryId,
                                  @QueryParam("ka_cat") List<Long> kiezatlasCategoryIds) {
        // Prerequisite: categories are modeled per 1) Aggregation Def, 2) Cardinality Many
        FacetValue value = new FacetValue(FAMPORTAL_CATEGORY_URI).addRef(famportalCategoryId);
        for (long catId : kiezatlasCategoryIds) {
            List<RelatedTopic> geoObjects = kiezatlasService.getGeoObjectsByCategory(catId);
            for (Topic geoObject : geoObjects) {
                facetsService.updateFacet(geoObject, FAMPORTAL_CATEGORY_FACET_URI, value, null, null);
                                                                               // clientState=null, directives=null
            }
        }
    }

    @DELETE
    @Path("/category/{id}")
    @Override
    public void deleteAssignments(@PathParam("id") long famportalCategoryId,
                                  @QueryParam("geo_object") List<Long> geoObjectIds) {
        // Prerequisite: categories are modeled per 1) Aggregation Def, 2) Cardinality Many
        FacetValue value = new FacetValue(FAMPORTAL_CATEGORY_URI).addDeletionRef(famportalCategoryId);
        for (long geoObjectId : geoObjectIds) {
            facetsService.updateFacet(geoObjectId, FAMPORTAL_CATEGORY_FACET_URI, value, null);  // clientState=null
        }
    }



    // ****************************
    // *** Hook Implementations ***
    // ****************************



    @Override
    @ConsumesService({
        "de.deepamehta.plugins.facets.service.FacetsService",
        "de.deepamehta.plugins.kiezatlas.service.KiezatlasService"
    })
    public void serviceArrived(PluginService service) {
        if (service instanceof FacetsService) {
            facetsService = (FacetsService) service;
        } else if (service instanceof KiezatlasService) {
            kiezatlasService = (KiezatlasService) service;
        }
    }

    @Override
    public void serviceGone(PluginService service) {
        if (service == facetsService) {
            facetsService = null;
        } else if (service == kiezatlasService) {
            kiezatlasService = null;
        }
    }
}
