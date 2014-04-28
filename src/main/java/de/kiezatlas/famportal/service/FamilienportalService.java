package de.kiezatlas.famportal.service;

import de.kiezatlas.famportal.CategorySet;
import de.kiezatlas.famportal.GeoObject;
import de.kiezatlas.famportal.GeoObjectCount;
import de.deepamehta.core.service.PluginService;

import java.util.List;



public interface FamilienportalService extends PluginService {

    // --- Retrieval API ---

    List<GeoObject> getGeoObjects(List<CategorySet> categorySets);

    // --- Redationalwerkzeug ---

    void createAssignments(long famportalCategoryId, List<Long> kiezatlasCategoryIds);

    void deleteAssignments(long famportalCategoryId, List<Long> geoObjectIds);

    GeoObjectCount countAssignments();
}
