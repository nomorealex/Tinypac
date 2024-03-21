package pt.isec.pa.tinypac.ui.gui.resources.managers;

import javafx.scene.Parent;

/**
 * Class CSSManager
 *
 * @author Provided by Prof.Alvaro (ISEC 22/23)
 *
 */
public class CSSManager {

    private CSSManager() { }
    public static void applyCSS(Parent parent, String filename) {
        var url = CSSManager.class.getResource("../styles/"+filename);
        if (url == null)
            return;
        String fileCSS = url.toExternalForm();
        parent.getStylesheets().add(fileCSS);
    }
}
