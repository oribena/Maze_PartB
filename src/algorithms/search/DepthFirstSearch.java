package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class DepthFirstSearch extends ASearchingAlgorithm {

    private Stack<AState> stack;

    public DepthFirstSearch() {
        this.queue = new LinkedList<AState>();
        this.visitedNodes = 0;
        this.stack = new Stack<AState>();
    }

    @Override
    public Solution solve(ISearchable Is) {
        if (Is==null || Is.getStartState()==null || Is.getGoalState()==null)
            return null;
        Is.ClearVisited();

        // Mark the start state as visited and add it to the queue
        Is.getStartState().setVisited(true);
        stack.push(Is.getStartState());
        ArrayList<AState> EmptySol = new ArrayList<AState>();
        Solution sol = new Solution(Is.getGoalState(),EmptySol);
        while (!stack.isEmpty())
        {
            // remove a random state from the queue and add it to the solution path
            AState currState = stack.pop();
            //if we arrived to the end, exit
            if (currState.equals(Is.getGoalState())) {
                sol = new Solution(currState);
                break;
            }
            //get all the possible next states
            ArrayList<AState> neighbors = Is.getAllPossibleStates(currState, getName());
            // go over all the neighbors of the curr state
            for (int i = 0; i < neighbors.size() ; i++) {
                //if the neighbor is not visited yet, set visited and set parent
                if (!neighbors.get(i).isVisited()){
                    neighbors.get(i).setVisited(true);
                    visitedNodes++;
                    neighbors.get(i).setCameFrom(currState);
                    //add the neighbor to the queue
                    stack.push(neighbors.get(i));
                }
            }
        }
        return sol;
    }

    @Override
    public String getName() {
        return "Depth First Search";
    }

}