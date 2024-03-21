package pt.isec.pa.tinypac.model.data.elements;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.MessageElementToWorld;
import pt.isec.pa.tinypac.model.data.PacWorld;

import java.util.ArrayList;
import java.util.List;

public abstract class AnimatedPacWorldElements extends PacWorldElements {

    public record LocationToGo(PacWorldElements element, int line, int Column){}

    public record DetailsOfNeighborhood(ArrowDirection arrowDirection, PacWorldElements element, int line, int column){}

    PacWorld pacWorld;

    protected AnimatedPacWorldElements(PacWorld pacWorld) {
        super();
        this.pacWorld = pacWorld;
    }

    public abstract MessageElementToWorld evolve(boolean isCounter);


    protected List<DetailsOfNeighborhood> checkNeighborhood(PacWorld.Position myPos){
        List<DetailsOfNeighborhood> detailsOfNeighborhoods = new ArrayList<>();
        detailsOfNeighborhoods.add(
                new DetailsOfNeighborhood(ArrowDirection.UP,pacWorld.getElement(myPos.line()-1, myPos.column()), myPos.line()-1,myPos.column()));
        detailsOfNeighborhoods.add(
                new DetailsOfNeighborhood(ArrowDirection.DOWN,pacWorld.getElement(myPos.line()+1, myPos.column()), myPos.line()+1,myPos.column()));
        detailsOfNeighborhoods.add(
                new DetailsOfNeighborhood(ArrowDirection.LEFT,pacWorld.getElement(myPos.line(), myPos.column()-1), myPos.line(),myPos.column()-1));
        detailsOfNeighborhoods.add(
                new DetailsOfNeighborhood(ArrowDirection.RIGHT,pacWorld.getElement(myPos.line(), myPos.column()+1), myPos.line(),myPos.column()+1));
        return detailsOfNeighborhoods;
    }

    protected LocationToGo checkElementAhead(PacWorld.Position myPos, ArrowDirection arrowDirection){
        return switch(arrowDirection){
            case UP -> new LocationToGo(
                    pacWorld.getElement(myPos.line() - 1, myPos.column()),
                    myPos.line() - 1,
                    myPos.column()
            );
            case DOWN -> new LocationToGo(
                    pacWorld.getElement(myPos.line() + 1,myPos.column()),
                    myPos.line() + 1,
                    myPos.column()
            );
            case LEFT -> new LocationToGo(
                    pacWorld.getElement(myPos.line(), myPos.column() - 1),
                    myPos.line(),
                    myPos.column() - 1
            );
            case RIGHT -> new LocationToGo(
                    pacWorld.getElement(myPos.line(), myPos.column() + 1),
                    myPos.line(),
                    myPos.column() + 1
            );
        };
    }

}
