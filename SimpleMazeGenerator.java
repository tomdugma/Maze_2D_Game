package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {


    @Override
    public Maze generate(int i, int j) {

        if (i <= 0 || j <= 0) {
            System.out.println("wrong parameters given");
            return null;
        }

        Maze myMaze = new Maze(i, j);
//        Position start = new Position(0, 0);
//        Position goal = new Position(i - 1, j - 1);
//        int[][] simpleMaze = myMaze.maze;

        for (int index = 0; index < j; index++)  // CREATING BOTTOM AND UPPER FRAME
        {
            myMaze.setPath(new Position(0, index), 1);
            myMaze.setPath(new Position(i - 1, index), 1);
        }
        for (int index = 0; index < i; index++)  // CREATING RIGHT AND LEFT FRAME
        {
            myMaze.setPath(new Position(index, 0), 1);
            myMaze.setPath(new Position(index, j - 1), 1);
        }

        int row;
        int col;
        for (row = 1; row < i - 1; row++) { //fill the maze randomly with zero or ones
            for (col = 1; col < j - 1; col++) {
                int randomCell = (int) Math.round(Math.random());
                myMaze.maze[row][col] = randomCell;
            }
        }

        myMaze.maze[1][1] = 0;
        myMaze.maze[i - 2][j - 2] = 0;
        row = 1;
        col = 1;
        boolean edgeOfRows = false;
        boolean edgeOfCols = false;
        boolean downOrRight = false;

        for (int stepsCounter = 0; stepsCounter < i + j - 2; ++stepsCounter) {
            int randomStep = (int) (Math.random() * 2.0);
            if (randomStep == 0) {
                downOrRight = edgeOfRows;
            } else {
                downOrRight = !edgeOfCols;
            }

            if (downOrRight) {
                if (col < j - 2)
                    col++;
                if (col == j - 1) {
                    edgeOfCols = true;
                }
            } else {
                if (row < i - 2)
                    row++;
                if (row == i - 1) {
                    edgeOfRows = true;
                }
            }

            myMaze.maze[row][col] = 0;
        }
        myMaze.setStartingPoint(0, 1);
        myMaze.setEndingPoint(i - 1, j - 2);
        return myMaze;

    }
}