package Model;

import View.BoardDisplay;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface IModel {
    void generateMaze(int width, int height);

    void moveCharacter(KeyCode movement);

    int[][] getMaze();

    int getCharacterPositionRow();

    int getCharacterPositionColumn();

    int getGoalPositionRow();

    int getGoalPositionCol();

    void saveMaze(File file) throws IOException;

    void loadMaze(File file) throws Exception;

    public void solveMaze();

    ArrayList<AState> getMazeSolutionSteps();

    boolean isShowSolution();

    void showProperties();

    String getMazeGenerator();

    String getSearchingAlgorithm();

    String getNumOfThreads();

    boolean isGoalAchived();

    void clearMaze();

    public void closeProgram();
}