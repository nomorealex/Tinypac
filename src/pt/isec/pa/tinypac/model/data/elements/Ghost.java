package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.MessageElementToWorld;
import pt.isec.pa.tinypac.model.data.PacWorld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ghost extends AnimatedPacWorldElements {

    public record Previous(PacWorldElements element, int line, int column){}

    enum GhostState{ON_CAVE, GOING_OUT, OUTSIDE}

    protected List<Previous> previousList;


    protected Ghost(PacWorld pacWorld) {
        super(pacWorld);
        previousList = new ArrayList<>();
    }

    protected static ArrowDirection generateRandomDirection() {
        Random random = new Random();
        int index = random.nextInt(ArrowDirection.values().length);
        return ArrowDirection.values()[index];
    }

    protected static ArrowDirection generateRandomDirection(List<ArrowDirection> arrowDirections) {
        Random random = new Random();
        int index = random.nextInt(arrowDirections.size());
        return arrowDirections.get(index);
    }

    protected void moveToPortal(Ghost ghost){
        var portalPosition = pacWorld.getPositionOfPortal();
        var portalInstance = pacWorld.getElement(portalPosition.line(), portalPosition.column());
        var ghostPosition = pacWorld.getPositionOf(ghost);
        previousList.add(new Previous(portalInstance, portalPosition.line(), portalPosition.column()));
        pacWorld.addElement(new GhostsCave(),ghostPosition.line(),ghostPosition.column());
        pacWorld.addElement(ghost,portalPosition.line(),portalPosition.column());
    }

    public void restore(){
        if(previousList.isEmpty())
            return;

        List<Integer> indexToRemove = new ArrayList<>();


        for(int i = 0; i< previousList.size(); ++i){
            if(pacWorld.getElement(previousList.get(i).line(),previousList.get(i).column()) == null){
                pacWorld.addElement(previousList.get(i).element(),previousList.get(i).line(),previousList.get(i).column());
                indexToRemove.add(i);
            }
        }

        for(var index : indexToRemove)
            previousList.remove(index.intValue());
    }


    public abstract void startMoving();
    @Override
    public MessageElementToWorld evolve(boolean isCounter) { return MessageElementToWorld.NONE;}
    @Override
    public char getSymbol() {return 0;}
}
