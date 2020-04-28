package algorithms.search;

import algorithms.mazeGenerators.Maze;

import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm{

    String name;
    int numOfNodes;

    public ASearchingAlgorithm(String name) {
        this.name = name;
    }

    public void setNumOfNodeEvaluated(int numOfNodes) {
        this.numOfNodes = numOfNodes;
    }

    public int getNumberOfNodesEvaluated() {
        return numOfNodes;
    }


    public ISearchable searchable;

    public ASearchingAlgorithm(ISearchable searchable) {
        this.searchable = searchable;
    }

    public ISearchable getSearchable() {
        return searchable;
    }

    public void setSearchable(ISearchable searchable) {
        this.searchable = searchable;
    }

    @Override
    public String getName() {
        return name;
    }

}
