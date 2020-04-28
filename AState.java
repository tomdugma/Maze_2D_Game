package algorithms.search;

import algorithms.mazeGenerators.Position;
import javafx.geometry.Pos;

public abstract class AState {

    Position position;
    AState father;
//    int numOfNodes = 0;
    int cost;

    public AState(){}

    public AState(Position position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object obj) {
        AState tmp = (AState)obj;
        if(this.position.equals(tmp.position))
            return true;
        return false;
    }


    public void setFather(AState f)
    {
        this.father = f;
    }

    public AState getFather() {
        return father;
    }

//    public void setNumOfNodeEvaluated(int numOfNodes) {
//        this.numOfNodes = numOfNodes;
//    }
//
//    public int getNumOfNodeEvaluated() {
//        return numOfNodes;
//    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }


    @Override
    public String toString() {
        return  position.toString() ;

    }
}
