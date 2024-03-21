package pt.isec.pa.tinypac.model.factories;

import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.fsm.ITinyPacState;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.model.fsm.states.CounterAttackState;
import pt.isec.pa.tinypac.model.fsm.states.LocomoteState;
import pt.isec.pa.tinypac.model.fsm.states.PauseState;
import pt.isec.pa.tinypac.model.fsm.states.PrepareState;
import pt.isec.pa.tinypac.model.fsm.states.WaitingTimeState;

public class TinyPacStateFactory {

    public static ITinyPacState createState(TinyPacState type, PacWorld pacWorld, TinyPacContext tinyPacContext){
        
        return switch(type){
            case PREPARE_STATE -> new PrepareState(pacWorld,tinyPacContext);
            case LOCOMOTE_STATE -> new LocomoteState(pacWorld,tinyPacContext);
            case COUNTERATTACK_STATE -> new CounterAttackState(pacWorld,tinyPacContext);
            case PAUSE_STATE -> new PauseState(pacWorld,tinyPacContext,null);
            case WAITINGTIME_STATE -> new WaitingTimeState(pacWorld,tinyPacContext);
        };
    }

    public static ITinyPacState createState(TinyPacState type, PacWorld pacWorld, TinyPacContext tinyPacContext, TinyPacState oldTinyPacState){

        return switch(type){
            case PREPARE_STATE -> new PrepareState(pacWorld,tinyPacContext);
            case LOCOMOTE_STATE -> new LocomoteState(pacWorld,tinyPacContext);
            case COUNTERATTACK_STATE -> new CounterAttackState(pacWorld,tinyPacContext);
            case PAUSE_STATE -> new PauseState(pacWorld,tinyPacContext,oldTinyPacState);
            case WAITINGTIME_STATE -> new WaitingTimeState(pacWorld,tinyPacContext);
        };
    }
}
