package algorithms.search;

import java.util.ArrayList;
import java.util.Collections;

public class Solution {
    private AState goalState;
    private ArrayList<AState> path;

    public Solution(AState goalState, ArrayList<AState> path) {
        this.goalState = goalState;
        this.path = path;
    }

    public Solution(AState goalState) {
        this.goalState = goalState;
        ArrayList<AState> p = new ArrayList<AState>();
        AState curr = goalState;
        while (curr!=null) {
            p.add(curr);
            curr = curr.getCameFrom();
        }
        Collections.reverse(p);
        this.path = p;
    }

    public AState getGoalState() {
        return goalState;
    }

    public ArrayList<AState> getSolutionPath() {
        return path;
    }

    public void setPath(ArrayList<AState> path) {
        this.path = path;
    }
}
