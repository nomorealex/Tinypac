package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import pt.isec.pa.tinypac.comunication.PropsChange;
import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.panes.states.CounterAttackStateUI;
import pt.isec.pa.tinypac.ui.gui.panes.states.LocomoteStateUI;
import pt.isec.pa.tinypac.ui.gui.panes.states.PauseStateUI;
import pt.isec.pa.tinypac.ui.gui.panes.states.PrepareStateUI;
import pt.isec.pa.tinypac.ui.gui.panes.states.WaitingTimeStateUI;
import pt.isec.pa.tinypac.ui.gui.resources.managers.CSSManager;

/**
 * Class PlayGameUI
 * <p>Class that represents an interface for presentation(extends BorderPane)</p>
 * @author Nuno Domingues
 *
 */
public class PlayGameUI extends BorderPane {

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * StackPane to store and handle all the views according to the stateMachine
     */
    private StackPane stackPane;

    /**
     * StatusBar that is going to be on top
     * */
    private final StatusBar statusBar;

    /**
     * LifeStatusBar that is going to be on right
     * */
    private final LifeStatusBar lifeStatusBar;

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public PlayGameUI(TinyPacModelManager tinyPacModelManager){
        this.tinyPacModelManager = tinyPacModelManager;
        statusBar = new StatusBar(tinyPacModelManager);
        lifeStatusBar = new LifeStatusBar(tinyPacModelManager);
        createViews();
        registerHandlers();
        update();
    }

    /**
     * createViews function
     * Create the elements that of the interface like the buttons and labels
     * @return void
     * */
    private void createViews() {
        CSSManager.applyCSS(this,"myStyleSheet.css");
        stackPane = new StackPane(
                new PrepareStateUI(tinyPacModelManager),
                new WaitingTimeStateUI(tinyPacModelManager),
                new LocomoteStateUI(tinyPacModelManager),
                new CounterAttackStateUI(tinyPacModelManager),
                new PauseStateUI(tinyPacModelManager)
        );
        Stop[] stops = new Stop[] { new Stop(0, Color.DARKBLUE), new Stop(1, Color.STEELBLUE)};
        LinearGradient lg1 = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        stackPane.setBackground(new Background(new BackgroundFill(lg1, CornerRadii.EMPTY, Insets.EMPTY)));
        setCenter(stackPane);
    }

    /**
     * registerHandlers function
     * Register the PropertyListeners and the keyboardEvents event
     * @return void
     * */
    private void registerHandlers() {
        tinyPacModelManager.addListener(PropsChange.PROPS_GAME_BOARD.getName(),event -> Platform.runLater(this::update));
        tinyPacModelManager.addListener(PropsChange.PROPS_GAME_TOP_MODAL.getName(), event -> Platform.runLater(this::showModal));
        stackPane.setOnKeyPressed(event -> {
            switch (event.getCode()){
                case UP -> {tinyPacModelManager.move(ArrowDirection.UP);}
                case DOWN -> {tinyPacModelManager.move(ArrowDirection.DOWN);}
                case LEFT -> {tinyPacModelManager.move(ArrowDirection.LEFT);}
                case RIGHT -> {tinyPacModelManager.move(ArrowDirection.RIGHT);}
                case P -> {tinyPacModelManager.pause();}
                default -> {}
            }
        });
        stackPane.setFocusTraversable(true);
    }

    /**
     * update function
     * Called by the Property change listener
     * @return void
     * */
    private void update(){
        switch(tinyPacModelManager.getFsmState()){
            case PAUSE_STATE:
                setTop(null);
                setRight(null);
                setCenter(stackPane);
                break;
            default:
                setTop(statusBar);
                setRight(lifeStatusBar);
                setCenter(stackPane);
                break;
        }
    }

    /**
     * Called by the Property change listener
     * @return void
     * */
    private void showModal(){
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("END OF GAME!");
        dialog.setHeaderText("TOP 5");

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(okButtonType);

        dialog.getDialogPane().getButtonTypes().remove(ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        dialog.getDialogPane().setContent(grid);

        Button okButton = (Button) dialog.getDialogPane().lookupButton(okButtonType);
        okButton.disableProperty().bind(nameField.textProperty().isEmpty());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return nameField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(name -> {
            tinyPacModelManager.register(name);
            tinyPacModelManager.savePacWorld();
            Platform.exit();
        });
    }

}
