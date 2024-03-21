package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.model.fsm.TinyPacStateAdapter;

public class WaitingTimeState extends TinyPacStateAdapter {

    private long oldtime;

    public WaitingTimeState(PacWorld pacWorld, TinyPacContext tinyPacContext) {
        super(pacWorld, tinyPacContext);
        oldtime = System.currentTimeMillis();
    }


    @Override
    public boolean move(ArrowDirection arrowDirection) {
        pacWorld.setPacmanArrowDirection(arrowDirection);
        return true;
    }

    @Override
    public void pause(){
        changeState(TinyPacState.PAUSE_STATE,TinyPacState.WAITINGTIME_STATE);
    }


    @Override
    public boolean evolve(){
        pacWorld.evolveGhostsEatPacman(false);
        if((System.currentTimeMillis() - oldtime) >= 5000){
            //pacWorld.startGhost();
            changeState(TinyPacState.LOCOMOTE_STATE);
        }
        return false;
    }

    @Override
    public TinyPacState getState() {
        return TinyPacState.WAITINGTIME_STATE;
    }
}
