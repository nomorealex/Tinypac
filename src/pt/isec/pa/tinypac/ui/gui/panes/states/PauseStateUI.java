package pt.isec.pa.tinypac.ui.gui.panes.states;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.comunication.PropsChange;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.ui.gui.controls.CustomButton;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationButtonFactory;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationLabelFactory;
import pt.isec.pa.tinypac.ui.gui.panes.MainMenuUI;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;

/**
 * Class PauseStateUI
 * <p>Class that represents an interface for presentation(extends VBox)</p>
 * @author Nuno Domingues
 *
 */
public class PauseStateUI extends VBox {

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * Constant value EXIT
     * */
    private static final String EXIT = "EXIT";

    /**
     * Constant value SAVE_GAME
     * */
    private static final String SAVE_GAME = "SAVE GAME!";

    /**
     * Label Title
     * */
    private final Label title;

    /**
     * exitButton to exit the game
     * */
    private final CustomButton exitButton;

    /**
     * saveGameButton to dave the game
     * */
    private final CustomButton saveGameButton;


    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public PauseStateUI(TinyPacModelManager tinyPacModelManager) {
        this.tinyPacModelManager = tinyPacModelManager;
        exitButton = new CustomButton(EXIT,false,false);
        saveGameButton = new CustomButton(SAVE_GAME,false,false);
        title = new Label();
        createViews();
        registerPropertyListener();
        registerHandlers();
        update();
    }


    /**
     * createViews function
     * Create the elements that of the interface like the buttons,labels
     * @return void
     * */
    private void createViews() {
        title.setText("IN PAUSE...");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        exitButton.setPrefSize(200,50);
        saveGameButton.setPrefSize(200,50);

        Timeline labelAnimation = AnimationLabelFactory.createAnimation(true,title);
        Timeline buttonsAnimation = AnimationButtonFactory.createAnimation(true,exitButton,saveGameButton);
        labelAnimation.play();
        buttonsAnimation.play();

        HBox hBox = new HBox(exitButton,saveGameButton);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        this.getChildren().addAll(title,hBox);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(50);
    }

    /**
     * registerPropertyListener function
     * Register the PropertyListener
     * @return void
     * */
    private void registerPropertyListener() {
        tinyPacModelManager.addListener(PropsChange.PROPS_GAME_BOARD.getName(), event -> Platform.runLater(this::update));
    }

    /**
     * registerHandlers function
     * Register exitButton event and saveGameButton event
     * @return void
     * */
    private void registerHandlers() {
        exitButton.setOnAction(actionEvent -> {
            exitDialog();
        });

        saveGameButton.setOnAction(actionEvent -> {
            tinyPacModelManager.resume();
        });

    }

    /**
     * update function
     * Called by the Property change listener
     * @return void
     * */
    private void update(){
        if(tinyPacModelManager.getFsmState() != TinyPacState.PAUSE_STATE) {
            setVisible(false);
            return;
        }
        setVisible(true);

    }

    private void exitDialog() {

        final Stage stage = new Stage();

        stage.getIcons().add(ImageManager.getImage("pacmanLogo.png"));

        Label question = new Label("Do you really want to exit the game?");
        question.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 20;" +
                "-fx-text-fill: white");


        Button closeButton = new Button("Yes");
        closeButton.setPrefSize(100,20);
        closeButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 15;");
        closeButton.focusTraversableProperty().set(false);
        closeButton.setOnAction(event -> {
            stage.close();
            Stage thisStage = (Stage)this.getScene().getWindow();
            Scene newScene = new Scene(new MainMenuUI(tinyPacModelManager),1000,700);
            thisStage.setScene(newScene);
        });

        Button ignoreButton = new Button("No");
        ignoreButton.setPrefSize(100,20);
        ignoreButton.setStyle("-fx-font-family: 'Arial Rounded MT Bold';" +
                "-fx-font-size: 15;");
        ignoreButton.setOnAction(event -> {
            stage.close();
        });

        HBox buttonsContainer = new HBox(closeButton,ignoreButton);
        buttonsContainer.setAlignment(Pos.CENTER);
        buttonsContainer.setSpacing(20);
        stage.setHeight(250);
        VBox vBox = new VBox(question,buttonsContainer);
        vBox.setStyle("-fx-background-color: linear-gradient(to top left, #18595b, #2b4e83);");
        vBox.setSpacing(10);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);
        stage.setScene(new Scene(vBox));
        stage.setTitle("Exit Game!");
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.getScene().getWindow());
        stage.showAndWait();
    }
}
