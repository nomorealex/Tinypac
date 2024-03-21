package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.controls.CustomButton;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationButtonFactory;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationLabelFactory;
import pt.isec.pa.tinypac.ui.gui.resources.managers.CSSManager;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;

/**
 * Class RootPaneUI
 * <p>The starter class(extends BorderPane)</p>
 * @author Nuno Domingues
 *
 */
public class RootPaneUI extends BorderPane {

    /**
     * Constant value CONTINUE
     */
    private static final String CONTINUE = "Continue";

    /**
     * Constant value PACMAN_RADIUS
     */
    private static final double PACMAN_RADIUS = 30;

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * Continue Button
     */
    private final CustomButton continueButton;

    /**
     * Title Label
     */
    private final Label title;

    /**
     * Credits Text
     */
    private final Text credits;

    /**
     * Glow Effect to apply on credits
     * */
    private final Glow glowEffect;

    /**
     * ImageView for Isec Logo
     * */
    private final ImageView isecLogoImageView;

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public RootPaneUI(TinyPacModelManager tinyPacModelManager) {
        this.tinyPacModelManager = tinyPacModelManager;
        continueButton = new CustomButton(CONTINUE,false,false);
        title = new Label();
        credits = new Text();
        glowEffect = new Glow(10.0);
        isecLogoImageView = new ImageView();
        createViews();
        registerHandlers();
    }

    /**
     * createViews function
     * Create the elements that of the interface like the ImageView,buttons,labels,text and setting background
     * @return void
     * */
    private void createViews() {
        CSSManager.applyCSS(this,"myStyleSheet.css");

        this.setBackground(
                new Background(
                        new BackgroundImage(
                                ImageManager.getImage("pacman.jpg"),
                                BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO,
                                        BackgroundSize.AUTO,true,true,true,true))));

        isecLogoImageView.setImage(ImageManager.getImage("ISEC.png"));
        isecLogoImageView.setFitWidth(200);
        isecLogoImageView.setPreserveRatio(true);
        isecLogoImageView.setSmooth(true);
        isecLogoImageView.setCache(true);

        credits.setText("""
                
                DEIS-ISEC-IPC - LEI
                Programação Avançada - 2022/2023
                Trabalho realizado em contexto académico por Nuno Domingues - 2020109910
                """);
        credits.setTextAlignment(TextAlignment.CENTER);
        credits.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        credits.setFill(Color.WHITE);
        credits.setEffect(glowEffect);

        title.setText("©TINYPAC 2022/2023");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 32));

        VBox presentationVbox = new VBox(isecLogoImageView,credits,title);
        presentationVbox.setAlignment(Pos.CENTER);
        presentationVbox.setSpacing(10);
        presentationVbox.setPadding(new Insets(10,0,0,0));

        Timeline buttonsAnimation = AnimationButtonFactory.createAnimation(true,continueButton);
        Timeline labelAnimation = AnimationLabelFactory.createAnimation(true,title);


        Arc pacmanCharacter = new Arc(0, 0, PACMAN_RADIUS, PACMAN_RADIUS, 45, 270);
        pacmanCharacter.setType(ArcType.ROUND);
        pacmanCharacter.setFill(Color.YELLOW);
        Label pacmanLabel = new Label("LET'S EAT GHOSTS.\nPLEASE, CONTINUE! ↘");
        pacmanLabel.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
        pacmanLabel.setTextFill(Color.WHITE);
        pacmanLabel.setOpacity(0.0);
        pacmanLabel.setEffect(glowEffect);

        HBox pacmanAnimationStage = new HBox(pacmanCharacter,pacmanLabel);
        pacmanAnimationStage.setSpacing(15);
        pacmanAnimationStage.setAlignment(Pos.CENTER);

        Timeline entertainmentAnimation = new Timeline(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(pacmanCharacter.startAngleProperty(),45),
                        new KeyValue(pacmanCharacter.lengthProperty(),270),
                        new KeyValue(pacmanLabel.opacityProperty(), 0.0),
                        new KeyValue(pacmanLabel.translateXProperty(),2)
                ),
                new KeyFrame(Duration.seconds(0.3),
                        new KeyValue(pacmanCharacter.startAngleProperty(),0),
                        new KeyValue(pacmanCharacter.lengthProperty(),360)
                ),
                new KeyFrame(Duration.seconds(0.6),
                        new KeyValue(pacmanCharacter.radiusXProperty(),PACMAN_RADIUS+10),
                        new KeyValue(pacmanCharacter.radiusYProperty(),PACMAN_RADIUS+10)
                ),
                new KeyFrame(Duration.seconds(1.0),
                        new KeyValue(pacmanCharacter.startAngleProperty(),0),
                        new KeyValue(pacmanCharacter.lengthProperty(),360),
                        new KeyValue(pacmanCharacter.radiusXProperty(),PACMAN_RADIUS+10),
                        new KeyValue(pacmanCharacter.radiusYProperty(),PACMAN_RADIUS+10)
                ),
                new KeyFrame(Duration.seconds(1.3),
                        new KeyValue(pacmanCharacter.startAngleProperty(),45),
                        new KeyValue(pacmanCharacter.lengthProperty(),270),
                        new KeyValue(pacmanCharacter.radiusXProperty(),PACMAN_RADIUS),
                        new KeyValue(pacmanCharacter.radiusYProperty(),PACMAN_RADIUS),
                        new KeyValue(pacmanLabel.opacityProperty(), 0.0)
                ),
                new KeyFrame(Duration.seconds(2.0),
                        new KeyValue(pacmanLabel.opacityProperty(), 1.0)
                ),
                new KeyFrame(Duration.seconds(5.0),
                        new KeyValue(pacmanLabel.opacityProperty(), 1.0),
                        new KeyValue(pacmanLabel.translateXProperty(),50)
                ),
                new KeyFrame(Duration.seconds(6.0),
                        new KeyValue(pacmanLabel.opacityProperty(), 0.0),
                        new KeyValue(pacmanLabel.translateXProperty(),60)
                )
        );
        entertainmentAnimation.setCycleCount(1);

        VBox containerForPacmanAnimation = new VBox(pacmanAnimationStage);
        containerForPacmanAnimation.setSpacing(50);
        containerForPacmanAnimation.setPadding(new Insets(0,0,150,0));
        containerForPacmanAnimation.setAlignment(Pos.CENTER);


        continueButton.setPrefSize(200,50);
        VBox containerForContinueButton = new VBox(continueButton);
        containerForContinueButton.setAlignment(Pos.BOTTOM_RIGHT);
        containerForContinueButton.setPadding(new Insets(20));

        pacmanCharacter.setOnMouseEntered(mouseEvent -> {
            entertainmentAnimation.play();
        });
        buttonsAnimation.play();
        labelAnimation.play();

        this.setTop(presentationVbox);
        this.setCenter(containerForPacmanAnimation);
        this.setBottom(containerForContinueButton);
    }


    /**
     * registerHandlers function
     * Register the continueButton event
     * @return void
     * */
    private void registerHandlers() {
        continueButton.setOnAction(actionEvent ->{
            switchToMainMenuUI();
        });
    }

    /**
     * switchToMainMenuUI function
     * Create a new scene, and apply to the stage switching for MainMenuUI
     * @return void
     * */
    private void switchToMainMenuUI(){
        Stage thisStage = (Stage)this.getScene().getWindow();
        Scene newScene = new Scene(new MainMenuUI(tinyPacModelManager),1000,700);
        thisStage.setScene(newScene);
    }

}
