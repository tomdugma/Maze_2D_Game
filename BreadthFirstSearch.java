package algorithms.search;

import java.util.*;


public class BreadthFirstSearch extends ASearchingAlgorithm {

    Queue<AState> Q;
    HashSet<String> visited = new HashSet<>();

    public BreadthFirstSearch() {
        super("BFS");
        Q = new LinkedList<>();
    }

    public BreadthFirstSearch(String name) {
        super(name);
    }

    @Override
    public Solution solve(ISearchable domain) {

        Q.add(domain.getStartState());
        visited.add(domain.getStartState().toString());

        while (!Q.isEmpty()) {
            AState curState = Q.remove();
//            searchingComparator.setPrice(curState, searchingComparator.getPrice(curState)+1);
            this.setNumOfNodeEvaluated(this.getNumberOfNodesEvaluated() + 1);
            if (curState.equals(domain.getGoalState()))
                return new Solution(curState);

            ArrayList<AState> curNeightbours = domain.getAllPossibleStates(curState);
            for (int i = 0; i < curNeightbours.size(); i++) {
//                if (visited.contains(randomState.position.toString())){
                if(!visited.contains(curNeightbours.get(i).toString()))
                {
//                    int curStatePrice = searchingComparator.getPrice(curState);
//                    int curNeightboursPrice = searchingComparator.getPrice(curNeightbours.get(i));
//                    searchingComparator.setPrice(curNeightbours.get(i),curStatePrice+curNeightboursPrice);
                    curNeightbours.get(i).setCost(curState.getCost()+ curNeightbours.get(i).getCost());
                    Q.add(curNeightbours.get(i));
                    visited.add(curNeightbours.get(i).toString());
                    curNeightbours.get(i).setFather(curState);
                }
            }

        }
        return null;
    }

    @Override
    public String getName() {
        return name;
    }
}
