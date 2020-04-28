package algorithms.search;

import algorithms.mazeGenerators.Maze;

public interface ISearchingAlgorithm {

    public Solution solve(ISearchable domain); // DOMAIN IS MAZE

    public String getName(); // name of the search algo... /BFS/DFS etc

    public int getNumberOfNodesEvaluated();

}


