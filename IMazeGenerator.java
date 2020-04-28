package algorithms.mazeGenerators;

public interface IMazeGenerator {

    Maze generate(int i, int j);

    long measureAlgorithmTimeMillis(int i, int j);
}
