package pt.isec.pa.tinypac.ui.gui.factories;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

/**
 * Class AnimationLabelFactory
 * <p>It's a factory that allows to apply a specific animation to labels</p>
 * @author Nuno Domingues
 *
 */
public final class AnimationLabelFactory {
    private AnimationLabelFactory(){}

    /**
     * final static variable to store constant colors that are going to be used on animation
     * */
    private static final Color [] colors = new Color[]{
            Color.rgb(239, 255, 0),
            Color.rgb(255, 0, 0),
            Color.rgb(255,154,0),
            Color.rgb(0,222,255),
            Color.rgb(255,0,222)
    };

    /**
     * createAnimation function
     * Creates an animation for the labels provided
     * @param autoReverse if the animation needs to be reversed
     * @param labels it's an indeterminated number of labels that are going to have the animation
     * @return Timeline
     * */

    public static Timeline createAnimation(boolean autoReverse, Label...labels){

        List<KeyValue> keyValues0 = new ArrayList<>();
        List<KeyValue> keyValues1 = new ArrayList<>();
        List<KeyValue> keyValues2 = new ArrayList<>();
        List<KeyValue> keyValues3 = new ArrayList<>();
        List<KeyValue> keyValues4 = new ArrayList<>();

        for (var lb : labels) {
            keyValues0.add(new KeyValue(lb.textFillProperty(), colors[0]));
            keyValues1.add(new KeyValue(lb.textFillProperty(), colors[1]));
            keyValues2.add(new KeyValue(lb.textFillProperty(), colors[2]));
            keyValues3.add(new KeyValue(lb.textFillProperty(), colors[3]));
            keyValues4.add(new KeyValue(lb.textFillProperty(), colors[4]));
        }

        KeyFrame keyFrame0 = new KeyFrame(Duration.ZERO, keyValues0.toArray(KeyValue[]::new));
        KeyFrame keyFrame1 = new KeyFrame(Duration.seconds(0.5), keyValues1.toArray(KeyValue[]::new));
        KeyFrame keyFrame2 = new KeyFrame(Duration.seconds(1.0), keyValues2.toArray(KeyValue[]::new));
        KeyFrame keyFrame3 = new KeyFrame(Duration.seconds(1.5), keyValues3.toArray(KeyValue[]::new));
        KeyFrame keyFrame4 = new KeyFrame(Duration.seconds(2.0), keyValues4.toArray(KeyValue[]::new));

        Timeline animation = new Timeline(
                keyFrame0,
                keyFrame1,
                keyFrame2,
                keyFrame3,
                keyFrame4
        );
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setAutoReverse(autoReverse);
        return animation;
    }
}
