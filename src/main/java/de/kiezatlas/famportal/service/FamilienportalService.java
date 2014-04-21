package de.kiezatlas.famportal.service;

import de.kiezatlas.famportal.CategorySet;
import de.kiezatlas.famportal.GeoObject;
import de.deepamehta.core.service.PluginService;

import java.util.List;



public interface FamilienportalService extends PluginService {

    List<GeoObject> getGeoObjects(List<CategorySet> categorySets);

    // ---

    void createAssignments(long famportalCategoryId, List<Long> kiezatlasCategoryIds);

    void deleteAssignments(long famportalCategoryId, List<Long> geoObjectIds);
}
