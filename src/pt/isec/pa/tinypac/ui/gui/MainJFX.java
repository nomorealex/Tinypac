package pt.isec.pa.tinypac.ui.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import pt.isec.pa.tinypac.Main;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.panes.RootPaneUI;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;

/**
 * Class MainJFX
 *
 * @author Nuno Domingues
 *
 */
public class MainJFX extends Application {

    /**
     * Reference for data model
     */
    TinyPacModelManager tinyPacModelManager;


    @Override
    public void init() throws Exception {
        super.init();
        this.tinyPacModelManager = Main.model;
    }


    /**
     * start function of Application
     * <p>Specification of the stages</p>
     * @return void
     */
    @Override
    public void start(Stage stage) throws Exception {
        RootPaneUI root = new RootPaneUI(tinyPacModelManager);
        Scene scene = new Scene(root,1000,750);
        stage.setScene(scene);
        stage.setTitle("©TinyPAC - 22/23");
        stage.setMinWidth(800);
        stage.setMinHeight(700);
        stage.setMaxWidth(1000);
        stage.setMaxHeight(800);
        stage.setX(Screen.getPrimary().getVisualBounds().getMinX());
        stage.setY(Screen.getPrimary().getVisualBounds().getMinY());
        stage.show();

        try {
            Image logo = ImageManager.getImage("pacmanLogo.png");
            stage.getIcons().add(logo);
        }catch (NullPointerException e){}

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
                t.consume();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Exit Game!");
                alert.setHeaderText(null);
                alert.setContentText("Do you really want to leave the game?");

                final Stage closedStage = (Stage) alert.getDialogPane().getScene().getWindow();
                try {
                    Image logo = ImageManager.getImage("pacmanLogo.png");
                    closedStage.getIcons().add(logo);
                }catch (NullPointerException e){}

                ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yesButton, noButton);
                alert.showAndWait();

                if (alert.getResult().getButtonData() == ButtonBar.ButtonData.YES) {
                    closedStage.close();
                    Platform.exit();
                }
            }
        });

        /*
        Stage stage1 = new Stage();

        RootPaneUI root1 = new RootPaneUI(tinyPacModelManager);
        Scene scene1 = new Scene(root1,1000,750);
        stage1.setScene(scene1);
        stage1.setTitle("©TinyPAC - 22/23 0101");
        stage1.setMinWidth(800);
        stage1.setMinHeight(700);
        stage1.setMaxWidth(1000);
        stage1.setMaxHeight(800);
        stage1.setX(Screen.getPrimary().getVisualBounds().getMinX());
        stage1.setY(Screen.getPrimary().getVisualBounds().getMinY());
        stage1.show();
        */
    }


    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
