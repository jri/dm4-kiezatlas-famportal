package de.kiezatlas.famportal.service;

import de.deepamehta.core.RelatedTopic;
import de.deepamehta.core.service.PluginService;
import de.deepamehta.core.service.ResultList;



public interface FamilienportalService extends PluginService {

    ResultList<RelatedTopic> getGeoObjectsByCategory(long famportalCategoryId);
}
