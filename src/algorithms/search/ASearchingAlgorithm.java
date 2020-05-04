package algorithms.search;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public abstract class ASearchingAlgorithm implements ISearchingAlgorithm {

    protected HashMap<AState, Integer> sumPriority = new HashMap<AState, Integer>();
    protected Queue<AState> queue; //openList
    protected int visitedNodes;

    public ASearchingAlgorithm() {
        this.queue = new LinkedList<AState>();
        this.visitedNodes = 0;
    }

    protected AState popOpenList(){
        visitedNodes++;
        return queue.poll();
    }

    @Override
    public Solution solve(ISearchable Is) {
        return null;
    }

    @Override
    public int getNumberOfNodesEvaluated() {
        return visitedNodes;
    }
}
