package de.kiezatlas.famportal;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;



/**
 * A set of Familienportal category XML IDs as obtained from a request.
 */
public class CategorySet implements Iterable<String> {

    // ---------------------------------------------------------------------------------------------- Instance Variables

    private List<String> categoryXmlIds;

    // ---------------------------------------------------------------------------------------------------- Constructors

    /**
     * Called by JAX-RS container to create a CategorySet from a "category" @QueryParam
     */
    public CategorySet(String category) {
        categoryXmlIds = Arrays.asList(category.split(","));
    }

    // -------------------------------------------------------------------------------------------------- Public Methods

    public int size() {
        return categoryXmlIds.size();
    }

    @Override
    public Iterator<String> iterator() {
        return categoryXmlIds.iterator();
    }

    @Override
    public String toString() {
        return categoryXmlIds.toString();
    }
}
