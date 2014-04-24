package de.kiezatlas.famportal;

import de.deepamehta.core.JSONEnabled;
import de.deepamehta.plugins.geomaps.model.GeoCoordinate;

import org.codehaus.jettison.json.JSONObject;



/**
 * A data transfer object as returned by the Familienportal service.
 */
public class GeoObject implements JSONEnabled {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private JSONObject json = new JSONObject();

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public JSONObject toJSON() {
        return json;
    }

    // ----------------------------------------------------------------------------------------- Package Private Methods

    void setName(String name) {
        try {
            json.put("name", name);
        } catch (Exception e) {
            throw new RuntimeException("Constructing a GeoObject failed", e);
        }
    }

    void setGeoCoordinate(GeoCoordinate geoCoord) {
        try {
            JSONObject geolocation = new JSONObject();
            geolocation.put("lon", geoCoord.lon);
            geolocation.put("lat", geoCoord.lat);
            //
            json.put("geolocation", geolocation);
        } catch (Exception e) {
            throw new RuntimeException("Constructing a GeoObject failed", e);
        }
    }

    void setLink(String link) {
        try {
            json.put("link", link);
        } catch (Exception e) {
            throw new RuntimeException("Constructing a GeoObject failed", e);
        }
    }
}
