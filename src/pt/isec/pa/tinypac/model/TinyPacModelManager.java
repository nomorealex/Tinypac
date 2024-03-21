package pt.isec.pa.tinypac.model;

import pt.isec.pa.tinypac.comunication.Messages;
import pt.isec.pa.tinypac.comunication.PropsChange;
import pt.isec.pa.tinypac.gameengine.IGameEngine;
import pt.isec.pa.tinypac.gameengine.IGameEngineEvolve;
import pt.isec.pa.tinypac.model.data.MazeData;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.data.Top5Data;
import pt.isec.pa.tinypac.model.factories.MazeBoardFactory;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Class TinyPacModelManager
 * <p>Class that handles the comunication between the UI and the model data, this is the class which is register in the GameEngine</p>
 * @author Nuno Domingues
 *
 */
public class TinyPacModelManager implements IGameEngineEvolve {

    //TODO::
    //Falta ver os movimentos dos fantasmas
    //Falta ver a pontuação das bolas e em geral
    //Falta ver a questão do final do game;(ou seja quando perde as três vidas ou quando passa os 20 niveis)
    //Javadoc
    //relatório
    //Acelerar a velocidade dos fantasmas e diminuir a vulnerabilidade dos fantasmas(desacelerar o pacman)
    //PropsChange para a status's bar
    //retirar máximo possível a lógica da UI
    //Toda a alteração ao modelo de dados deve acontecer via estados
    //em cada tick ir buscar o tempo do sistema e subtrair
    //evolves diferentes para counterAttack e locomote,é o pacman primeiro a avançar ou os ghosts
    //alterar a questão de passar para o prepareState e depois acabar o jogo
    //fazer o caminho para trás quando o pacman come um fantasma


    /**
     * tinyPacContext is the context of the application handle the redirection to the active state
     * */
    private TinyPacContext tinyPacContext;

    /**
     * pacWorld
     * */
    private PacWorld pacWorld;

    /**
     * propertyChangeSupport handle the events to update the views on the UI
     * */
    private PropertyChangeSupport propertyChangeSupport;



    /**
     * Constructor
     * Initiate the PropertyChangeSupport
     * */
    public TinyPacModelManager() {
        propertyChangeSupport = new PropertyChangeSupport(this);
    }


    /**
     * addListener function
     * Regist a new object to a property, so when that property fire that object will be update
     * @param listener
     * @param property
     * @return void
     * */
    public void addListener(String property, PropertyChangeListener listener){
        propertyChangeSupport.addPropertyChangeListener(property,listener);

    }

    public void removeListener(String property, PropertyChangeListener listener){
        propertyChangeSupport.removePropertyChangeListener(property, listener);
    }


    /**
     * isExistGameSaved function
     * To see if a game is saved
     * @return boolean
     * */
    public boolean isExistGameSaved(){
        return new File("./savedgame/backupGame.dat").exists();
    }


    /**
     * startGame function
     * Method to start the game
     * @param msg give information about if it is to load the previous game or to generate a new game
     * @return void
     * */
    public void startGame(Messages msg){
        if(tinyPacContext == null) {
            switch (msg) {
                case LOAD -> {
                    if (!isExistGameSaved()) {
                        generateNewPacWorld();
                    } else {
                        loadPacWorld();
                    }
                }
                case NOT_LOAD -> generateNewPacWorld();
            }
        }

    }

    /**
     * generateNewPacWorld function
     * Generate a new PacWorld
     * @return void
     * */
    private void generateNewPacWorld(){
        pacWorld = new PacWorld();
        MazeData mazeData = MazeBoardFactory.getMazeData(1,pacWorld);
        if(mazeData == null)
            return;
        pacWorld.setMazeData(mazeData);
        this.tinyPacContext = new TinyPacContext(pacWorld);
    }

    /**
     * getPacWorldHeight function
     * returns the pacWorld height
     * @return int
     * */
    public int getPacWorldHeight(){
        return tinyPacContext.getRefPacWorld().getHeight();
    }

    /**
     * getPacWorldWidth function
     * returns the pacWorld width
     * @return int
     * */
    public int getPacWorldWidth(){
        return tinyPacContext.getRefPacWorld().getWidth();
    }

    /**
     * getPacmanArrowDirection function
     * returns the pacman direction
     * @return ArrowDirection
     * */
    public ArrowDirection getPacmanArrowDirection() { return tinyPacContext.getPacmanArrowDirection();}


    /**
     * getFsmState function
     * returns the state of the FSM
     * @return TinyPacState
     * */
    public TinyPacState getFsmState(){return tinyPacContext.getState();}

    /**
     * move function
     * Invoke the method move which is defined in ITinyPacState
     * @param arrowDirection
     * @return void
     * */
    public void move(ArrowDirection arrowDirection){
        tinyPacContext.move(arrowDirection);
        propertyChangeSupport.firePropertyChange(PropsChange.PROPS_GAME_BOARD.getName(), null, null);
    }

    /**
     * pause function
     * Invoke the method pause which is defined in ITinyPacState
     * @return void
     * */
    public void pause(){
        if(getFsmState() != TinyPacState.PAUSE_STATE){
            tinyPacContext.pause();
            propertyChangeSupport.firePropertyChange(PropsChange.PROPS_GAME_BOARD.getName(), null,null);
        }
    }

    /**
     * resume function
     * Invoke the method resume which is defined in ITinyPacState
     * @return void
     * */
    public void resume() {
        savePacWorld();
        tinyPacContext.resume();
        propertyChangeSupport.firePropertyChange(PropsChange.PROPS_GAME_BOARD.getName(), null,null);
    }

    /**
     * register function
     * Invoke the method register which is defined in ITinyPacState
     * @param name
     * @return void
     * */
    public void register(String name) {
        tinyPacContext.register(name);
    }

    /**
     * getPoints function
     * returns the points of the game
     * @return int
     * */
    public int getPoints(){return tinyPacContext.getPoints();}


    /**
     * getLifeScore function
     * returns the lifeScore of the player
     * @return int
     * */
    public int getLifeScore() {return tinyPacContext.getLifeScore();}

    /**
     * getLevel function
     * returns the Level of the game
     * @return int
     * */
    public int getLevel() {return tinyPacContext.getLevel();}

    /**
     * getTop5 function
     * returns the top5 of the game
     * @return List<Top5Data>
     * */
    public List<Top5Data> getTop5(){
        if(tinyPacContext != null)
            return tinyPacContext.getTop5();
        return null;
    }

    /**
     * getWorld function
     * returns the pacWorld board of the game
     * @return char[][]
     * */
    public char[][] getWorld(){
        return tinyPacContext.getPacWorld();
    }


    /**
     * loadPacWorld function
     * Load a previous saved pacWorld game
     * @return void
     * */
    private void loadPacWorld(){
        try (ObjectInputStream ois =
                     new ObjectInputStream(
                             new FileInputStream("./savedgame/backupGame.dat")))
        {
            pacWorld = (PacWorld) ois.readObject();
            MazeData mazeData = MazeBoardFactory.getMazeData(pacWorld.getLevel(),pacWorld);
            if(mazeData == null)
                return;
            pacWorld.setMazeData(mazeData);
            this.tinyPacContext = new TinyPacContext(pacWorld);

        } catch (Exception e) {
            System.err.println("Error loading data");
        }

    }

    /**
     * savePacWorld function
     * Save thw current pacWorld game
     * @return void
     * */
    public void savePacWorld(){
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream("./savedgame/backupGame.dat")))
        {
            oos.writeObject(pacWorld);
        } catch (Exception e) {
            System.err.println("Error saving data");
        }
    }


    /**
     * evolve function
     * Method from IGameEngine, it's called by a defined interval.
     * */
    @Override
    public void evolve(IGameEngine gameEngine, long currentTime) {

        if(tinyPacContext != null){
            propertyChangeSupport.firePropertyChange(PropsChange.PROPS_GAME_BOARD.getName(),null,null);
            propertyChangeSupport.firePropertyChange(PropsChange.PROPS_GAME_LIFE_STATUS_BAR.getName(),null,null);
            if (tinyPacContext.getState() == TinyPacState.WAITINGTIME_STATE ||
                    tinyPacContext.getState() == TinyPacState.LOCOMOTE_STATE ||
                    tinyPacContext.getState() == TinyPacState.COUNTERATTACK_STATE) {
                if(tinyPacContext.evolve())
                    propertyChangeSupport.firePropertyChange(PropsChange.PROPS_GAME_TOP_MODAL.getName(), null,null);
            }
        }
    }


}
