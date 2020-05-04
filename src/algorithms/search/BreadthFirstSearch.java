package algorithms.search;

import java.util.ArrayList;
import java.util.LinkedList;

public class BreadthFirstSearch extends ASearchingAlgorithm {

    public BreadthFirstSearch() {
        this.queue = new LinkedList<AState>();
        this.visitedNodes = 0;
    }

    @Override
    public Solution solve(ISearchable Is) {
        if (Is == null || Is.getStartState() == null || Is.getGoalState() == null)
            return null;
        Is.ClearVisited();

        // Mark the start state as visited and add it to the queue
        Is.getStartState().setVisited(true);
        sumPriority.put(Is.getStartState(), 0);
        queue.add(Is.getStartState());
        //if NO Solution
        ArrayList<AState> EmptySol = new ArrayList<AState>();
        Solution sol = new Solution(Is.getGoalState(),EmptySol);
        while (queue.size() != 0) {
            // remove state from the queue and add it to the solution path
            AState currState = queue.remove();
            //if we arrived to the end, exit
            if (currState.equals(Is.getGoalState())) {
                sol = new Solution(currState);
                break;
            }
            //get all the possible next states
            ArrayList<AState> neighbors = Is.getAllPossibleStates(currState, getName());
            // go over all the neighbors of the curr state
            for (int i = 0; i < neighbors.size(); i++) {
                //if the neighbor is not visited yet, set visited and set parent
                if (!neighbors.get(i).isVisited()) {
                    neighbors.get(i).setVisited(true);
                    visitedNodes++;
                    neighbors.get(i).setCameFrom(currState);
                    //add the neighbor to the queue
                    queue.add(neighbors.get(i));
                    //check Priority and enter to the HashMap
                    int currSum = (int) (sumPriority.get(neighbors.get(i).getCameFrom()) + neighbors.get(i).getPriority());
                    sumPriority.put(neighbors.get(i), currSum);

                } else { //if already visited check shortest path
                    int newSum = (int) (sumPriority.get(currState) + neighbors.get(i).getPriority());
                    int oldSum = sumPriority.get(neighbors.get(i));
                    // if the new priority is smaller update the CameFrom
                    if (comparePriority(newSum, oldSum)) {
                        neighbors.get(i).setCameFrom(currState);
                        sumPriority.replace(neighbors.get(i), newSum);
                    }

                }
            }
        }
        return sol;
    }

    @Override
    public String getName() {
        return "Breadth First Search";
    }

    public boolean comparePriority(int newP, int oldP) {
        if ((newP - oldP) < 0) // if the new priority is smaller
            return true;
        return false;
    }
}
