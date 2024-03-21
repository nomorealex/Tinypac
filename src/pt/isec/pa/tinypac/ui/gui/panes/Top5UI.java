package pt.isec.pa.tinypac.ui.gui.panes;

import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.model.data.Top5Data;
import pt.isec.pa.tinypac.ui.gui.controls.CustomButton;
import pt.isec.pa.tinypac.ui.gui.factories.AnimationButtonFactory;
import pt.isec.pa.tinypac.ui.gui.resources.managers.CSSManager;

import java.util.List;
/**
 * Class Top5UI
 * <p>Class that represents an interface for presentation(extends BorderPane)</p>
 * @author Nuno Domingues
 *
 */
public class Top5UI extends BorderPane {

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * TableView to visualize the top5 players
     */
    private final TableView top5TableView;

    /**
     * Back Button to return
     */
    private final CustomButton backButton;

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public Top5UI(TinyPacModelManager tinyPacModelManager) {
        this.tinyPacModelManager = tinyPacModelManager;
        top5TableView = new TableView<>();
        backButton = new CustomButton("Back",false,false);
        createViews();
        registerHandlers();
    }

    /**
     * createViews function
     * Create the elements that of the interface like the button and the tableview
     * @return void
     * */
    private void createViews() {
        CSSManager.applyCSS(this,"myStyleSheet.css");
        this.setStyle("-fx-background-color: linear-gradient(to top left, #18595b, #2b4e83);");

        TableColumn<Top5Data, String> username = new TableColumn<Top5Data,String>("User Name");
        username.setCellValueFactory(new PropertyValueFactory<Top5Data,String>("name"));
        TableColumn<Top5Data,Integer> points = new TableColumn<Top5Data,Integer>("User Points");
        points.setCellValueFactory(new PropertyValueFactory<Top5Data,Integer>("points"));

        top5TableView.setPlaceholder(new Label("NO DATA!"));
        top5TableView.setMaxHeight(350);
        top5TableView.setMaxWidth(500);

        top5TableView.getColumns().add(username);
        top5TableView.getColumns().add(points);

        top5TableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        top5TableView.setEditable(false);

        List<Top5Data> list = tinyPacModelManager.getTop5();
        if(list != null){
            top5TableView.getItems().clear();
            for(Top5Data el : list){
                System.out.println("el"+el);
                top5TableView.getItems().add(el);
            }
        }

        backButton.setPrefSize(200,50);

        Timeline buttonAnimation = AnimationButtonFactory.createAnimation(true,backButton);
        buttonAnimation.play();

        VBox vBox = new VBox(backButton);
        vBox.setAlignment(Pos.BOTTOM_CENTER);
        vBox.setPadding(new Insets(0,0,20,0));

        setCenter(top5TableView);
        setBottom(vBox);

    }


    /**
     * registerHandlers function
     * Register the backButton event
     * @return
     * */
    private void registerHandlers() {
        backButton.setOnAction(actionEvent ->{
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
