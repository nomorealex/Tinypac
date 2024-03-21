package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.data.Top5Data;
import pt.isec.pa.tinypac.model.factories.TinyPacStateFactory;

import java.util.List;

/**
 * Class TinyPacContext
 * <p>Context that handle the state machine</p>
 * @author Nuno Domingues
 *
 */
public class TinyPacContext {

    private PacWorld pacWorld;
    private ITinyPacState state;


    public TinyPacContext(PacWorld pacWorld){
        this.pacWorld = pacWorld;
        state = TinyPacStateFactory.createState(TinyPacState.PREPARE_STATE,this.pacWorld,this);
    }


    public char[][] getPacWorld(){ return state.show();}

    public PacWorld getRefPacWorld(){ return pacWorld;}

    public TinyPacState getState(){ return state.getState();}

    public void changeState(ITinyPacState newState){
        state = newState;
    }

    public void move(ArrowDirection arrowDirection){state.move(arrowDirection);}

    public void pause(){state.pause();}
    public void resume() {state.resume();}


    public boolean evolve() {
        return state.evolve();
    }

    public void register(String name) {
        state.register(name);
    }

    public int getPoints() {
        return pacWorld.getPoints();
    }

    public int getLifeScore() { return pacWorld.getLifeScore();}

    public int getLevel() {return pacWorld.getLevel();}

    public ArrowDirection getPacmanArrowDirection(){return pacWorld.getPacmanArrowDirection();}

    public List<Top5Data> getTop5(){
        return pacWorld.getTop5();
    }

}
