package pt.isec.pa.tinypac.model.data.elements;

public class Fruit extends InanimatedPacWorldElements {
    public static final char SYMBOL = 'F';

    public Fruit() {
        super();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
