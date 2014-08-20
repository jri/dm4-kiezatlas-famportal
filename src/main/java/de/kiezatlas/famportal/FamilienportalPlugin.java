package de.kiezatlas.famportal;

import de.kiezatlas.famportal.service.FamilienportalService;
import de.kiezatlas.service.KiezatlasService;

import de.deepamehta.plugins.facets.service.FacetsService;
import de.deepamehta.plugins.facets.model.FacetValue;
import de.deepamehta.plugins.geomaps.service.GeomapsService;
import de.deepamehta.plugins.geomaps.model.GeoCoordinate;

import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.Topic;
import de.deepamehta.core.model.SimpleValue;
import de.deepamehta.core.osgi.PluginActivator;
import de.deepamehta.core.service.Directives;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.core.service.annotation.ConsumesService;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;



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

    // The URIs of KA2 Bezirksregion topics have this prefix.
    // The remaining part of the URI is the original KA1 map alias.
    private static final String KA2_BEZIRKSREGION_URI_PREFIX = "ka2.bezirksregion.";

    // The URIs of KA2 Bezirk topics have this prefix.
    // The remaining part of the URI is the original KA1 overall map alias.
    private static final String KA2_BEZIRK_URI_PREFIX = "ka2.bezirk.";

    // The URIs of KA2 Geo Object topics have this prefix.
    // The remaining part of the URI is the original KA1 topic id.
    private static final String KA2_GEO_OBJECT_URI_PREFIX = "de.kiezatlas.topic.";

    private static final String KA1_MAP_URL = "http://www.kiezatlas.de/map/%s/p/%s";

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private KiezatlasService kiezatlasService;
    private GeomapsService geomapsService;
    private FacetsService facetsService;

    private Logger logger = Logger.getLogger(getClass().getName());

    // -------------------------------------------------------------------------------------------------- Public Methods



    // ************************************
    // *** FacetsService Implementation ***
    // ************************************



    // --- Retrieval API ---

    @GET
    @Path("/geoobject")
    @Override
    public List<GeoObject> getGeoObjects(@QueryParam("category") List<CategorySet> categorySets,
                                         @QueryParam("proximity") ProximityFilter proximityFilter) {
        try {
            if (categorySets.size() == 0) {
                throw new RuntimeException("Missing the \"category\" parameter in request");
            } else if (categorySets.size() > 1) {
                throw new RuntimeException("Requests with more than 1 \"category\" parameter not yet supported");
            }
            CategorySet categorySet = categorySets.get(0);
            if (categorySet.size() > 1) {
                throw new RuntimeException("More than 1 category per \"category\" parameter not yet supported");
            }
            //
            List<GeoObject> geoObjects = new ArrayList();
            for (String categoryXmlId : categorySet) {
                long catId = categoryTopic(categoryXmlId).getId();
                for (Topic geoObjectTopic : kiezatlasService.getGeoObjectsByCategory(catId, false)) {
                                                                                         // fetchComposite=false
                    try {
                        GeoCoordinate geoCoord = geoCoordinate(geoObjectTopic);
                        // apply proximity filter
                        if (proximityFilter != null) {
                            double distance = geomapsService.getDistance(geoCoord, proximityFilter.geoCoordinate);
                            if (distance > proximityFilter.radius) {
                                continue;
                            }
                        }
                        // add geo object to result
                        geoObjects.add(createGeoObject(geoObjectTopic, geoCoord));
                    } catch (Exception e) {
                        logger.warning("### Excluding geo object " + geoObjectTopic.getId() + " (\"" +
                            geoObjectTopic.getSimpleValue() + "\") from result (" + e + ")");
                    }
                }
            }
            return geoObjects;
        } catch (Exception e) {
            throw new RuntimeException("Fetching geo objects failed (categorySets=" + categorySets + ")", e);
        }
    }

    // --- Redationalwerkzeug ---

    @PUT
    @Path("/category/{id}")
    @Override
    public void createAssignments(@PathParam("id") long famportalCategoryId,
                                  @QueryParam("geo_object") List<Long> geoObjectIds) {
        updateFacet(geoObjectIds, createFacetValue(famportalCategoryId));
    }

    @PUT
    @Path("/category/{id}/ka_cat")
    @Override
    public void createAssignmentsByCategories(@PathParam("id") long famportalCategoryId,
                                              @QueryParam("ka_cat") List<Long> kiezatlasCategoryIds) {
        updateFacetByCategories(kiezatlasCategoryIds, createFacetValue(famportalCategoryId));
    }

    // ---

    @DELETE
    @Path("/category/{id}")
    @Override
    public void deleteAssignments(@PathParam("id") long famportalCategoryId,
                                  @QueryParam("geo_object") List<Long> geoObjectIds) {
        updateFacet(geoObjectIds, createDeletionFacetValue(famportalCategoryId));
    }

    @DELETE
    @Path("/category/{id}/ka_cat")
    @Override
    public void deleteAssignmentsByCategories(@PathParam("id") long famportalCategoryId,
                                              @QueryParam("ka_cat") List<Long> kiezatlasCategoryIds) {
        updateFacetByCategories(kiezatlasCategoryIds, createDeletionFacetValue(famportalCategoryId));
    }

    // ---

    @GET
    @Path("/count")
    @Override
    public GeoObjectCount countAssignments() {
        GeoObjectCount count = new GeoObjectCount();
        for (Topic famportalCategory : dms.getTopics(FAMPORTAL_CATEGORY_URI, false, 0).getItems()) {
            long famCatId = famportalCategory.getId();
            count.addCount(famCatId, kiezatlasService.getGeoObjectsByCategory(famCatId, false).size());
                                                                                     // fetchComposite=false
        }
        return count;
    }



    // ****************************
    // *** Hook Implementations ***
    // ****************************



    @Override
    @ConsumesService({KiezatlasService.class, GeomapsService.class, FacetsService.class})
    public void serviceArrived(PluginService service) {
        if (service instanceof KiezatlasService) {
            kiezatlasService = (KiezatlasService) service;
        } else if (service instanceof GeomapsService) {
            geomapsService = (GeomapsService) service;
        } else if (service instanceof FacetsService) {
            facetsService = (FacetsService) service;
        }
    }

    @Override
    public void serviceGone(PluginService service) {
        if (service == kiezatlasService) {
            kiezatlasService = null;
        } else if (service == geomapsService) {
            geomapsService = null;
        } else if (service == facetsService) {
            facetsService = null;
        }
    }



    // ------------------------------------------------------------------------------------------------- Private Methods

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

    private GeoCoordinate geoCoordinate(Topic geoObjectTopic) {
        Topic address = geoObjectTopic.getCompositeValue().getTopic("dm4.contacts.address");
        GeoCoordinate geoCoord = geomapsService.getGeoCoordinate(address);
        if (geoCoord == null) {
            throw new RuntimeException("Geo coordinate is unknown");
        }
        return geoCoord;
    }

    // --- Create result GeoObject ---

    /**
     * @param   geoCoord    the geo coordinate already looked up.
     */
    private GeoObject createGeoObject(Topic geoObjectTopic, GeoCoordinate geoCoord) {
        GeoObject geoObject = new GeoObject();
        //
        Topic bezirk = bezirk(geoObjectTopic);
        geoObject.setName(geoObjectTopic.getSimpleValue().toString());
        geoObject.setBezirk(bezirk.getSimpleValue().toString());
        geoObject.setGeoCoordinate(geoCoord);
        geoObject.setLink(link(geoObjectTopic, bezirk));
        //
        return geoObject;
    }

    /**
     * Returns the Bezirk topic assigned to the given Geo Object.
     * If no Bezirk topic is assigned an exception is thrown.
     */
    private Topic bezirk(Topic geoObjectTopic) {
        Topic bezirk = facetsService.getFacet(geoObjectTopic, "ka2.bezirk.facet");
        if (bezirk == null) {
            throw new RuntimeException("No Bezirk is assigned");
        }
        return bezirk;
    }

    /**
     * @param   bezirk      the Bezirk topic already looked up. Not null.
     */
    private String link(Topic geoObjectTopic, Topic bezirk) {
        String ka1TopicId, ka1MapAlias;
        //
        ka1TopicId = uriPostfix(geoObjectTopic.getUri(), KA2_GEO_OBJECT_URI_PREFIX, "geo object");
        //
        Topic bezirksregion = facetsService.getFacet(geoObjectTopic, "ka2.bezirksregion.facet");
        if (bezirksregion != null) {
            ka1MapAlias = uriPostfix(bezirksregion.getUri(), KA2_BEZIRKSREGION_URI_PREFIX, "Bezirksregion");
        } else {
            // Fallback: link to Bezirksgesamtkarte when Bezirksregion is unknown
            ka1MapAlias = uriPostfix(bezirk.getUri(), KA2_BEZIRK_URI_PREFIX, "Bezirk");
        }
        //
        return String.format(KA1_MAP_URL, ka1MapAlias, ka1TopicId);
    }

    private String uriPostfix(String uri, String uriPrefix, String entityName) {
        if (!uri.startsWith(uriPrefix)) {
            throw new RuntimeException("The " + entityName + " URI does not start with \"" + uriPrefix +
                "\" but is \"" + uri + "\"");
        }
        //
        return uri.substring(uriPrefix.length());
    }

    // --- Update Famportal Category facet ---

    private FacetValue createFacetValue(long famportalCategoryId) {
        // Prerequisite: categories are modeled per 1) Aggregation Def, 2) Cardinality Many
        return new FacetValue(FAMPORTAL_CATEGORY_URI).addRef(famportalCategoryId);
    }

    private FacetValue createDeletionFacetValue(long famportalCategoryId) {
        // Prerequisite: categories are modeled per 1) Aggregation Def, 2) Cardinality Many
        return new FacetValue(FAMPORTAL_CATEGORY_URI).addDeletionRef(famportalCategoryId);
    }

    // ---

    private void updateFacet(List<Long> geoObjectIds, FacetValue value) {
        for (long geoObjectId : geoObjectIds) {
            facetsService.updateFacet(geoObjectId, FAMPORTAL_CATEGORY_FACET_URI, value, null);  // clientState=null
        }
    }

    private void updateFacetByCategories(List<Long> kiezatlasCategoryIds, FacetValue value) {
        for (long catId : kiezatlasCategoryIds) {
            List<RelatedTopic> geoObjects = kiezatlasService.getGeoObjectsByCategory(catId, false);
                                                                                         // fetchComposite=false
            for (Topic geoObject : geoObjects) {
                facetsService.updateFacet(geoObject, FAMPORTAL_CATEGORY_FACET_URI, value, null, new Directives());
                                                                                                // clientState=null
            }
        }
    }
}
