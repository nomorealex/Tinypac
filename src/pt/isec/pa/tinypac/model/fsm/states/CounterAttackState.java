package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.model.fsm.TinyPacStateAdapter;

public class CounterAttackState extends TinyPacStateAdapter {

    private long oldtime;
    private int numberOfGhostsEated;
    public CounterAttackState(PacWorld pacWorld, TinyPacContext tinyPacContext) {
        super(pacWorld, tinyPacContext);
        oldtime = System.currentTimeMillis();
        numberOfGhostsEated = 0;
    }

    @Override
    public boolean move(ArrowDirection arrowDirection) {
        pacWorld.setPacmanArrowDirection(arrowDirection);
        return true;
    }

    @Override
    public void pause(){
        changeState(TinyPacState.PAUSE_STATE,TinyPacState.COUNTERATTACK_STATE);
    }

    @Override
    public void register(String name){
        pacWorld.setTop5(name);
    }

    @Override
    public boolean evolve(){
        if(!pacWorld.isFinish()) {
            if((System.currentTimeMillis() - oldtime) > 7000){
                changeState(TinyPacState.LOCOMOTE_STATE);
                return false;
            }
            switch (pacWorld.evolvePacmanEatGhosts(true)){
                case PACMAN_IN_FRONT -> {

                }
                case SUPERBALL_EATED -> {
                    changeState(TinyPacState.COUNTERATTACK_STATE);
                }
                case GHOST_IN_FRONT -> {
                    if(numberOfGhostsEated == 0){
                        pacWorld.setPoints(pacWorld.getPoints()+50);
                    }else if(numberOfGhostsEated == 1){
                        pacWorld.setPoints(pacWorld.getPoints()+100);
                    }else if(numberOfGhostsEated == 2){
                        pacWorld.setPoints(pacWorld.getPoints()+150);
                    }else if(numberOfGhostsEated == 3){
                        pacWorld.setPoints(pacWorld.getPoints()+200);
                    }
                    numberOfGhostsEated++;
                }
                case NO_BALLS -> {
                }
                case NONE -> {}
            }

        }
        return false;
    }


    @Override
    public TinyPacState getState() {
        return TinyPacState.COUNTERATTACK_STATE;
    }
}

