package pt.isec.pa.tinypac.ui.gui.controls;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;
import pt.isec.pa.tinypac.ui.gui.resources.managers.SoundManager;


/**
 * Class CustomButton
 * <p>It's a Base class to custom buttons</p>
 * @author Nuno Domingues
 *
 */
public class CustomButton extends Button {

    /**
     * DropShadow effect to apply on button
     * */
    private final DropShadow shadow;

    /**
     * isGraphicButton - if the button has some image
     * */
    private final boolean isGraphicButton;

    /**
     * gotRotation - if the button has some rotation animation
     * */
    private final boolean gotRotation;



    public CustomButton(String text, boolean isGraphicButton, boolean gotRotation){
        shadow = new DropShadow();
        this.isGraphicButton = isGraphicButton;
        this.gotRotation = gotRotation;
        init(text);
    }



    private void init(String text) {
        shadow.setColor(Color.WHITE);
        shadow.setSpread(0.2);
        shadow.setRadius(15);
        focusTraversableProperty().set(false);
        if(!isGraphicButton) {
            this.setId("tinyPacButton");
            setText(text);
        }
        else {
            this.setId("tinyPacGraphicButton");
            setStyle("-fx-background-color: none");
            Image image = ImageManager.getImage(text);
            setGraphic(new ImageView(image));
        }
        setListeners();
    }

    private void setListeners() {

        this.addEventHandler(MouseEvent.MOUSE_ENTERED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        SoundManager.play("beep.mp3");
                        setEffect(shadow);
                        if(gotRotation)
                            setRotate(20);
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_EXITED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        setEffect(null);
                        if(gotRotation)
                            setRotate(0);
                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_PRESSED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(!isGraphicButton)
                            setStyle("-fx-border-color: red;");


                    }
                });

        this.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(!isGraphicButton)
                            setStyle("-fx-border-color: white;");
                    }
                });
    }
}

