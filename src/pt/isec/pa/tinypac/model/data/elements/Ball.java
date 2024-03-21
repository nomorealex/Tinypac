package pt.isec.pa.tinypac.model.data.elements;

public class Ball extends InanimatedPacWorldElements {

    public static final char SYMBOL = 'o';

    public Ball() {
        super();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
