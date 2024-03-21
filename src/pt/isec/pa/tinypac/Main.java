package pt.isec.pa.tinypac;

import javafx.application.Application;
import pt.isec.pa.tinypac.gameengine.GameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.model.TinyPacModelManager;
import pt.isec.pa.tinypac.ui.gui.MainJFX;

/**
 * Classe Main
 *
 * @author Nuno Domingues
 *
 */
public class Main {

    /**
     * Modelo de dados
     */
    public static TinyPacModelManager model = null;


    /**
     * @param args string array
     * @return void
     */
    public static void main(String[] args) {
        model = new TinyPacModelManager();
        IGameEngine gameEngine = new GameEngine();
        gameEngine.registerClient(model);
        gameEngine.start(150);

        Application.launch(MainJFX.class,args);

        gameEngine.stop();
        gameEngine.waitForTheEnd();

    }
}