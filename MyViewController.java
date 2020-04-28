package View;

import ViewModel.MyViewModel;
import algorithms.search.AState;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static com.sun.tools.doclint.Entity.delta;

public class MyViewController implements Observer, IView {

    private Stage primaryStage;
    private Scene primaryScene;
    private MyViewModel viewModel;
    public javafx.scene.layout.BorderPane borderPane;
    public javafx.scene.layout.Pane displayPane;
    public javafx.scene.layout.VBox leftVBox;
    public BoardDisplay boardDisplayer;
    private int[][] board = null;
    public javafx.scene.control.Button createBoardButton;
    public javafx.scene.control.Label numOfRowsLabel;
    public javafx.scene.control.Label numOfColsLabel;
    public javafx.scene.control.Label characterPositionRowLabel;
    public javafx.scene.control.Label characterPositionColLabel;
    public javafx.scene.control.TextField numOfRowsTextField;
    public javafx.scene.control.TextField numOfColsTextField;
    public javafx.scene.control.Label yourPositionRowLabel;
    public javafx.scene.control.Label yourPositionColLabel;
    public javafx.scene.control.Button solveButton;
    private boolean showSolution = false;
    private boolean alreadySolved = false;
    public javafx.scene.control.MenuItem saveButton;
    public javafx.scene.control.MenuItem loadButton;
    public javafx.scene.control.Button hideSolutionButton;
    private ArrayList<AState> solutionPath;
    MediaPlayer player;
    Media ninjaGoSong = new Media(new File("resources/Media/LegoNinjagoMp3.mp3").toURI().toString());
    Media openingMusic = new Media(new File("resources/Media/opening.mp3").toURI().toString());
    Media partyMusic = new Media(new File("resources/Media/NinjagoCityFiesta.mp3").toURI().toString());
    private boolean playingNow = false;
    private boolean goalAchived = false;

    public void setPrimaryStageAndScene(Stage stage, Scene scene) {
        this.primaryStage = stage;
        this.primaryScene = scene;
        primaryStage.setOnCloseRequest(e -> {
            e.consume();
            exitGame();
            viewModel.closeProgram();
        });
        primaryScene.setOnKeyPressed(
                e -> { if (e.getCode().equals(KeyCode.ENTER))
                {e.consume();

                }
                    viewModel.moveCharacter(e.getCode());
                    e.consume();}
        );
    }

    public void setViewModel(MyViewModel viewModel) {
        this.viewModel = viewModel;
        bindProperties(viewModel);
    }

    public void setResizeEvent(Scene scene) {

        boardDisplayer.widthProperty().bind(displayPane.widthProperty());
        boardDisplayer.heightProperty().bind(displayPane.heightProperty());


        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            boardDisplayer.widthProperty().bind(displayPane.widthProperty());

        });

        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            boardDisplayer.heightProperty().bind(displayPane.heightProperty());

        });
    }

    private void bindProperties(MyViewModel viewModel) {
        characterPositionRowLabel.textProperty().bind(viewModel.characterPositionRow);
        characterPositionColLabel.textProperty().bind(viewModel.characterPositionColumn);

    }

    public void KeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER))
        {keyEvent.consume();

        }
        viewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
    }

    @Override
    public void update(Observable o, Object arg) {

        if (o == viewModel) {
            board = viewModel.getBoard();

            boardDisplayer.setGoalPosition(viewModel.getGoalPositionRow(), viewModel.getGoalPositionCol());

            if (viewModel.isShowSolution()) {
                if (!alreadySolved || showSolution) {
                    showSolution = true;
                    solutionPath = viewModel.getSolutionPath();
                    boardDisplayer.setShowSolution(true, solutionPath);
                }
            }
            displayBoard();

            if (viewModel.isAchivedGoal()) {
                goalAchived = true;
                goalAchived();
            }
        }
    }

    private EventHandler<ScrollEvent> onScrollEventHandler = new EventHandler<ScrollEvent>() {

        @Override
        public void handle(ScrollEvent event) {

            if (event.isControlDown()) {
                double movementY = event.getDeltaY();

                double zoomBy = 1.1;
                if (movementY < 0) {
                    zoomBy = 0.9;
                }
                double newScaleX = boardDisplayer.getScaleX() * zoomBy;
                double newScaleY = boardDisplayer.getScaleY() * zoomBy;

                boardDisplayer.setScaleX(newScaleX);
                boardDisplayer.setScaleY(newScaleY);
            }

        }
    };

    public void startGame() {

        displayPane.setOnScroll(onScrollEventHandler);

        VBox startLayout = new VBox(300);

        Label welcomeLable = new Label("Welcome To Ninjago City!");

        Button startButton = new Button("Start New Game");
        startButton.setOnAction(e -> {
            if (goalAchived) {
                viewModel.clearBoard();
            }
            primaryStage.setScene(primaryScene);
            newGame();
        });
        startButton.setPrefWidth(200);
        startButton.setPrefHeight(50);

        startLayout.getChildren().addAll(welcomeLable, startButton);
        startLayout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(startLayout, 1300, 950);
        scene.getStylesheets().add(getClass().getResource("/View/OpeningWindow.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();

        playSong(openingMusic);

    }


    public void newGame() {
        goalAchived = false;
        solveButton.setVisible(false);
        yourPositionRowLabel.setVisible(false);
        characterPositionRowLabel.setVisible(false);
        yourPositionColLabel.setVisible(false);
        characterPositionColLabel.setVisible(false);
        numOfRowsLabel.setVisible(true);
        numOfRowsTextField.setVisible(true);
        numOfColsLabel.setVisible(true);
        numOfColsTextField.setVisible(true);
        createBoardButton.setVisible(true);
        showSolution = false;
        alreadySolved = false;
        hideSolutionButton.setVisible(false);
        boardDisplayer.clearBoard();
        boardDisplayer.setShowSolution(false, null);
    }

    public void createBoard() {
        createBoardButton.setVisible(false);
        try {
            int numOfRows = Integer.parseInt(numOfRowsTextField.getText());
            int numOfCols = Integer.parseInt(numOfColsTextField.getText());
            if (numOfRows < 0 || numOfCols < 0) {
                showAlert("The Values You Entered Are Not Valid");
                return;
            }

            numOfRowsLabel.setVisible(false);
            numOfRowsTextField.setVisible(false);
            numOfColsLabel.setVisible(false);
            numOfColsTextField.setVisible(false);
            solveButton.setVisible(true);
            yourPositionRowLabel.setVisible(true);
            characterPositionRowLabel.setVisible(true);
            yourPositionColLabel.setVisible(true);
            characterPositionColLabel.setVisible(true);
            saveButton.setDisable(false);

            stopSong();
            playSong(ninjaGoSong);

            viewModel.createBoard(numOfCols, numOfRows);

        } catch (NumberFormatException e) {
            showAlert("Your Input is Not Valid");
            e.getCause();
            return;
        }
    }

    public void displayBoard() {
        if (board != null) {
            int characterPositionRow = viewModel.getCharacterPositionRow();
            int characterPositionColumn = viewModel.getCharacterPositionColumn();
            boardDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
            this.characterPositionRow.set(characterPositionRow + "");
            this.characterPositionColumn.set(characterPositionColumn + "");
            boardDisplayer.setBoard(board);
        }
    }

    public void playSong(Media song) {

        player = new MediaPlayer(song);
        player.setAutoPlay(true);
        player.setVolume(0.5);
        player.setOnEndOfMedia(new Runnable() {
            public void run() {
                player.seek(Duration.ZERO);
            }
        });
        player.play();
        playingNow = true;
    }

    public void stopSong() {
        if (playingNow == true) {
            player.stop();
        }
        playingNow = false;
    }

    public void saveGame() {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File compressedBoardFile = fileChooser.showSaveDialog(primaryStage);

            if (compressedBoardFile != null) {
                viewModel.saveBoard(compressedBoardFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Maze Could Not Be Saved");
        }

    }

    public void loadGame() {
        try {
            FileChooser fileChooser = new FileChooser();

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MAZE files (*.maze)", "*.maze");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(primaryStage);

            if (file != null) {
                viewModel.loadBoard(file);
            }

            alreadySolved = false;
            hideSolution();
            stopSong();
            playSong(ninjaGoSong);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Maze Could Not Be Loaded");
        }
    }

    public void showSolution() {
        solveButton.setVisible(false);
        hideSolutionButton.setVisible(true);
        if (alreadySolved) {
            showSolution = true;
            boardDisplayer.setShowSolution(true, solutionPath);
            displayBoard();
        } else {
            viewModel.showSolution();
            alreadySolved = true;
        }
    }


    public void hideSolution() {
        showSolution = false;
        boardDisplayer.setShowSolution(false, null);
        displayBoard();
        solveButton.setVisible(true);
        hideSolutionButton.setVisible(false);
    }

    public StringProperty characterPositionRow = new SimpleStringProperty();

    public StringProperty characterPositionColumn = new SimpleStringProperty();


    private void newWindow(String title, VBox layout, Button... buttons) {

        Stage window = new Stage();
        window.setTitle(title);
        window.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes


        buttons[0].setOnAction(event -> {
            if (goalAchived) {
                stopSong();
                startGame();
            }
            window.close();
        });

//        closeButton.setOnKeyPressed(e -> {
//            if (e.getCode().equals(KeyCode.ENTER)) {
//                if (goalAchived) {
//                    startGame();
//                }
//                window.close();
//            }
//        });

        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, displayPane.widthProperty().get(), displayPane.heightProperty().get());
        if(buttons.length==2){
            scene.getStylesheets().add(getClass().getResource("/View/ExitWindow.css").toExternalForm());
            buttons[1].setOnAction(event -> {
                window.close();
                primaryStage.close();
                viewModel.closeProgram();
            });
        }
        if (goalAchived && buttons.length==1) {
            scene.getStylesheets().add(getClass().getResource("/View/victoryWindow.css").toExternalForm());
            stopSong();
            playSong(partyMusic);
        }
        else{
            scene.getStylesheets().add(getClass().getResource("/View/MyViewStyle.css").toExternalForm());
        }
        window.setScene(scene);
        window.show();

    }

    public void showProperties() {

        setProperties();

        VBox propertiesLayout = new VBox(50);

        Label generatorLabel = new Label("Maze Generator:   " + viewModel.getMazeGenerator());
        Label algorithmLabel = new Label("Search Algorithm:   " + viewModel.getSearchingAlgorithm());
        Label threadsLabel = new Label("Number Of Threads:   " + viewModel.getNumOfTreads());

        Button closeProperties = new Button("OK");

        propertiesLayout.getChildren().addAll(generatorLabel, algorithmLabel, threadsLabel, closeProperties);
        propertiesLayout.setAlignment(Pos.CENTER);


        newWindow("Properties", propertiesLayout, closeProperties);
    }

    private void setProperties() {

        viewModel.showProperties();

    }

    public void about() {

        VBox aboutLayout = new VBox(50);

        Label nameOfGame = new Label("Lego Ninjago Maze");
        Label infoLabel = new Label("This Game Is Brought To You By Danielle Lavi,\n" +
                "As Part Of An Assignment For Advanced Topics In Programming Course At Ben-Gurion University.");


        Button closeAbout = new Button("OK");
        closeAbout.setPrefWidth(100);
        closeAbout.setPrefHeight(30);

        aboutLayout.getChildren().addAll(nameOfGame, infoLabel, closeAbout);
        aboutLayout.setAlignment(Pos.CENTER);


        newWindow("About", aboutLayout, closeAbout);
    }

    public void exitGame(){

        VBox exitLayout = new VBox(300);
        HBox exitButtons = new HBox(20);

        Label exitMassage = new Label("Are You Sure You Want To Exit?");

        Button closeExitButton = new Button("Exit");
        Button cancelExitButton = new Button("Cancel");
        closeExitButton.setPrefWidth(200);
        closeExitButton.setPrefHeight(50);
        cancelExitButton.setPrefWidth(200);
        cancelExitButton.setPrefHeight(50);

        exitButtons.getChildren().addAll(closeExitButton, cancelExitButton);
        exitButtons.setAlignment(Pos.CENTER);
        exitLayout.getChildren().addAll(exitMassage, exitButtons);
        exitLayout.setAlignment(Pos.BOTTOM_CENTER);

        newWindow("Help", exitLayout, cancelExitButton, closeExitButton);
    }


    public void help() {

        VBox helpLayout = new VBox(80);

        Label goalLabel = new Label("Lloyd Garmadon Lost His Sword While Fighting Lord Garmadon.\n" +
                "Now He Needs To Find It Before Lord Garmadon Will Destroy Ninjago City.\n" +
                "Help Lloyd To Find His Sword And Save The City Before It Will Be Too Late\n" +
                "Its All Up To You Now, But No Pressure,\n" +
                "Only Millions Of Plastic Pieces Are Counting On You To Save Them.\n");
        Label rulesLabel = new Label("You Can Move Up, Down, Right, Left And In A Diagonal Direction As Long As There Is No Wall Blocking Your Way.\n");
        Label goodLuckLabel = new Label("Good Luck! ");

        Button closeHelpButton = new Button("OK");
        closeHelpButton.setPrefWidth(100);
        closeHelpButton.setPrefHeight(30);

        helpLayout.getChildren().addAll(goalLabel, rulesLabel, goodLuckLabel, closeHelpButton);
        helpLayout.setAlignment(Pos.CENTER);


        newWindow("Help", helpLayout, closeHelpButton);

    }

    public void goalAchived() {

        VBox endingLayout = new VBox(80);

        Label congratsLabel = new Label("Good Job!");
        Label infoLabel = new Label("    You Helped Lloyd To Get His Sword Back \n" +
                "          In Order To Defeat Lord Garmadon\n" +
                "             And Save Ninjago City\n" +
                "        And Now Millions Of Plastic Pieces \n" +
                "           Can Live To See Another Day!");

        Button closeEndingSceneButton = new Button("Finish");
        closeEndingSceneButton.setPrefWidth(200);
        closeEndingSceneButton.setPrefHeight(50);

        endingLayout.getChildren().addAll(congratsLabel, infoLabel, closeEndingSceneButton);
        endingLayout.setAlignment(Pos.CENTER);

        player.stop();

        newWindow("Congratulations!", endingLayout, closeEndingSceneButton);

    }

    public void showAlert(String massage) {

        Label alertLabel = new Label(massage);
        Button closeButton = new Button("OK");
        closeButton.setPrefWidth(100);
        closeButton.setPrefHeight(30);

        VBox alertLayout = new VBox(50);
        alertLayout.getChildren().addAll(alertLabel, closeButton);
        alertLayout.setAlignment(Pos.CENTER);

        newWindow("Error: Wrong Input", alertLayout, closeButton);

    }
}