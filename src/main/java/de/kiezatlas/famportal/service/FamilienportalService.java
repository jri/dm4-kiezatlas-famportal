package de.kiezatlas.famportal.service;

import de.deepamehta.core.service.PluginService;

import java.util.List;



public interface FamilienportalService extends PluginService {

    void createAssignments(long famportalCategoryId, List<Long> kiezatlasCategoryIds);

    void deleteAssignments(long famportalCategoryId, List<Long> geoObjectIds);
}
