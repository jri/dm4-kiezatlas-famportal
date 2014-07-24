package de.kiezatlas.famportal.service;

import de.kiezatlas.famportal.CategorySet;
import de.kiezatlas.famportal.GeoObject;
import de.kiezatlas.famportal.GeoObjectCount;
import de.kiezatlas.famportal.ProximityFilter;
import de.deepamehta.core.service.PluginService;

import java.util.List;



public interface FamilienportalService extends PluginService {

    // --- Retrieval API ---

    List<GeoObject> getGeoObjects(List<CategorySet> categorySets, ProximityFilter proximity);

    // --- Redationalwerkzeug ---

    void createAssignments(long famportalCategoryId, List<Long> geoObjectIds);

    void createAssignmentsByCategories(long famportalCategoryId, List<Long> kiezatlasCategoryIds);

    // ---

    void deleteAssignments(long famportalCategoryId, List<Long> geoObjectIds);

    void deleteAssignmentsByCategories(long famportalCategoryId, List<Long> kiezatlasCategoryIds);

    // ---

    GeoObjectCount countAssignments();
}
