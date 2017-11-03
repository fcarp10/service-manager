/**
 * Categorizing module inside the Service Manager
 * Part of the mF2C Project: http://www.mf2c-project.eu/
 * <p>
 * This code is licensed under an Apache 2.0 license. Please, refer to the LICENSE.TXT file for more information
 *
 * @author Francisco Carpio - TUBS
 */
package src.categorization;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.elements.Category;


public class Categorizer {

    private static Logger log = LoggerFactory.getLogger(Categorizer.class);

    public Categorizer() {
        //TODO
    }

    public Category categorise(int taskId) {
        log.info("Categorizing task @id-" + taskId);
        Category category = new Category();

        //TODO

        return category;
    }
}
