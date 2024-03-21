package pt.isec.pa.tinypac.model.data.elements;

public class WrapZone extends InanimatedPacWorldElements {

    public static final char SYMBOL = 'W';

    public WrapZone() {
        super();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
