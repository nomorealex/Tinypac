package pt.isec.pa.tinypac.model.data;

import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.elements.AnimatedPacWorldElements;
import pt.isec.pa.tinypac.model.data.elements.Ball;
import pt.isec.pa.tinypac.model.data.elements.Ghost;
import pt.isec.pa.tinypac.model.data.elements.GhostBlinky;
import pt.isec.pa.tinypac.model.data.elements.GhostClyde;
import pt.isec.pa.tinypac.model.data.elements.GhostInky;
import pt.isec.pa.tinypac.model.data.elements.GhostPinky;
import pt.isec.pa.tinypac.model.data.elements.GhostsCave;
import pt.isec.pa.tinypac.model.data.elements.GhostsPortal;
import pt.isec.pa.tinypac.model.data.elements.PacWorldElements;
import pt.isec.pa.tinypac.model.data.elements.Pacman;
import pt.isec.pa.tinypac.model.data.elements.SuperBall;
import pt.isec.pa.tinypac.model.data.elements.WrapZone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * Class PacWorld
 * <p>Class that contains all data about the game</p>
 * @author Nuno Domingues
 *
 */
public class PacWorld implements Serializable {

    static final long serialVersionUID = 1L;

    public record Position(int line, int column) {}
    private transient MazeData maze;
    private List<Top5Data> top5Data;
    private int level;
    private int points;
    private int lifeScore;

    private boolean finish;

    public PacWorld(){
        top5Data = new ArrayList<>();
        this.points = 0;
        this.level = 1;
        this.lifeScore = 3;
        this.finish = false;
    }

    public void setTop5(String name) {
        if(top5Data.size() < 5){
            top5Data.add(new Top5Data(name,points));
        } else if(top5Data.get(4).getPoints() < points){
            top5Data.set(4,new Top5Data(name,points));
        }
        top5Data.sort(Comparator.comparing(Top5Data::getPoints));
    }

    public List<Top5Data> getTop5(){
       return new ArrayList<>(top5Data);
    }

    public void setLifeScore(int lifeScore) {
        this.lifeScore = lifeScore;
    }

    public int getLifeScore() {
        return lifeScore;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public boolean existAnyBalls(){
        for(int i = 0;i < maze.height(); ++i){
            for(int j = 0; j < maze.width(); ++j){
                if(maze.maze().get(i,j) instanceof Ball ||
                        maze.maze().get(i,j) instanceof SuperBall ){
                    return true;
                }
            }
        }
        return false;
    }


    private void setMissingGhosts() {
        boolean blinkyIsMissing = true;
        boolean clydeIsMissing = true;
        boolean inkyIsMissing = true;
        boolean pinkyIsMissing = true;
        for(int i = 0;i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                if(getElement(i,j) instanceof GhostBlinky)
                    blinkyIsMissing = false;
                else if(getElement(i,j) instanceof GhostClyde)
                    clydeIsMissing = false;
                else if(getElement(i,j) instanceof GhostInky)
                    inkyIsMissing = false;
                else if(getElement(i,j) instanceof GhostPinky)
                    pinkyIsMissing = false;
            }
        }

        Random r = new Random();
        int resultLine = maze.caveStartLine();
        int resultColumn = maze.caveStartColumn();
        if(blinkyIsMissing){
            while(!(getElement(resultLine,resultColumn) instanceof GhostsCave)){
                resultLine = r.nextInt(maze.caveEndLine() - maze.caveStartLine()) + maze.caveStartLine();
                resultColumn = r.nextInt(maze.caveEndColumn() - maze.caveStartColumn()) + maze.caveStartColumn();
            }
            addElement(new GhostBlinky(this),resultLine,resultColumn);
        }
        if(clydeIsMissing){
            while(!(getElement(resultLine,resultColumn) instanceof GhostsCave)){
                resultLine = r.nextInt(maze.caveEndLine() - maze.caveStartLine()) + maze.caveStartLine();
                resultColumn = r.nextInt(maze.caveEndColumn() - maze.caveStartColumn()) + maze.caveStartColumn();
            }
            addElement(new GhostClyde(this),resultLine,resultColumn);
        }
        if(inkyIsMissing){
            while(!(getElement(resultLine,resultColumn) instanceof GhostsCave)){
                resultLine = r.nextInt(maze.caveEndLine() - maze.caveStartLine()) + maze.caveStartLine();
                resultColumn = r.nextInt(maze.caveEndColumn() - maze.caveStartColumn()) + maze.caveStartColumn();
            }
            addElement(new GhostInky(this),resultLine,resultColumn);
        }
        if(pinkyIsMissing){
            while(!(getElement(resultLine,resultColumn) instanceof GhostsCave)){
                resultLine = r.nextInt(maze.caveEndLine() - maze.caveStartLine()) + maze.caveStartLine();
                resultColumn = r.nextInt(maze.caveEndColumn() - maze.caveStartColumn()) + maze.caveStartColumn();
            }
            addElement(new GhostPinky(this),resultLine,resultColumn);
        }
    }

    public void startGhost(){
        setMissingGhosts();

        for(var ghost : getGhosts()){
            Position position = getPositionOf(ghost);
            if(itIsInCave(position)){
                ghost.startMoving();
            }
        }
    }

    private boolean itIsInCave(Position position) {
        if(position.line() >= maze.caveStartLine() && position.line() <= maze.caveEndLine() &&
            position.column() >= maze.caveStartColumn() && position.column() <= maze.caveEndColumn())
            return true;
        return false;
    }


    public void setMazeData(MazeData mazeData){
        maze = mazeData;
        this.level = maze.level();
    }

    public int getPoints() {
        return points;
    }

    public void setPoints() {
        this.points++;
    }
    public void setPoints(int points){
        this.points = points;
    }

    public int getLevel(){return this.level;}

    public void setLevel(int level){this.level = level;}

    public int getHeight() {
        return maze.height();
    }

    public int getWidth() {
        return maze.width();
    }

    public List<Ghost> getGhosts(){
        List<Ghost> lst = new ArrayList<>();
        for(int i = 0;i < maze.height(); ++i){
            for(int j = 0; j < maze.width(); ++j){
                if(maze.maze().get(i,j) instanceof Ghost ghost){
                    lst.add(ghost);
                }
            }
        }
        return lst;
    }






    public void setPacmanArrowDirection(ArrowDirection arrowDirection) {
        Pacman pacman = null;
        if((pacman = getPacman()) != null)
            pacman.setArrowDirection(arrowDirection);
    }

    private Pacman getPacman(){
        for(int i = 0;i < maze.height(); ++i){
            for(int j = 0; j < maze.width(); ++j){
                if(maze.maze().get(i,j) instanceof Pacman pacman){
                    return pacman;
                }
            }
        }
        return null;
    }

    public ArrowDirection getPacmanArrowDirection(){
        var pacman = getPacman();
        if(pacman != null)
            return pacman.getArrowDirection();
        return null;
    }

    public PacWorldElements getElement(int y, int x) {
        IMazeElement pacElement = maze.maze().get(y,x);
        if (pacElement instanceof PacWorldElements element)
            return element;
        return null;
    }

    public Position getPositionOfPortal(){
        for(int i = 0; i < maze.height();i++)
            for(int j = 0;j < maze.width(); j++)
                if (maze.maze().get(i,j) instanceof GhostsPortal)
                    return new Position(i,j);
        return null;
    }

    public List<Position> getPositionOfWrapZones(){
        List<Position> elements = new ArrayList<>();
        for(int i = 0; i < maze.height();i++)
            for(int j = 0;j < maze.width(); j++)
                if (maze.maze().get(i,j) instanceof WrapZone)
                    elements.add(new Position(i,j));
        return elements;
    }




    public Position getPositionOf(AnimatedPacWorldElements element) {
        for(int i = 0; i < maze.height();i++)
            for(int j = 0;j < maze.width(); j++)
                if (maze.maze().get(i,j) == element)
                    return new Position(i,j);
        return null;
    }

    private Position cellAvailable(int line, int column){
        if(this.getElement(line-1,column) instanceof Ball ||
                this.getElement(line-1,column) instanceof SuperBall ||
                this.getElement(line-1,column) == null){
            return new Position(line-1,column);
        } else if (this.getElement(line+1,column) instanceof Ball ||
                    this.getElement(line+1,column) instanceof SuperBall ||
                    this.getElement(line+1,column) == null) {
            return new Position(line+1,column);
        } else if (this.getElement(line,column-1) instanceof Ball ||
                    this.getElement(line,column-1) instanceof SuperBall ||
                    this.getElement(line,column-1) == null) {
            return new Position(line,column-1);
        } else if (this.getElement(line,column+1) instanceof Ball ||
                    this.getElement(line,column+1) instanceof SuperBall ||
                    this.getElement(line,column+1) == null) {
            return new Position(line,column+1);
        }
        return null;
    }

    public Position getAdjacentFoodCells(int line, int column){
        return cellAvailable(line, column);
    }



    public void addElement(PacWorldElements element, int posLine, int posColumn) {
        maze.maze().set(posLine,posColumn,element);
    }

    public char[][] getPacWorld(){return maze.maze().getMaze();}


    //first evolve...
    public MessageElementToWorld evolveGhostsEatPacman(boolean moveGhosts) {
        if(!moveGhosts){
            var pacman = getPacman();
            if(pacman != null)
                pacman.evolve(false);
            return MessageElementToWorld.NONE;
        }

        if(!existAnyBalls())
            return MessageElementToWorld.NO_BALLS;


        List<Ghost> ghostList = new ArrayList<>();
        Pacman pacman = null;
        for(int i = 0;i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                if(maze.maze().get(i,j) instanceof Pacman pacmanInstance){
                    pacman = pacmanInstance;
                }else if(maze.maze().get(i,j) instanceof Ghost ghostInstance){
                    ghostList.add(ghostInstance);
                }
            }
        }

        for(Ghost ghost : ghostList) {
            if (ghost.evolve(false) == MessageElementToWorld.PACMAN_IN_FRONT)
                return MessageElementToWorld.PACMAN_IN_FRONT;
        }


        if(pacman.evolve(false) == MessageElementToWorld.SUPERBALL_EATED)
            return MessageElementToWorld.SUPERBALL_EATED;

        return MessageElementToWorld.NONE;
    }




    //second evolve...
    public MessageElementToWorld evolvePacmanEatGhosts(boolean moveGhosts) {

        MessageElementToWorld finalMessage = MessageElementToWorld.NONE;
        if (!moveGhosts) {
            var pacman = getPacman();
            if (pacman != null)
                pacman.evolve(true);
            return finalMessage;
        }

        if (!existAnyBalls()) {
            finalMessage = MessageElementToWorld.NO_BALLS;
            return finalMessage;
        }

        Pacman pacman = getPacman();
        var messageFromPacman = pacman.evolve(true);

        if (messageFromPacman == MessageElementToWorld.GHOST_IN_FRONT) {
            finalMessage = MessageElementToWorld.GHOST_IN_FRONT;
        } else if (messageFromPacman == MessageElementToWorld.SUPERBALL_EATED) {
            finalMessage = MessageElementToWorld.SUPERBALL_EATED;
        }

        List<Ghost> ghostList = new ArrayList<>();
        for (int i = 0; i < maze.height(); ++i) {
            for (int j = 0; j < maze.width(); ++j) {
                if (maze.maze().get(i, j) instanceof Ghost ghostInstance) {
                    ghostList.add(ghostInstance);
                }
            }
        }

        for (Ghost ghost : ghostList)
            ghost.evolve(true);

        return finalMessage;

    }


}
