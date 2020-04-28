package Model;

import Client.Client;
import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategySolveSearchProblem;
import Server.ServerStrategyGenerateMaze;
import Server.Configurations;
import algorithms.mazeGenerators.EmptyMazeGenerator;
import algorithms.mazeGenerators.Maze;
import Client.IClientStrategy;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Properties;


public class MyModel extends Observable implements IModel {

    private Server mazeGeneratingServer;
    private Server solveSearchProblemServer;

    private Maze maze = null;

    private int characterPositionRow = 1;
    private int characterPositionColumn = 1;
    private int goalPositionRow;
    private int goalPositionCol;
//    String propertiesDirectoryPath = System.getProperty("java.io.tmpdir");
    private ArrayList<AState> mazeSolutionSteps = new ArrayList<>();
    private boolean showSolution = false;
    private String mazeGenerator;
    private String searchingAlgorithm;
    private String numOfThreads;
    private boolean goalAchived;


    public MyModel() {

        //Raise the servers
        mazeGeneratingServer = new Server(5400, 1000, new
                ServerStrategyGenerateMaze());

        solveSearchProblemServer = new Server(5401, 1000, new
                ServerStrategySolveSearchProblem());

//        File mazePropertiesDir = new File(propertiesDirectoryPath, "MazeProperties");
//        boolean dirDCreated = mazePropertiesDir.mkdir();
//        if ((!dirDCreated) && ((!mazePropertiesDir.exists()) || (!mazePropertiesDir.isDirectory()))) {
//            System.out.println("There was a problem creating the directory");
//        }
    }

    public void startServers() {
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }

    public void stopServers() {

        mazeGeneratingServer.stop();
        solveSearchProblemServer.stop();

    }

    public void closeProgram()
    {
        stopServers();
    }

    public void generateMaze(int width, int height) {

        CommunicateWithServer_MazeGenerating(height, width);
        characterPositionRow = maze.getStartPosition().getRowIndex();
        characterPositionColumn = maze.getStartPosition().getColumnIndex();
        goalPositionRow = maze.getGoalPosition().getRowIndex();
        goalPositionCol = maze.getGoalPosition().getColumnIndex();
        mazeSolutionSteps.clear();

        setChanged();
        notifyObservers();
    }

    public void solveMaze() {

        CommunicateWithServer_SolveSearchProblem();
        showSolution = true;
        setChanged();
        notifyObservers();

    }

    public boolean isShowSolution() {
        return showSolution;
    }

    private void CommunicateWithServer_MazeGenerating(int numOfRows, int numOfCols) {

        try {
            Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{numOfRows, numOfCols};
                        toServer.writeObject(mazeDimensions);
                        toServer.flush();
                        byte[] compressedMaze = (byte[]) ((byte[]) fromServer.readObject());
                        InputStream in = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        int byteArraySize = new EmptyMazeGenerator().generate(numOfRows, numOfCols).toByteArray().length;
                        byte[] decompressedMaze = new byte[byteArraySize];
                        in.read(decompressedMaze);
                        maze = new Maze(decompressedMaze);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    private void CommunicateWithServer_SolveSearchProblem() {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        toServer.writeObject(maze);
                        toServer.flush();
                        Solution mazeSolution = (Solution) fromServer.readObject();
                        mazeSolutionSteps = mazeSolution.getSolutionPath();
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var2) {
            var2.printStackTrace();
        }

    }

    // @Override
    public int[][] getMaze() {
        return maze.getMaze();
    }

    public int getGoalPositionRow() {
        return goalPositionRow;
    }

    public int getGoalPositionCol() {
        return goalPositionCol;
    }

    // @Override
    public void moveCharacter(KeyCode movement) {
        switch (movement) {
            case DIGIT8: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn)) {
                    characterPositionRow--;
                }
                break;
            }
            case NUMPAD8: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn)) {
                    characterPositionRow--;
                }
                break;
            }
            case UP: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn)) {
                    characterPositionRow--;
                }
                break;
            }
            case DIGIT2: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn)) {
                    characterPositionRow++;
                }
                break;
            }
            case NUMPAD2: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn)) {
                    characterPositionRow++;
                }
                break;
            }
            case DOWN: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn)) {
                    characterPositionRow++;
                }
                break;
            }
            case DIGIT6: {
                if (isLegalMove(characterPositionRow, characterPositionColumn + 1)) {
                    characterPositionColumn++;
                }
                break;
            }
            case NUMPAD6: {
                if (isLegalMove(characterPositionRow, characterPositionColumn + 1)) {
                    characterPositionColumn++;
                }
                break;
            }
            case RIGHT: {
                if (isLegalMove(characterPositionRow, characterPositionColumn + 1)) {
                    characterPositionColumn++;
                }
                break;
            }
            case DIGIT4: {
                if (isLegalMove(characterPositionRow, characterPositionColumn - 1)) {
                    characterPositionColumn--;
                }
                break;
            }
            case NUMPAD4: {
                if (isLegalMove(characterPositionRow, characterPositionColumn - 1)) {
                    characterPositionColumn--;
                }
                break;
            }
            case LEFT: {
                if (isLegalMove(characterPositionRow, characterPositionColumn - 1)) {
                    characterPositionColumn--;
                }
                break;
            }
            case DIGIT3: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn + 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn + 1)) ||
                            (isLegalMove(characterPositionRow + 1, characterPositionColumn))) {
                        characterPositionRow++;
                        characterPositionColumn++;
                    }
                }
                break;
            }
            case NUMPAD3: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn + 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn + 1)) ||
                            (isLegalMove(characterPositionRow + 1, characterPositionColumn))) {
                        characterPositionRow++;
                        characterPositionColumn++;
                    }
                }
                break;
            }
            case DIGIT1: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn - 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn - 1)) ||
                            (isLegalMove(characterPositionRow + 1, characterPositionColumn))) {
                        characterPositionRow++;
                        characterPositionColumn--;
                    }
                }
                break;
            }
            case NUMPAD1: {
                if (isLegalMove(characterPositionRow + 1, characterPositionColumn - 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn - 1)) ||
                            (isLegalMove(characterPositionRow + 1, characterPositionColumn))) {
                        characterPositionRow++;
                        characterPositionColumn--;
                    }
                }
                break;
            }
            case DIGIT9: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn + 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn + 1)) ||
                            (isLegalMove(characterPositionRow - 1, characterPositionColumn))) {
                        characterPositionRow--;
                        characterPositionColumn++;
                    }
                }
                break;
            }
            case NUMPAD9: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn + 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn + 1)) ||
                            (isLegalMove(characterPositionRow - 1, characterPositionColumn))) {
                        characterPositionRow--;
                        characterPositionColumn++;
                    }
                }
                break;
            }
            case DIGIT7: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn - 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn - 1)) ||
                            (isLegalMove(characterPositionRow - 1, characterPositionColumn))) {
                        characterPositionRow--;
                        characterPositionColumn--;
                    }
                }
                break;
            }
            case NUMPAD7: {
                if (isLegalMove(characterPositionRow - 1, characterPositionColumn - 1)) {
                    if ((isLegalMove(characterPositionRow, characterPositionColumn - 1)) ||
                            (isLegalMove(characterPositionRow - 1, characterPositionColumn))) {
                        characterPositionRow--;
                        characterPositionColumn--;
                    }
                }
                break;
            }
        }

        if (characterPositionRow == goalPositionRow && characterPositionColumn == goalPositionCol) {
            goalAchived = true;
        }
        setChanged();
        notifyObservers();
    }

    public boolean isLegalMove(int goToRow, int goToCol) {

        int[][] mazeMatrix = maze.getMaze();
        int mazeRows = mazeMatrix.length;
        int mazeCols = mazeMatrix[0].length;
        if (goToRow < 0 || goToRow > mazeRows - 1 || goToCol < 0 || goToCol > mazeCols - 1) {
            return false;
        }
        if (mazeMatrix[goToRow][goToCol] == 1) {
            return false;
        }
        return true;
    }

    public void saveMaze(File file) throws IOException {

        ByteArrayOutputStream b = new ByteArrayOutputStream();

        OutputStream out = new MyCompressorOutputStream(b);

        byte[] toByteArray = maze.toByteArray();
        out.write(toByteArray);
        out.flush();
        out.close();
        b.close();

        ArrayList<Object> saveProperties = new ArrayList<>();
        saveProperties.add(toByteArray.length);
        saveProperties.add(b.toByteArray());
        saveProperties.add(characterPositionRow);
        saveProperties.add(characterPositionColumn);
        saveProperties.add(goalPositionRow);
        saveProperties.add(goalPositionCol);

        ObjectOutputStream propertiesFile = new ObjectOutputStream(new FileOutputStream(file));

        propertiesFile.writeObject(saveProperties);

        propertiesFile.close();
    }

    public void loadMaze(File file) throws Exception {

        showSolution = false;
        Maze loadedMaze = null;

        FileInputStream dataFile = new FileInputStream(file.getPath());

        ObjectInputStream input = new ObjectInputStream(dataFile);
        ArrayList<Object> properties = (ArrayList<Object>) input.readObject();

        Object[] propertiesObjects = properties.toArray();

        byte[] savedMazeBytes = new byte[(int) properties.get(0)];

        ByteArrayInputStream bIn = new ByteArrayInputStream((byte[]) properties.get(1));
        InputStream in = new MyDecompressorInputStream(bIn);

        in.read(savedMazeBytes);
        in.close();

        loadedMaze = new Maze(savedMazeBytes);

        characterPositionRow = (int) propertiesObjects[2];
        characterPositionColumn = (int) propertiesObjects[3];
        goalPositionRow = (int) propertiesObjects[4];
        goalPositionCol = (int) propertiesObjects[5];

        if (loadedMaze != null) {
            maze = loadedMaze;
            setChanged();
            notifyObservers();
        } else {
            throw new Exception();
        }
    }


    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }

    public String getMazeGenerator() {
        return mazeGenerator;
    }

    public String getSearchingAlgorithm() {
        return searchingAlgorithm;
    }

    public String getNumOfThreads() {
        return numOfThreads;
    }

    public ArrayList<AState> getMazeSolutionSteps() {
        return mazeSolutionSteps;
    }

    public boolean isGoalAchived() {
        return goalAchived;
    }

    public void clearMaze() {

        maze = new EmptyMazeGenerator().generate(100, 100);
        goalAchived = false;
        characterPositionColumn = 1;
        characterPositionRow = 1;
        setChanged();
        notifyObservers();
    }

    public void showProperties() {

        Properties properties = new Properties();
        InputStream input = Configurations.class.getClassLoader().getResourceAsStream("config.properties");

        try {
            properties.load(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
        mazeGenerator = properties.getProperty("MazeGenerator");
        if (mazeGenerator == null) {
            mazeGenerator = "";
        }
        searchingAlgorithm = properties.getProperty("searchingAlgorithm");
        if (searchingAlgorithm == null) {
            searchingAlgorithm = "";
        }
        numOfThreads = properties.getProperty("numOfThreads");
        if (numOfThreads == null) {
            numOfThreads = "";
        }

    }
}