package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.MessageElementToWorld;
import pt.isec.pa.tinypac.model.data.PacWorld;

import java.util.List;

public class Pacman extends AnimatedPacWorldElements {

    public static final char SYMBOL = 'M';
    private ArrowDirection newArrowDirection;
    private ArrowDirection oldArrowDirection;

    public Pacman(PacWorld pacWorld) {
        super(pacWorld);
        setArrowDirection(ArrowDirection.RIGHT);
    }


    public void setArrowDirection(ArrowDirection arrowDirection) {
        this.newArrowDirection = arrowDirection;
    }
    public ArrowDirection getArrowDirection(){ return oldArrowDirection;}


    private PacWorld.Position calculatePositionForWrapZones(PacWorld.Position myPos, int wrapLine, int wrapColumn){
        List<PacWorld.Position> elements = pacWorld.getPositionOfWrapZones();
        for(var el : elements) {
            if (el.line() != wrapLine || el.column() != wrapColumn) {
                return pacWorld.getAdjacentFoodCells(el.line(), el.column());
            }
        }
        return null;
    }


    private LocationToGo directionToGo(PacWorld.Position myPos, ArrowDirection arrowDirection){
        var elementAhead = checkElementAhead(myPos,arrowDirection);

        if(elementAhead.element() == null){
            return elementAhead;
        }
        return switch (elementAhead.element().getSymbol()){
            case Ball.SYMBOL -> {
                pacWorld.setPoints();
                yield elementAhead;
            }
            case SuperBall.SYMBOL -> {
                pacWorld.setPoints(pacWorld.getPoints()+10);
                yield elementAhead;
            }
            case WrapZone.SYMBOL -> {
                var positionWrap=calculatePositionForWrapZones(myPos, elementAhead.line(), elementAhead.Column());
                yield new LocationToGo(elementAhead.element(), positionWrap.line(), positionWrap.column());
            }

            case GhostBlinky.SYMBOL,GhostClyde.SYMBOL,GhostInky.SYMBOL,GhostPinky.SYMBOL -> {
                yield elementAhead;
            }




            default -> null;
        };

    }

    private MessageElementToWorld checkAndMoveOrNot(PacWorld.Position myPos){

        var newPos = directionToGo(myPos, this.newArrowDirection);

        if(newPos != null){
            if(!(newPos.element() instanceof Ghost)) {
                pacWorld.addElement(null, myPos.line(), myPos.column());
                pacWorld.addElement(this, newPos.line(), newPos.Column());
                oldArrowDirection = newArrowDirection;
            }
            if(newPos.element() instanceof SuperBall)
                return MessageElementToWorld.SUPERBALL_EATED;
        }else{
            newPos = directionToGo(myPos, this.oldArrowDirection);
            if(newPos != null){
                if(!(newPos.element()instanceof Ghost)) {
                    pacWorld.addElement(null, myPos.line(), myPos.column());
                    pacWorld.addElement(this, newPos.line(), newPos.Column());
                }
                if(newPos.element() instanceof SuperBall)
                    return MessageElementToWorld.SUPERBALL_EATED;
            }
        }
        return MessageElementToWorld.NONE;
    }

    public MessageElementToWorld checkAndMoveOrNotOnCounter(PacWorld.Position myPos){
        var newPos = directionToGo(myPos, this.newArrowDirection);

        if(newPos != null){
            pacWorld.addElement(null, myPos.line(), myPos.column());
            pacWorld.addElement(this, newPos.line(), newPos.Column());
            oldArrowDirection = newArrowDirection;
            if(newPos.element() instanceof Ghost){
                return MessageElementToWorld.GHOST_IN_FRONT;
            }
        }else{
            newPos = directionToGo(myPos, this.oldArrowDirection);
            if(newPos != null){
                pacWorld.addElement(null, myPos.line(), myPos.column());
                pacWorld.addElement(this, newPos.line(), newPos.Column());
                if(newPos.element() instanceof Ghost){
                    return MessageElementToWorld.GHOST_IN_FRONT;
                }
            }
        }


        return MessageElementToWorld.NONE;
    }




    @Override
    public MessageElementToWorld evolve(boolean isCounter) {
        PacWorld.Position myPos = pacWorld.getPositionOf(this);
        MessageElementToWorld finalMessage;
        if(isCounter){
            finalMessage = checkAndMoveOrNotOnCounter(myPos);
        }else{
            finalMessage = checkAndMoveOrNot(myPos);
        }

        return finalMessage;
    }


    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
