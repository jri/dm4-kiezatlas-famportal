package de.kiezatlas.famportal;

import de.kiezatlas.famportal.service.FamilienportalService;
import de.kiezatlas.service.KiezatlasService;

import de.deepamehta.plugins.facets.service.FacetsService;
import de.deepamehta.plugins.facets.model.FacetValue;
import de.deepamehta.core.AssociationDefinition;
import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.Topic;
import de.deepamehta.core.model.SimpleValue;
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

import java.util.ArrayList;
import java.util.List;



@Path("/famportal")
@Consumes("application/json")
@Produces("application/json")
public class FamilienportalPlugin extends PluginActivator implements FamilienportalService {

    // ------------------------------------------------------------------------------------------------------- Constants

    private static final String FAMPORTAL_CATEGORY_URI        = "famportal.category";
    private static final String FAMPORTAL_CATEGORY_FACET_URI  = "famportal.category.facet";

    // The URIs of Familienportal Kategorie topics have this prefix.
    // The remaining part of the URI is the original Familienportal category XML ID.
    private static final String FAMPORTAL_CATEGORY_URI_PREFIX = "famportal.";

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private KiezatlasService kiezatlasService;
    private FacetsService facetsService;

    // -------------------------------------------------------------------------------------------------- Public Methods



    // ************************************
    // *** FacetsService Implementation ***
    // ************************************



    @GET
    @Path("/geoobject")
    @Override
    public List<GeoObject> getGeoObjects(@QueryParam("category") List<CategorySet> categorySets) {
        try {
            if (categorySets.size() > 1) {
                throw new RuntimeException("Queries with more than 1 \"category\" parameter not yet supported");
            }
            CategorySet categorySet = categorySets.get(0);
            if (categorySet.size() > 1) {
                throw new RuntimeException("More than 1 category per \"category\" parameter not yet supported");
            }
            //
            List<GeoObject> geoObjects = new ArrayList();
            for (String categoryXmlId : categorySet) {
                long catId = categoryTopic(categoryXmlId).getId();
                for (Topic geoObject : kiezatlasService.getGeoObjectsByCategory(catId)) {
                    geoObject.loadChildTopics("dm4.contacts.address");  // ### TODO: do NOT fetch the address's childs
                    geoObjects.add(createGeoObject(geoObject));
                }
            }
            return geoObjects;
        } catch (Exception e) {
            throw new RuntimeException("Fetching geo objects failed (categorySets=" + categorySets + ")", e);
        }
    }

    // ---

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
        "de.kiezatlas.service.KiezatlasService",
        "de.deepamehta.plugins.facets.service.FacetsService"
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



    // ------------------------------------------------------------------------------------------------- Private Methods

    private GeoObject createGeoObject(Topic geoObjectTopic) {
        GeoObject geoObject = new GeoObject();
        geoObject.setName(geoObjectTopic.getSimpleValue().toString());
        return geoObject;
    }

    /**
     * Returns the Familienportal Kategorie topic that corresponds to the original Familienportal category XML ID.
     */
    private Topic categoryTopic(String famportalCategoryXmlId) {
        Topic cat = dms.getTopic("uri", new SimpleValue(FAMPORTAL_CATEGORY_URI_PREFIX + famportalCategoryXmlId), false);
        if (cat == null) {
            throw new RuntimeException("\"" + famportalCategoryXmlId + "\" is an unknown Familienportal category " +
                "XML ID (no corresponding topic found)");
        }
        return cat;
    }
}
