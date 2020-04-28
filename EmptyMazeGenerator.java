package algorithms.mazeGenerators;

import java.lang.reflect.Array;

public class EmptyMazeGenerator extends AMazeGenerator{


    @Override
    public Maze generate(int i, int j) {
        if (i <= 0 || j <= 0) {
            System.out.println("wrong parameters given");
            return null;
        }
        Maze M = new Maze(i,j);
        return M;
    }
}
