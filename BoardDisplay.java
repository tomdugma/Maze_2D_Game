package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.ScrollEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class BoardDisplay extends javafx.scene.canvas.Canvas {

    private int[][] maze;
    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private int goalPositionRow;
    private int goalPositionCol;
    ArrayList<AState> solutionPath;
    private boolean showSolution;


    public BoardDisplay() {
        widthProperty().addListener(e -> redraw());
        heightProperty().addListener(e -> redraw());
    }

    public void setBoard(int[][] maze) {
        this.maze = maze;
        redraw();
    }

    public void setCharacterPosition(int row, int column) {
        characterPositionRow = row;
        characterPositionColumn = column;
        redraw();
    }

    public void setGoalPosition(int row, int col) {
        goalPositionRow = row;
        goalPositionCol = col;
    }

    public void setShowSolution(boolean showSolution, ArrayList<AState> solutionPath) {

        this.showSolution = showSolution;
        if (this.showSolution) {
            this.solutionPath = solutionPath;
        }
    }

    public void redraw() {
        if (maze != null) {
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            double cellHeight = canvasHeight / maze.length;
            double cellWidth = canvasWidth / maze[0].length;

            try {
                Image wallImage = new Image(new FileInputStream(ImageFileNameBlueBrick.get()));
                // Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
                Image characterImage = new Image(new FileInputStream(ImageFileNameLloyd.get()));
                Image goalImage = new Image(new FileInputStream(ImageFileNameGoldenKatana.get()));
                Image solutionPathImage = new Image(new FileInputStream(ImageFileNameSolution.get()));
                Image greenBrickImage = new Image(new FileInputStream(ImageFileNameGreenBrick.get()));
                Image yellowBrickImage = new Image(new FileInputStream(ImageFileNameYellowBrick.get()));
                Image blueBrickImage = new Image(new FileInputStream(ImageFileNameBlueBrick.get()));
                Image redBrickImage = new Image(new FileInputStream(ImageFileNameRedBrick.get()));
                Image orangeBrickImage = new Image(new FileInputStream(ImageFileNameOrangeBrick.get()));


                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());

                //Draw Maze
                for (int i = 0; i < maze.length; i++) {
                    for (int j = 0; j < maze[i].length; j++) {
                        if (maze[i][j] == 1) {
                            int brick = (i + j) % 5;
                            switch (brick) {
                                case 0: {
                                    wallImage = greenBrickImage;
                                    break;
                                }
                                case 1: {
                                    wallImage = yellowBrickImage;
                                    break;
                                }
                                case 2: {
                                    wallImage = blueBrickImage;
                                    break;
                                }
                                case 3: {
                                    wallImage = redBrickImage;
                                    break;
                                }
                                case 4: {
                                    wallImage = orangeBrickImage;
                                    break;
                                }
                            }
                            gc.drawImage(wallImage, j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        }
                    }
                }

                if (showSolution) {

                    for (int i = 0; i < solutionPath.size(); i++) {

                        MazeState currentPosition = (MazeState) solutionPath.get(i);
                        int positionRow = currentPosition.getCurrentPosition().getRowIndex();
                        int positionCol = currentPosition.getCurrentPosition().getColumnIndex();
                        gc.drawImage(solutionPathImage, positionCol * cellWidth, positionRow * cellHeight, cellWidth, cellHeight);
                    }
                }

                gc.drawImage(goalImage, goalPositionCol * cellWidth, goalPositionRow * cellHeight, cellWidth, cellHeight);

                gc.drawImage(characterImage, characterPositionColumn * cellWidth, characterPositionRow * cellHeight, cellWidth, cellHeight);

            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            }


        }
    }


    //region Properties
    private StringProperty ImageFileNameWall = new SimpleStringProperty();
    private StringProperty ImageFileNameGreenBrick = new SimpleStringProperty();
    private StringProperty ImageFileNameYellowBrick = new SimpleStringProperty();
    private StringProperty ImageFileNameRedBrick = new SimpleStringProperty();
    private StringProperty ImageFileNameBlueBrick = new SimpleStringProperty();
    private StringProperty ImageFileNameOrangeBrick = new SimpleStringProperty();
    private StringProperty ImageFileNameCharacter = new SimpleStringProperty();
    private StringProperty ImageFileNameLloyd = new SimpleStringProperty();
    private StringProperty ImageFileNameGoldenKatana = new SimpleStringProperty();
    private StringProperty ImageFileNameGoal = new SimpleStringProperty();
    private StringProperty ImageFileNameSolution = new SimpleStringProperty();
    private StringProperty ImageFileNameMasterWu = new SimpleStringProperty();
    private StringProperty MediaFileNameNinjaGoSong = new SimpleStringProperty();


    public void clearBoard(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
    }

    public String getMediaFileNameNinjaGoSong() {
        return MediaFileNameNinjaGoSong.get();
    }

    public StringProperty mediaFileNameNinjaGoSongProperty() {
        return MediaFileNameNinjaGoSong;
    }

    public void setMediaFileNameNinjaGoSong(String mediaFileNameNinjaGoSong) {
        this.MediaFileNameNinjaGoSong.set(mediaFileNameNinjaGoSong);
    }

    public String getImageFileNameMasterWu() {
        return ImageFileNameMasterWu.get();
    }

    public StringProperty imageFileNameMasterWuProperty() {
        return ImageFileNameMasterWu;
    }

    public void setImageFileNameMasterWu(String imageFileNameMasterWu) {
        this.ImageFileNameMasterWu.set(imageFileNameMasterWu);
    }

    public String getImageFileNameGoldenKatana() {
        return ImageFileNameGoldenKatana.get();
    }

    public StringProperty imageFileNameGoldenKatanaProperty() {
        return ImageFileNameGoldenKatana;
    }

    public void setImageFileNameGoldenKatana(String imageFileNameGoldenKatana) {
        this.ImageFileNameGoldenKatana.set(imageFileNameGoldenKatana);
    }

    public String getImageFileNameLloyd() {
        return ImageFileNameLloyd.get();
    }

    public StringProperty imageFileNameLloydProperty() {
        return ImageFileNameLloyd;
    }

    public void setImageFileNameLloyd(String imageFileNameLloyd) {
        this.ImageFileNameLloyd.set(imageFileNameLloyd);
    }

    public String getImageFileNameGreenBrick() {
        return ImageFileNameGreenBrick.get();
    }

    public StringProperty imageFileNameGreenBrickProperty() {
        return ImageFileNameGreenBrick;
    }

    public void setImageFileNameGreenBrick(String imageFileNameGreenBrick) {
        this.ImageFileNameGreenBrick.set(imageFileNameGreenBrick);
    }

    public String getImageFileNameYellowBrick() {
        return ImageFileNameYellowBrick.get();
    }

    public StringProperty imageFileNameYellowBrickProperty() {
        return ImageFileNameYellowBrick;
    }

    public void setImageFileNameYellowBrick(String imageFileNameYellowBrick) {
        this.ImageFileNameYellowBrick.set(imageFileNameYellowBrick);
    }

    public String getImageFileNameRedBrick() {
        return ImageFileNameRedBrick.get();
    }

    public StringProperty imageFileNameRedBrickProperty() {
        return ImageFileNameRedBrick;
    }

    public void setImageFileNameRedBrick(String imageFileNameRedBrick) {
        this.ImageFileNameRedBrick.set(imageFileNameRedBrick);
    }

    public String getImageFileNameBlueBrick() {
        return ImageFileNameBlueBrick.get();
    }

    public StringProperty imageFileNameBlueBrickProperty() {
        return ImageFileNameBlueBrick;
    }

    public void setImageFileNameBlueBrick(String imageFileNameBlueBrick) {
        this.ImageFileNameBlueBrick.set(imageFileNameBlueBrick);
    }

    public String getImageFileNameOrangeBrick() {
        return ImageFileNameOrangeBrick.get();
    }

    public StringProperty imageFileNameOrangeBrickProperty() {
        return ImageFileNameOrangeBrick;
    }

    public void setImageFileNameOrangeBrick(String imageFileNameOrangeBrick) {
        this.ImageFileNameOrangeBrick.set(imageFileNameOrangeBrick);
    }

    public void setImageFileNameSolution(String imageFileNameSolution) {
        this.ImageFileNameSolution.set(imageFileNameSolution);
    }

    public String getImageFileNameSolution() {
        return ImageFileNameSolution.get();
    }

    public String getImageFileNameWall() {
        return ImageFileNameWall.get();
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.ImageFileNameWall.set(imageFileNameWall);
    }

    public String getImageFileNameCharacter() {
        return ImageFileNameCharacter.get();
    }

    public void setImageFileNameCharacter(String imageFileNameCharacter) {
        this.ImageFileNameCharacter.set(imageFileNameCharacter);
    }

    public String getImageFileNameGoal() {
        return ImageFileNameGoal.get();
    }

    public void setImageFileNameGoal(String imageFileNameGoal) {
        this.ImageFileNameGoal.set(imageFileNameGoal);
    }
    //endregion


}
