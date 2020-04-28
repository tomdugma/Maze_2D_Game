package algorithms.search;

import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends BreadthFirstSearch {

    public BestFirstSearch() {
        super("BEST");
        this.Q = new PriorityQueue<>((p2, p1) -> Integer.compare(p1.getCost(), p2.getCost()));
//        this.Q = new PriorityQueue<>(Comparator.comparing(AState::getCost));

    }
}