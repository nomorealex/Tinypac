package pt.isec.pa.tinypac.model.data.elements;

public class SuperBall extends InanimatedPacWorldElements {

    public static final char SYMBOL = 'O';

    public SuperBall() {
        super();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
