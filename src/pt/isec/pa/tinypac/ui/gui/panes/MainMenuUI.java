package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.comunication.Messages;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.controls.CustomButton;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationButtonFactory;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationLabelFactory;
import pt.isec.pa.tinypac.ui.gui.resources.managers.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;

/**
 * Class MainMenuUI
 * <p>Class that represents an interface for presentation(extends BorderPane)</p>
 * @author Nuno Domingues
 *
 */
public class MainMenuUI extends BorderPane {

    /**
     * Constant value PLAY_GAME
     */
    private static final String PLAY_GAME = "Play Game";

    /**
     * Constant value TOP_5
     */
    private static final String TOP_5 = "Top 5";

    /**
     * Constant value EXIT_GAME
     */
    private static final String EXIT_GAME = "Exit Game";

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * playGameButton tp start the game
     * */
    private final CustomButton playGameButton;

    /**
     * top5Button to show top 5players
     * */
    private final CustomButton top5Button;

    /**
     * exitGameButton to exit the game
     * */
    private final CustomButton exitGameButton;
    private final Label title;
    private final Label mainMenuLabel;
    private final VBox container;
    private final DropShadow shadow = new DropShadow();

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public MainMenuUI(TinyPacModelManager tinyPacModelManager){
        this.tinyPacModelManager = tinyPacModelManager;
        playGameButton = new CustomButton(PLAY_GAME,false,false);
        top5Button = new CustomButton(TOP_5, false, false);
        exitGameButton = new CustomButton(EXIT_GAME,false,false);
        title = new Label();
        mainMenuLabel = new Label();
        container = new VBox();
        shadow.setColor(Color.WHITE);
        shadow.setSpread(0.2);
        shadow.setRadius(15);
        createViews();
        registerHandlers();
    }


    /**
     * createViews function
     * Create the elements that of the interface like the buttons,labels
     * @return void
     * */
    private void createViews() {
        CSSManager.applyCSS(this,"myStyleSheet.css");

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("pacman.jpg"),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO,
                                        BackgroundSize.AUTO,
                                        true,true,true,true))));


        title.setText("©TINYPAC 2022/2023");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        mainMenuLabel.setText("----Main Menu----");
        mainMenuLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        mainMenuLabel.setTextFill(Color.WHITE);

        playGameButton.setPrefSize(200,50);
        top5Button.setPrefSize(200,50);
        exitGameButton.setPrefSize(200,50);


        Timeline labelAnimation = AnimationLabelFactory.createAnimation(true,title);
        Timeline buttonsAnimation = AnimationButtonFactory.createAnimation(true,playGameButton,top5Button,exitGameButton);
        labelAnimation.play();
        buttonsAnimation.play();

        container.getChildren().addAll(title,mainMenuLabel,playGameButton,top5Button,exitGameButton);
        container.setSpacing(50);
        container.setPadding(new Insets(0,0,150,0));
        container.setAlignment(Pos.CENTER);
        this.setCenter(container);
    }

    /**
     * perform function
     * @return void
     * */
    private void performButtonTask(String text){
        switch(text){
            case PLAY_GAME -> switchToPlay();
            case TOP_5 -> switchToTop5UI();
            case EXIT_GAME -> {
                exitDialog();
            }
            default -> {}
        }


    }

    /**
     * registerHandlers function
     * Register container event
     * @return void
     * */
    private void registerHandlers() {
        container.addEventFilter(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getTarget() instanceof Button button){
                            performButtonTask(button.getText());
                        }
                        if(mouseEvent.getTarget() instanceof Text text){
                            performButtonTask(text.getText());
                        }
                    }
                });
    }

    /**
     * switchToPlay function
     * @return void
     * */
    private void switchToPlay(){
        if(tinyPacModelManager.isExistGameSaved()){
            if(gameSavedDialog())
                tinyPacModelManager.startGame(Messages.LOAD);
            else
                tinyPacModelManager.startGame(Messages.NOT_LOAD);
        }else{
            tinyPacModelManager.startGame(Messages.NOT_LOAD);
        }
        Stage thisStage = (Stage)this.getScene().getWindow();
        Scene newScene = new Scene(new PlayGameUI(tinyPacModelManager),1000,700);
        thisStage.setScene(newScene);
    }

    /**
     * switchToTop5UI function
     * @return void
     * */
    private void switchToTop5UI() {
        Stage thisStage = (Stage)this.getScene().getWindow();
        Scene newScene = new Scene(new Top5UI(tinyPacModelManager),1000,700);
        thisStage.setScene(newScene);
    }

    /**
     * exitDialog function
     * Dialog that appear when exit is clicked
     * @return void
     * */
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
            Platform.exit();
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

    /**
     * gameSavedDialog function
     * @return void
     * */
    private boolean gameSavedDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LOAD GAME!");
        alert.setHeaderText(null);
        alert.setContentText("Saved game found! Do you want to load it?");

        final Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        try {
            Image logo = ImageManager.getImage("pacmanLogo.png");
            stage.getIcons().add(logo);
        }catch (NullPointerException e){}

        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");

        alert.getButtonTypes().setAll(yesButton, noButton);

        ButtonType userChoice = alert.showAndWait().orElse(ButtonType.CANCEL);

        return userChoice == yesButton;
    }
}

