package pt.isec.pa.tinypac.model.data.elements;

public class GhostsPortal extends InanimatedPacWorldElements {
    public static final char SYMBOL = 'Y';

    public GhostsPortal() {
        super();
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
