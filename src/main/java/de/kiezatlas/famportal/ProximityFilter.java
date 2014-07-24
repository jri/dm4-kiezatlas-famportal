package de.kiezatlas.famportal;

import de.deepamehta.plugins.geomaps.model.GeoCoordinate;



/**
 * A filter used in the retirval API to restrict the result set by proximity (geo coordinate + radius).
 */
public class ProximityFilter {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    GeoCoordinate geoCoordinate;
    float radius;   // in kilometer

    // ---------------------------------------------------------------------------------------------------- Constructors

    /**
     * Called by JAX-RS container to create a ProximityFilter from the "proximity" @QueryParam
     */
    public ProximityFilter(String proximity) {
        String[] str = proximity.split(",");
        this.geoCoordinate = new GeoCoordinate(Double.valueOf(str[0]), Double.valueOf(str[1]));
        this.radius = Float.valueOf(str[2]);
    }

    // -------------------------------------------------------------------------------------------------- Public Methods

    @Override
    public String toString() {
        return geoCoordinate.toString() + ", radius=" + radius;
    }
}
