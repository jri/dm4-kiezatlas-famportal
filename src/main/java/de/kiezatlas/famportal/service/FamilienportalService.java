package de.kiezatlas.famportal.service;

import de.deepamehta.core.service.PluginService;

import java.util.List;



public interface FamilienportalService extends PluginService {

    void assignToFamportalCategory(long categoryId, List<Long> geoObjectIds);

    void removeFromFamportalCategory(long categoryId, List<Long> geoObjectIds);
}
