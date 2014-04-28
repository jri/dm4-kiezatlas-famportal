package de.kiezatlas.famportal;

import de.deepamehta.core.JSONEnabled;

import org.codehaus.jettison.json.JSONObject;



/**
 * A data transfer object as returned by the Familienportal service.
 */
public class GeoObjectCount implements JSONEnabled {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private JSONObject json = new JSONObject();

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public JSONObject toJSON() {
        return json;
    }

    // ----------------------------------------------------------------------------------------- Package Private Methods

    void addCount(long famportalCategoryId, int geoObjectCount) {
        try {
            json.put("cat-" + famportalCategoryId, geoObjectCount);
        } catch (Exception e) {
            throw new RuntimeException("Constructing a GeoObjectCount failed", e);
        }
    }
}
