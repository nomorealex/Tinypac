package pt.isec.pa.tinypac.ui.gui.factories;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import pt.isec.pa.tinypac.model.ArrowDirection;
import pt.isec.pa.tinypac.model.data.elements.Ball;
import pt.isec.pa.tinypac.model.data.elements.Fruit;
import pt.isec.pa.tinypac.model.data.elements.GhostBlinky;
import pt.isec.pa.tinypac.model.data.elements.GhostClyde;
import pt.isec.pa.tinypac.model.data.elements.GhostInky;
import pt.isec.pa.tinypac.model.data.elements.GhostPinky;
import pt.isec.pa.tinypac.model.data.elements.Pacman;
import pt.isec.pa.tinypac.model.data.elements.SuperBall;
import pt.isec.pa.tinypac.model.data.elements.Wall;
import pt.isec.pa.tinypac.model.data.elements.WrapZone;
import pt.isec.pa.tinypac.model.fsm.TinyPacState;
import pt.isec.pa.tinypac.ui.gui.resources.managers.ImageManager;

/**
 * Class MazeGuiFactory
 * <p>Final Class that represents a factory of board game, with static methods that print the maze</p>
 * @author Nuno Domingues
 *
 */
public final class MazeGuiFactory {
    private MazeGuiFactory() {}

    /**
     * clearScreen function
     * Clear the screen on the given GraphicsContext and Color
     * @param graphicsContext graphicContext
     * @param color color to clear the graphicContext
     * @return void
     */
    private static void clearScreen(GraphicsContext graphicsContext, Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillRect(
                0,
                0,
                graphicsContext.getCanvas().getWidth(),
                graphicsContext.getCanvas().getHeight()
        );
    }

    /**
     * printMaze function
     * print the board game
     * @param pacmanDirection direction of pacman to print the pacman correctly
     * @param tinyPacState according to the state some colors of the board should change
     * @param pacWorld it's the array with the elements
     * @param graphicsContext graphicContext
     * @return void
     */
    public static void printMaze(ArrowDirection pacmanDirection, TinyPacState tinyPacState, char[][] pacWorld, GraphicsContext graphicsContext){
        if(graphicsContext == null) return;

        int xPosition = 0;
        int yPosition = 0;


        switch (tinyPacState){
            case PREPARE_STATE -> {
                clearScreen(graphicsContext, Color.GRAY);
            }
            case WAITINGTIME_STATE, LOCOMOTE_STATE, COUNTERATTACK_STATE -> {
                graphicsContext.clearRect(0,0,graphicsContext.getCanvas().getWidth(),graphicsContext.getCanvas().getHeight());
            }
            default -> {}
        }



        for (int i = 0; i < pacWorld.length; ++i){
            for(int j = 0; j < pacWorld[0].length; ++j) {
                drawElement(tinyPacState,pacmanDirection,graphicsContext, pacWorld[i][j], xPosition, yPosition);
                xPosition += 20;
            }
            xPosition = 0;
            yPosition += 20;
        }
    }

    /**
     * drawElement function
     * Draw a specify element in the array
     * @param tinyPacState according to the state the ghosts appearance should change
     * @param pacmanDirection according to the pacmanDirection the pacman appearance should change
     * @param graphicsContext graphicContext
     * @param element the element
     * @param xPosition xPosition to draw the element
     * @param yPosition yPostion to draw the element
     * @return void
     */
    private static void drawElement(TinyPacState tinyPacState,ArrowDirection pacmanDirection,GraphicsContext graphicsContext,char element, int xPosition, int yPosition){

        switch (element){
            case Wall.SYMBOL -> {
                graphicsContext.setFill(Color.DARKBLUE);
                graphicsContext.setStroke(Color.BLUE);
                graphicsContext.setLineWidth(2);
                graphicsContext.fillRect(xPosition,yPosition,20,20);
                graphicsContext.strokeRect(xPosition,yPosition,20,20);
            }
            case Ball.SYMBOL -> {
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillOval(xPosition+7,yPosition+7,5,5);
            }
            case SuperBall.SYMBOL -> {
                graphicsContext.setFill(Color.ORANGE);
                graphicsContext.fillOval(xPosition+5,yPosition+5,10,10);
            }
            case Fruit.SYMBOL -> {}
            case WrapZone.SYMBOL -> {
                graphicsContext.setFill(Color.GREEN);
                graphicsContext.setStroke(Color.LIGHTGREEN);
                graphicsContext.setLineWidth(1);
                graphicsContext.fillRect(xPosition,yPosition,20,20);
                graphicsContext.strokeRect(xPosition+2,yPosition+2,15,15);
            }
            case Pacman.SYMBOL -> {
                graphicsContext.setFill(Color.YELLOW);
                if(pacmanDirection == null)
                    pacmanDirection = ArrowDirection.RIGHT;
                switch (pacmanDirection){
                    case UP -> graphicsContext.fillArc(xPosition+3,yPosition+2,15,15,130,270, ArcType.ROUND);
                    case DOWN -> graphicsContext.fillArc(xPosition+3,yPosition+2,15,15,-45,270, ArcType.ROUND);
                    case RIGHT -> graphicsContext.fillArc(xPosition+3,yPosition+2,15,15,45,270, ArcType.ROUND);
                    case LEFT -> graphicsContext.fillArc(xPosition+3,yPosition+2,15,15,225,270, ArcType.ROUND);
                }

            }
            case GhostBlinky.SYMBOL -> {
                if(tinyPacState == TinyPacState.COUNTERATTACK_STATE){
                    graphicsContext.drawImage(ImageManager.getImage("ghostWeak.gif"),xPosition+3,yPosition+2);
                }else{
                    graphicsContext.drawImage(ImageManager.getImage("Blinky15.gif"),xPosition+3,yPosition+2);
                }

            }
            case GhostClyde.SYMBOL -> {
                if(tinyPacState == TinyPacState.COUNTERATTACK_STATE){
                    graphicsContext.drawImage(ImageManager.getImage("ghostWeak.gif"),xPosition+3,yPosition+2);
                }else{
                    graphicsContext.drawImage(ImageManager.getImage("Clyde15.gif"),xPosition+3,yPosition+2);
                }

            }
            case GhostInky.SYMBOL -> {
                if(tinyPacState == TinyPacState.COUNTERATTACK_STATE){
                    graphicsContext.drawImage(ImageManager.getImage("ghostWeak.gif"),xPosition+3,yPosition+2);
                }else{
                    graphicsContext.drawImage(ImageManager.getImage("Inky15.gif"),xPosition+3,yPosition+2);
                }

            }
            case GhostPinky.SYMBOL -> {
                if(tinyPacState == TinyPacState.COUNTERATTACK_STATE){
                    graphicsContext.drawImage(ImageManager.getImage("ghostWeak.gif"),xPosition+3,yPosition+2);
                }else{
                    graphicsContext.drawImage(ImageManager.getImage("Pinky15.gif"),xPosition+3,yPosition+2);
                }

            }
            default -> {
                graphicsContext.clearRect(xPosition,yPosition,20,20);
            }

        }

    }


}

