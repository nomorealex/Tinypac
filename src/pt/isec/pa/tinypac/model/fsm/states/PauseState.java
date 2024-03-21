package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.model.fsm.TinyPacStateAdapter;

public class PauseState extends TinyPacStateAdapter {

    private TinyPacState oldTinyPacState;

    public PauseState(PacWorld pacWorld, TinyPacContext tinyPacContext, TinyPacState oldTinyPacState) {
        super(pacWorld, tinyPacContext);
        this.oldTinyPacState = oldTinyPacState;
    }


    @Override
    public void resume(){
        if(this.oldTinyPacState != null)
            changeState(this.oldTinyPacState);
    }


    @Override
    public TinyPacState getState() {
        return TinyPacState.PAUSE_STATE;
    }
}
