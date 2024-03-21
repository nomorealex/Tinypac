package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.factories.TinyPacStateFactory;

/**
 * Class TinyPacStateAdapter
 * <p>Base Class for States</p>
 * @author Nuno Domingues
 *
 */
public abstract class TinyPacStateAdapter implements ITinyPacState {

    /**
     * Reference to the data model
     * */
    protected PacWorld pacWorld;

    /**
     * Reference to the game context
     */
    protected TinyPacContext tinyPacContext;


    /**
     * Construtor
     * @param pacWorld reference to data model
     * @param tinyPacContext reference to the game context
     * */
    protected TinyPacStateAdapter(PacWorld pacWorld, TinyPacContext tinyPacContext) {
        this.pacWorld = pacWorld;
        this.tinyPacContext = tinyPacContext;

    }

    /**
     * show function
     * Method that returns the char pacWorld Array
     * @return char[][]
     * */
    public char[][] show(){ return pacWorld.getPacWorld();}

    /**
     * changeState function
     * change the state for the provided
     * @param newState change for newState
     * @return void
     * */
    protected void changeState(TinyPacState newState){
        tinyPacContext.changeState(TinyPacStateFactory.createState(newState, pacWorld,tinyPacContext));
    }

    protected void changeState(TinyPacState newState, TinyPacState oldState){
        tinyPacContext.changeState(TinyPacStateFactory.createState(newState, pacWorld,tinyPacContext,oldState));
    }

}
