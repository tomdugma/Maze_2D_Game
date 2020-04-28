package algorithms.search;
import java.util.*;
public class DepthFirstSearch extends ASearchingAlgorithm {

    public DepthFirstSearch() {
        super("DFS");
    }

    private Stack<AState> stack = new Stack<>();
    HashSet<String> visited = new HashSet<>();

    @Override
    public Solution solve(ISearchable domain) {

        AState cur = domain.getStartState();
        stack.push(cur);
        visited.add(cur.toString());
        AState randomState = null;
        while (!stack.isEmpty())
        {

            this.setNumOfNodeEvaluated(this.getNumberOfNodesEvaluated() + 1);
            cur = stack.pop();
            if (cur.equals(domain.getGoalState()))
            {
                return new Solution(cur);
            }

            ArrayList<AState> curNeightbours = domain.getAllPossibleStates(cur);
            if (curNeightbours.isEmpty()) {
                stack.push(cur.father);
                continue;
            }
            while (!curNeightbours.isEmpty())
            {
                Random rand = new Random();
                int num = rand.nextInt(curNeightbours.size());
                randomState = curNeightbours.get(num);
                if (visited.contains(randomState.toString())){
                    curNeightbours.remove(num);
                    randomState = null;
                }
                else
                    break;
            }

            if (randomState == null)
                stack.push(cur.father);
            else {
                 if(cur.getFather() != null)
                cur.setCost(cur.getFather().getCost()+ cur.getCost());
                stack.push(randomState);
                randomState.setFather(cur);
                visited.add(randomState.toString());
            }
        }
        return new Solution(cur);

    }

}