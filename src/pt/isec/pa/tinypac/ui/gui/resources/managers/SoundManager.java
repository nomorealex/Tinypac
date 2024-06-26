package pt.isec.pa.tinypac.ui.gui.resources.managers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
/**
 * Class CSSManager
 *
 * @author Provided by Prof.Alvaro (ISEC 22/23)
 *
 */
public class SoundManager {
    private SoundManager() { }
    private static MediaPlayer mp;
    public static boolean play(String filename) {
        try {
            var url = SoundManager.class.getResource("../sounds/" + filename);
            if (url == null) return false;
            String path = url.toExternalForm();
            Media music = new Media(path);
            if (mp != null && mp.getStatus() == MediaPlayer.Status.PLAYING)
                mp.stop();
            mp = new MediaPlayer(music);
            mp.setStartTime(Duration.ZERO);
            mp.setStopTime(music.getDuration());
            mp.setAutoPlay(true);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}