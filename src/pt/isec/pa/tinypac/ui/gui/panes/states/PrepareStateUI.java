package pt.isec.pa.tinypac.ui.gui.panes.states;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import pt.isec.pa.tinypac.comunication.PropsChange;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.ui.gui.factories.MazeGuiFactory;

/**
 * Class PrepareStateUI
 * <p>Class that represents an interface for presentation(extends Canvas)</p>
 * @author Nuno Domingues
 *
 */
public class PrepareStateUI extends Canvas {

    /**
     * Reference for data model
     */
    private TinyPacModelManager tinyPacModelManager;

    /**
     * Constructor
     * @param tinyPacModelManager to obtain a reference to model data
     * */
    public PrepareStateUI(TinyPacModelManager tinyPacModelManager) {
        super(tinyPacModelManager.getPacWorldWidth()*20, tinyPacModelManager.getPacWorldHeight()*20);
        this.tinyPacModelManager = tinyPacModelManager;
        registerPropertyListener();
        update();
    }

    /**
     * registerPropertyListener function
     * Register the PropertyListener
     * @return void
     * */
    private void registerPropertyListener() {
        tinyPacModelManager.addListener(PropsChange.PROPS_GAME_BOARD.getName(),eve -> Platform.runLater(this::update));
    }

    /**
     * update function
     * Called by the Property change listener to print the board game
     * @return void
     * */
    private void update(){
        if(tinyPacModelManager.getFsmState() != TinyPacState.PREPARE_STATE) {
            setVisible(false);
            return;
        }
        setVisible(true);
        GraphicsContext gc = this.getGraphicsContext2D();
        MazeGuiFactory.printMaze(tinyPacModelManager.getPacmanArrowDirection(),tinyPacModelManager.getFsmState(),tinyPacModelManager.getWorld(),gc);
    }
}


