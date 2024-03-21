package pt.isec.pa.tinypac.model.data.elements;

public class Wall extends InanimatedPacWorldElements { //Singleton to save memory, maybe irrelevant(in this case), but...

    public static final char SYMBOL = 'x';
    private static Wall _instance=null;

    public static Wall getInstance() {
        if (_instance == null)
            _instance = new Wall();
        return _instance;
    }

    private Wall(){
        super();
    }

    /*
    protected ArrayList<String> log;

    private Wall() {
        log = new ArrayList<>();
    }

    public void reset() {
        log.clear();
    }

    public void add(String msg) {
        log.add(msg);
    }

    public List<String> getLog() {
        return new ArrayList<>(log);
    }
     */

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
