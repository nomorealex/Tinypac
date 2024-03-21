package pt.isec.pa.tinypac.ui.gui.resources.managers;

import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
/**
 * Class CSSManager
 *
 * @author Provided by Prof.Alvaro (ISEC 22/23)
 *
 */
public class ImageManager {

    private ImageManager() {
    }

    private static final HashMap<String, Image> images = new HashMap<>();

    public static Image getImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try (InputStream is = ImageManager.class.getResourceAsStream("../images/" + filename)) {
                image = new Image(is);
                images.put(filename, image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    public static Image getExternalImage(String filename) {
        Image image = images.get(filename);
        if (image == null)
            try {
                image = new Image(filename);
                images.put(filename, image);
            } catch (Exception e) {
                return null;
            }
        return image;
    }

    public static void purgeImage(String filename) {
        images.remove(filename);
    }
}
