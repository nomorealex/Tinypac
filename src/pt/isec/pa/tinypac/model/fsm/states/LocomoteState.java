package pt.isec.pa.tinypac.model.fsm.states;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.MazeData;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.factories.MazeBoardFactory;
import pt.isec.pa.tinypac.model.fsm.TinyPacContext;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.model.fsm.TinyPacStateAdapter;

public class LocomoteState extends TinyPacStateAdapter {

    public LocomoteState(PacWorld pacWorld, TinyPacContext tinyPacContext) {
        super(pacWorld, tinyPacContext);
        pacWorld.startGhost();
    }


    @Override
    public boolean move(ArrowDirection arrowDirection) {
        pacWorld.setPacmanArrowDirection(arrowDirection);
        return true;
    }

    @Override
    public void pause(){
        changeState(TinyPacState.PAUSE_STATE,TinyPacState.LOCOMOTE_STATE);
    }

    @Override
    public void register(String name){
        pacWorld.setTop5(name);
    }

    public boolean changeLevel(){
        if(pacWorld.getLevel() == 1){
            return true;
        }
        pacWorld.setLevel(pacWorld.getLevel()+1);
        MazeData mazeData = MazeBoardFactory.getMazeData(pacWorld.getLevel(), pacWorld);
        pacWorld.setMazeData(mazeData);
        changeState(TinyPacState.PREPARE_STATE);
        return false;
    }

    private boolean restartLevel() {
        if(pacWorld.getLifeScore() == 0){
            return true;
        }
        MazeData mazeData = MazeBoardFactory.getMazeData(pacWorld.getLevel(), pacWorld);
        pacWorld.setMazeData(mazeData);
        pacWorld.setLifeScore(pacWorld.getLifeScore()-1);
        changeState(TinyPacState.PREPARE_STATE);
        return false;
    }



    @Override
    public boolean evolve(){
        if(!pacWorld.isFinish()) {
            switch (pacWorld.evolveGhostsEatPacman(true)){
                case PACMAN_IN_FRONT -> {
                    if(restartLevel()) {
                        pacWorld.setFinish(true);
                        return true;
                    }
                }
                case SUPERBALL_EATED -> {
                    changeState(TinyPacState.COUNTERATTACK_STATE);
                }
                case GHOST_IN_FRONT -> {

                }
                case NO_BALLS -> {
                    if(changeLevel()) {
                        pacWorld.setFinish(true);
                        return true;
                    }
                }
                case NONE -> {}
            }
        }
        return false;
    }

    @Override
    public TinyPacState getState() {
        return TinyPacState.LOCOMOTE_STATE;
    }
}
