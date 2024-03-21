package pt.isec.pa.tinypac.model.factories;

import pt.isec.pa.tinypac.model.data.Maze;
import pt.isec.pa.tinypac.model.data.MazeData;
import pt.isec.pa.tinypac.model.data.PacWorld;
import pt.isec.pa.tinypac.model.data.elements.Ball;
import pt.isec.pa.tinypac.model.data.elements.Fruit;
import pt.isec.pa.tinypac.model.data.elements.GhostBlinky;
import pt.isec.pa.tinypac.model.data.elements.GhostClyde;
import pt.isec.pa.tinypac.model.data.elements.GhostInky;
import pt.isec.pa.tinypac.model.data.elements.GhostPinky;
import pt.isec.pa.tinypac.model.data.elements.GhostsCave;
import pt.isec.pa.tinypac.model.data.elements.GhostsPortal;
import pt.isec.pa.tinypac.model.data.elements.Pacman;
import pt.isec.pa.tinypac.model.data.elements.SuperBall;
import pt.isec.pa.tinypac.model.data.elements.Wall;
import pt.isec.pa.tinypac.model.data.elements.WrapZone;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

public final class MazeBoardFactory {

    private static final String[] levelFiles = {"./levelFiles/Level01.txt", "./levelFiles/Level02.txt", "./levelFiles/Level03.txt", "./levelFiles/Level04.txt", "./levelFiles/Level05.txt",
            "./levelFiles/Level06.txt", "./levelFiles/Level07.txt", "./levelFiles/Level08.txt", "./levelFiles/Level09.txt", "./levelFiles/Level10.txt",
            "./levelFiles/Level11.txt", "./levelFiles/Level12.txt", "./levelFiles/Level13.txt", "./levelFiles/Level14.txt", "./levelFiles/Level15.txt",
            "./levelFiles/Level16.txt", "./levelFiles/Level17.txt", "./levelFiles/Level18.txt", "./levelFiles/Level19.txt", "./levelFiles/Level20.txt"};

    private static int startLine = 0;
    private static int startColumn = 0;
    private static int endLine = 0;
    private static int endColumn = 0;


    private MazeBoardFactory() {}

    private static boolean checkNeighborhood(Maze maze, int line, int column) {
        if (maze.get(line - 1, column) instanceof GhostsPortal ||
                maze.get(line + 1, column) instanceof GhostsPortal ||
                maze.get(line, column - 1) instanceof GhostsPortal ||
                maze.get(line, column + 1) instanceof GhostsPortal) {
            return true;
        }
        return false;
    }

    public static MazeData getMazeData(int level, PacWorld pacWorld) {

        int height;
        int width;
        List<String> lines = new ArrayList<>();
        MazeData mazeData = null;
        int fruitLine = 0;
        int fruitColumn = 0;
        int pacmanLine = 0;
        int pacmanColumn = 0;
        int checkLevel = level;
        boolean foundit= false;

        while (!foundit) {
            if(checkLevel == 0)
                break;
            if (new File(levelFiles[checkLevel - 1]).exists()){
                foundit = true;
            }
            else {
                checkLevel--;
            }
        }



        try (var scanner = new Scanner(new BufferedReader(new FileReader(levelFiles[checkLevel-1])))){

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }

            height = lines.size();
            width = lines.get(0).length();
            if(height > 31 || width > 29)
                return null;

            Maze maze = new Maze(height, width);

            int posLine = 0;
            boolean firstEntry = true;
            for (String line : lines) {
                for (int i = 0; i < line.length(); ++i) {
                    switch (line.charAt(i)) {
                        case Ball.SYMBOL -> maze.set(posLine, i, new Ball());
                        case SuperBall.SYMBOL -> maze.set(posLine, i, new SuperBall());
                        case Fruit.SYMBOL -> {
                            fruitLine = posLine;
                            fruitColumn = i;
                        }
                        case Wall.SYMBOL -> maze.set(posLine, i, Wall.getInstance());
                        case WrapZone.SYMBOL -> maze.set(posLine, i, new WrapZone());
                        case Pacman.SYMBOL -> {
                            pacmanLine = posLine;
                            pacmanColumn = i;
                            maze.set(posLine, i, new Pacman(pacWorld));
                        }
                        case GhostsPortal.SYMBOL -> maze.set(posLine, i, new GhostsPortal());
                        case GhostsCave.SYMBOL -> {
                            if (firstEntry) {
                                startLine = posLine;
                                startColumn = i;
                                firstEntry = false;
                            }
                            endLine = posLine;
                            endColumn = i;
                            maze.set(posLine, i, new GhostsCave());
                        }
                    }
                }
                posLine++;
            }

            Supplier<Integer> lineSupplier = () -> new Random().nextInt(endLine - startLine + 1) + startLine;
            Supplier<Integer> columnSupplier = () -> new Random().nextInt(endColumn - startColumn + 1) + startColumn;
            int[][] positions = new int[4][2];

            HashSet<String> usedPositions = new HashSet<>();
            for (int i = 0; i < positions.length; i++) {
                int[] position = new int[2];
                do {
                    position[0] = lineSupplier.get();
                    position[1] = columnSupplier.get();
                } while (usedPositions.contains(position[0] + "," + position[1]) || checkNeighborhood(maze, position[0], position[1]));
                usedPositions.add(position[0] + "," + position[1]);
                positions[i] = position;
            }

            maze.set(positions[0][0],positions[0][1],new GhostBlinky(pacWorld));
            maze.set(positions[1][0],positions[1][1],new GhostClyde(pacWorld));
            maze.set(positions[2][0],positions[2][1],new GhostInky(pacWorld));
            maze.set(positions[3][0],positions[3][1],new GhostPinky(pacWorld));


            mazeData = new MazeData(maze, height, width, new PacWorld.Position(fruitLine, fruitColumn), new PacWorld.Position(pacmanLine,pacmanColumn),startLine,endLine,startColumn,endColumn,level);
        } catch (FileNotFoundException e) {
            return null;
        }

        return mazeData;
    }
}
