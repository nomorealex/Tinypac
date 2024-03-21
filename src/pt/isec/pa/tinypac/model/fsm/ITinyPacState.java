package pt.isec.pa.tinypac.model.fsm;

import pt.isec.pa.tinypac.model.ArrowDirection;

public interface ITinyPacState {

    default boolean move(ArrowDirection arrowDirection){ return false; }

    default void pause(){}
    default void resume(){}



    default boolean evolve() {return false;}

    default void register(String name) {}



    char[][] show();

    TinyPacState getState();


}
