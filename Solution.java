package algorithms.search;

import java.util.ArrayList;

public class Solution {

    AState sol;
    ArrayList<AState> path = new ArrayList<>();

    public Solution(AState sol) {
        this.sol = sol;
    }

    public ArrayList<AState> getSolutionPath(){
        path.add(sol);
//        AState tmp = this.sol;
        while (sol.getFather()!= null){
            path.add(0,sol.father);
            sol = sol.getFather();
        }
        return path;
    }
}
