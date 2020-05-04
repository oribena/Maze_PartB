package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    public MazeState getStartState();

    public MazeState getGoalState();

    public ArrayList<AState> getAllPossibleStates(AState s, String algName);

    public void ClearVisited();
}
