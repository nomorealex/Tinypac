package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import pt.isec.pa.tinypac.comunication.PropsChange;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.controls.CustomButton;
/**
 * Class StatusBar
 * <p>Class that represents an interface for presentation(extends HBox)</p>
 * @author Nuno Domingues
 *
 */
public class StatusBar extends HBox {

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * Label to present the score
     * */
    private final Label scoreLabel;

    /**
     * Label to present the game Level
     * */
    private final Label levelLabel;

    /**
     * Pause Button to pause the game
     * */
    private final CustomButton pauseButton;

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public StatusBar(TinyPacModelManager tinyPacModelManager) {
        this.tinyPacModelManager = tinyPacModelManager;
        scoreLabel = new Label();
        levelLabel = new Label();
        pauseButton = new CustomButton("Pause",false,false);
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
        pauseButton.setPrefSize(150,20);
        scoreLabel.setText("Score: ");
        scoreLabel.setFont(Font.font("Times New Roman",30));
        scoreLabel.setTextFill(Color.WHITE);

        levelLabel.setText("Level: ");
        levelLabel.setFont(Font.font("Times New Roman",30));
        levelLabel.setTextFill(Color.WHITE);

        Stop[] stops = new Stop[] { new Stop(0, Color.BLUE), new Stop(1, Color.RED)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        HBox.setMargin(pauseButton, new Insets(0, 150, 0, 150));

        this.getChildren().addAll(levelLabel,pauseButton,scoreLabel);
        this.setBackground(new Background(new BackgroundFill(lg1, CornerRadii.EMPTY, Insets.EMPTY)));
        this.setPadding(new Insets(10));
        this.setAlignment(Pos.CENTER);
    }

    /**
     * registerHandlers function
     * Register the PropertyListener and the pauseButton event
     * @return void
     * */
    private void registerHandlers() {
        tinyPacModelManager.addListener(PropsChange.PROPS_GAME_BOARD.getName(), event -> Platform.runLater(this::update));
        pauseButton.setOnAction(actionEvent -> {
            tinyPacModelManager.pause();
        });
    }

    /**
     * update function
     * Called by the Property change listener
     * @return void
     * */
    private void update(){
        levelLabel.setText("Level: "+tinyPacModelManager.getLevel());
        scoreLabel.setText("Score: "+tinyPacModelManager.getPoints());
    }
}
