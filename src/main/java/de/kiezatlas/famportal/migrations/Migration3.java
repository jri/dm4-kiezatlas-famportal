package de.kiezatlas.famportal.migrations;

import de.deepamehta.core.Topic;
import de.deepamehta.core.service.Directives;
import de.deepamehta.core.service.Migration;



/**
 * Deletes the Famportal category tree (that is all "Familienportal Kategorie" topics).
 */
public class Migration3 extends Migration {

    @Override
    public void run() {
        for (Topic topic : dms.getTopics("famportal.category", false, 0).getItems()) {
            topic.delete(new Directives());
        }
    }
}
