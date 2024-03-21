package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.MessageElementToWorld;
import pt.isec.pa.tinypac.model.data.PacWorld;

import java.util.ArrayList;
import java.util.List;

public class GhostBlinky extends Ghost {

    public static final char SYMBOL = 'B';


    private GhostState ghostState;
    private ArrowDirection arrowDirection;

    public GhostBlinky(PacWorld pacWorld) {
        super(pacWorld);
        this.ghostState = GhostState.ON_CAVE;
    }



    private List<ArrowDirection> allowDirections(
            List<DetailsOfNeighborhood> detailsOfNeighborhood){

        List<ArrowDirection> arrowDirections = new ArrayList<>();

        switch (arrowDirection){
            case UP -> {
                if(detailsOfNeighborhood.get(0).element() instanceof Ball
                        || detailsOfNeighborhood.get(0).element() instanceof SuperBall ||
                            detailsOfNeighborhood.get(0).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(0).arrowDirection());
                }
                if(detailsOfNeighborhood.get(2).element() instanceof Ball
                        || detailsOfNeighborhood.get(2).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(2).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(2).arrowDirection());
                }
                if(detailsOfNeighborhood.get(3).element() instanceof Ball
                        || detailsOfNeighborhood.get(3).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(3).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(3).arrowDirection());
                }

                if(arrowDirections.isEmpty())
                    arrowDirections.add(ArrowDirection.DOWN);

            }
            case DOWN -> {
                if(detailsOfNeighborhood.get(1).element() instanceof Ball
                        || detailsOfNeighborhood.get(1).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(1).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(1).arrowDirection());
                }
                if(detailsOfNeighborhood.get(2).element() instanceof Ball
                        || detailsOfNeighborhood.get(2).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(2).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(2).arrowDirection());
                }
                if(detailsOfNeighborhood.get(3).element() instanceof Ball
                        || detailsOfNeighborhood.get(3).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(3).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(3).arrowDirection());
                }

                if(arrowDirections.isEmpty())
                    arrowDirections.add(ArrowDirection.UP);


            }
            case RIGHT -> {
                if(detailsOfNeighborhood.get(0).element() instanceof Ball
                        || detailsOfNeighborhood.get(0).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(0).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(0).arrowDirection());
                }
                if(detailsOfNeighborhood.get(1).element() instanceof Ball
                        || detailsOfNeighborhood.get(1).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(1).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(1).arrowDirection());
                }
                if(detailsOfNeighborhood.get(3).element() instanceof Ball
                        || detailsOfNeighborhood.get(3).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(3).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(3).arrowDirection());
                }

                if(arrowDirections.isEmpty())
                    arrowDirections.add(ArrowDirection.LEFT);


            }
            case LEFT -> {
                if(detailsOfNeighborhood.get(0).element() instanceof Ball
                        || detailsOfNeighborhood.get(0).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(0).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(0).arrowDirection());
                }
                if(detailsOfNeighborhood.get(1).element() instanceof Ball
                        || detailsOfNeighborhood.get(1).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(1).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(1).arrowDirection());
                }
                if(detailsOfNeighborhood.get(2).element() instanceof Ball
                        || detailsOfNeighborhood.get(2).element() instanceof SuperBall ||
                        detailsOfNeighborhood.get(2).element() == null)
                {
                    arrowDirections.add(detailsOfNeighborhood.get(2).arrowDirection());
                }

                if(arrowDirections.isEmpty())
                    arrowDirections.add(ArrowDirection.RIGHT);
            }
        }

        return arrowDirections;
    }


    private void calculateNewPosition(PacWorld.Position myPos) {
        var elements = super.checkNeighborhood(myPos);//0 -> UP; 1 -> DOWN; 2 -> LEFT; 3 -> RIGHT
        var allowedDirection = allowDirections(elements);
        var decideDirection = generateRandomDirection(allowedDirection);
        moveGhost(decideDirection,myPos);
    }

    private void moveGhost(ArrowDirection decideDirection, PacWorld.Position myPos){

        switch (decideDirection) {
            case UP -> {
                super.previousList.add(new Previous(pacWorld.getElement(myPos.line() - 1,myPos.column()),
                        myPos.line() - 1,myPos.column()));
                pacWorld.addElement(null, myPos.line(),myPos.column());
                pacWorld.addElement(this, myPos.line() - 1, myPos.column());
                arrowDirection = ArrowDirection.UP;
            }
            case DOWN -> {
                super.previousList.add(new Previous(pacWorld.getElement(myPos.line() + 1,myPos.column()),
                        myPos.line() + 1,myPos.column()));
                pacWorld.addElement(null, myPos.line(),myPos.column());
                pacWorld.addElement(this, myPos.line() + 1, myPos.column());
                arrowDirection = ArrowDirection.DOWN;
            }
            case LEFT -> {
                super.previousList.add(new Previous(pacWorld.getElement(myPos.line(),myPos.column() - 1),
                        myPos.line(),myPos.column() - 1));
                pacWorld.addElement(null, myPos.line(),myPos.column());
                pacWorld.addElement(this, myPos.line(), myPos.column() - 1);
                arrowDirection = ArrowDirection.LEFT;
            }
            case RIGHT -> {
                super.previousList.add(new Previous(pacWorld.getElement(myPos.line(),myPos.column() + 1),
                        myPos.line(),myPos.column()+1));
                pacWorld.addElement(null, myPos.line(),myPos.column());
                pacWorld.addElement(this, myPos.line(), myPos.column() + 1);
                arrowDirection = ArrowDirection.RIGHT;
            }
        }
    }


    @Override
    public void startMoving() {
        ghostState = GhostState.GOING_OUT;
    }

    public boolean verifyIfPacmanIsOnTheWay(PacWorld.Position myPosition){
        switch (arrowDirection){
            case UP -> {
                if(pacWorld.getElement(myPosition.line()-1, myPosition.column()) instanceof Pacman){
                    pacWorld.addElement(null,myPosition.line()-1, myPosition.column());
                    return true;
                }

            }
            case DOWN -> {
                if(pacWorld.getElement(myPosition.line()+1, myPosition.column()) instanceof Pacman){
                    pacWorld.addElement(null,myPosition.line()+1, myPosition.column());
                    return true;
                }
            }
            case LEFT -> {
                if(pacWorld.getElement(myPosition.line(), myPosition.column()-1) instanceof Pacman){
                    pacWorld.addElement(null,myPosition.line(), myPosition.column()-1);
                    return true;
                }
            }
            case RIGHT -> {
                if(pacWorld.getElement(myPosition.line(), myPosition.column()+1) instanceof Pacman){
                    pacWorld.addElement(null,myPosition.line(), myPosition.column()+1);
                    return true;
                }

            }
        }
        return false;
    }


    @Override
    public MessageElementToWorld evolve(boolean isCounter) {
        super.restore();
        if(ghostState == GhostState.OUTSIDE){
            PacWorld.Position myPos = pacWorld.getPositionOf(this);
                if (!isCounter) {
                    if (verifyIfPacmanIsOnTheWay(myPos))
                        return MessageElementToWorld.PACMAN_IN_FRONT;
                }
                calculateNewPosition(myPos);
        }
        if(ghostState == GhostState.GOING_OUT){
            moveToPortal(this);
            ghostState = GhostState.OUTSIDE;
            arrowDirection = generateRandomDirection();
        }
        super.restore();
        return MessageElementToWorld.NONE;
    }




    @Override
    public char getSymbol() {return SYMBOL;}
}
