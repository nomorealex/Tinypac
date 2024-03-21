package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.model.fsm.TinyPacStateAdapter;

public class PrepareState extends TinyPacStateAdapter {
    public PrepareState(PacWorld pacWorld, TinyPacContext tinyPacContext) {
        super(pacWorld, tinyPacContext);
    }
    @Override
    public boolean move(ArrowDirection arrowDirection) {
        pacWorld.setPacmanArrowDirection(arrowDirection);
        changeState(TinyPacState.WAITINGTIME_STATE);
        return true;
    }

    @Override
    public void pause(){
        changeState(TinyPacState.PAUSE_STATE,TinyPacState.PREPARE_STATE);
    }



    @Override
    public TinyPacState getState() {
        return TinyPacState.PREPARE_STATE;
    }
}
