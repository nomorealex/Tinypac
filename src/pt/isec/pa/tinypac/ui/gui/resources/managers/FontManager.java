package pt.isec.pa.tinypac.ui.gui.resources.managers;

import javafx.scene.text.Font;

import java.io.InputStream;
/**
 * Class CSSManager
 *
 * @author Provided by Prof.Alvaro (ISEC 22/23)
 *
 */
public class FontManager {
    private FontManager() { }
    public static Font loadFont(String filename, int size) {
        try(InputStream inputStreamFont =
                    FontManager.class.getResourceAsStream("../fonts/" + filename)) {
            return Font.loadFont(inputStreamFont, size);
        } catch (Exception e) {
            return null;
        }
    }
}