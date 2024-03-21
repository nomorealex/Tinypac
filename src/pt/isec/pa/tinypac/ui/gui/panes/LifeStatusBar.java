package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import pt.isec.pa.tinypac.comunication.PropsChange;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;


/**
 * Class LifeStatusBar
 * <p>Class that represents an interface for presentation(extends VBox)</p>
 * @author Nuno Domingues
 *
 */
public class LifeStatusBar extends VBox {

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * ImageView to present the lives
     * */
    private final ImageView pacmanLife;

    /**
     * ImageView to present the fruit image
     * */
    private final ImageView fruitImage;

    /**
     * Label to present the lives
     * */
    private final Label lifeScore;

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public LifeStatusBar(TinyPacModelManager tinyPacModelManager) {
        this.tinyPacModelManager = tinyPacModelManager;
        pacmanLife = new ImageView(ImageManager.getImage("pacmanLife.png"));
        fruitImage = new ImageView(ImageManager.getImage("fruit60.png"));
        lifeScore = new Label();
        createViews();
        registerHandlers();
        update();
    }

    /**
     * createViews function
     * Create the elements that of the interface like the button and the labels
     * @return void
     * */
    private void createViews(){
        Stop[] stops = new Stop[] { new Stop(0, Color.BLUE), new Stop(1, Color.DEEPSKYBLUE)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        this.setBackground(new Background(new BackgroundFill(lg1, CornerRadii.EMPTY, Insets.EMPTY)));

        lifeScore.setText(" : ");
        lifeScore.setFont(Font.font("Times New Roman",32));
        lifeScore.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(pacmanLife,lifeScore);
        hBox.setAlignment(Pos.CENTER);

        this.getChildren().addAll(hBox,fruitImage);
        this.setSpacing(20);
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
    }

    /**
     * registerHandlers function
     * Register the PropertyListener
     * @return void
     * */
    private void registerHandlers() {
        tinyPacModelManager.addListener(PropsChange.PROPS_GAME_LIFE_STATUS_BAR.getName(), event -> Platform.runLater(this::update));
    }

    /**
     * update function
     * Called by the Property change listener
     * @return void
     * */
    private void update(){
        lifeScore.setText(" : "+tinyPacModelManager.getLifeScore());
    }
}