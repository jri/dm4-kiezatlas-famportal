package de.kiezatlas.famportal;

import de.deepamehta.core.JSONEnabled;

import org.codehaus.jettison.json.JSONObject;



/**
 * A data transfer object as returned by the Familienportal service.
 */
public class GeoObject implements JSONEnabled {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private JSONObject json = new JSONObject();

    // -------------------------------------------------------------------------------------------------- Public Methods

    void setName(String name) {
        try {
            json.put("name", name);
        } catch (Exception e) {
            throw new RuntimeException("Constructing a GeoObject failed", e);
        }
    }

    // ---

    @Override
    public JSONObject toJSON() {
        return json;
    }
}
