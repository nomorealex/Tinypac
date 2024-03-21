package pt.isec.pa.tinypac.model.data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;

public record MazeData(Maze maze, int height, int width, PacWorld.Position fruitPosition, PacWorld.Position pacmanPosition,
                       int caveStartLine, int caveEndLine, int caveStartColumn, int caveEndColumn, int level) implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "LEVEL -> "+level+" [Height: "+ height + ", Width: "+width+ "]\n\nMAZE:\n" + Arrays.deepToString(maze.getMaze());
    }
}
