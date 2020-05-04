package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;

public class SearchableMaze implements ISearchable {

    private Maze m_maze;
    private MazeState[][] stateMat;

    public SearchableMaze(Maze m_maze) {
        this.m_maze = m_maze;
        this.stateMat = new MazeState[m_maze.getNumOfRows()][m_maze.getNumOfColumns()];
        for (int i = 0; i < stateMat.length; i++) {
            for (int j = 0; j < stateMat[0].length; j++) {
                Position currPos = new Position(i, j);
                int currVal = m_maze.getMat()[i][j];
                stateMat[i][j] = new MazeState(currPos, currVal);
            }

        }
    }

    public MazeState[][] getStateMat() {
        return stateMat;
    }

    @Override
    public MazeState getStartState() {
        return new MazeState(m_maze.getStartPosition(), 0);
    }

    @Override
    public MazeState getGoalState() {
        return new MazeState(m_maze.getGoalPosition(), 0);
    }

    @Override
    public ArrayList<AState> getAllPossibleStates(AState s, String algName) {
        int row = ((MazeState) s).getPos().getRowIndex();
        int col = ((MazeState) s).getPos().getColumnIndex();
        ArrayList<AState> res = new ArrayList<AState>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // if the state is valid
                if (i >= 0 && i < m_maze.getNumOfRows() && j >= 0 && j < m_maze.getNumOfColumns()) {
                    // if this is a neighbor with a pass
                    if (!(i == row && j == col) && (stateMat[i][j].getVal() == 0) && (row - i == 0 || col - j == 0)) {
                        res.add(stateMat[i][j]);
                        // set Priority to valid neighbors
                        if (algName == "Best First Search")
                            stateMat[i][j].setPriority(10);
                        else // if bfs or dfs Priority=1
                            stateMat[i][j].setPriority(1);
                    }
                }
            }
        }
        // corner stateMat[row - 1][col - 1]
        if (row-1 >= 0 && row-1 < m_maze.getNumOfRows() && col-1 >= 0 && col-1 < m_maze.getNumOfColumns()) {
            // if this is a neighbor with a pass
            if (stateMat[row - 1][col - 1].getVal() == 0){
                // if one of the neighbors has a pass
                if (res.contains(stateMat[row - 1][col])||res.contains(stateMat[row][col-1])){
                    res.add(stateMat[row - 1][col - 1]);
                    // set Priority to valid neighbors
                    if (algName == "Best First Search")
                        stateMat[row - 1][col - 1].setPriority(15);
                    else // if bfs or dfs Priority=1
                        stateMat[row - 1][col - 1].setPriority(1);
                }

            }
        }
        // corner stateMat[row - 1][col + 1]
        if (row-1 >= 0 && row-1 < m_maze.getNumOfRows() && col+1 >= 0 && col+1 < m_maze.getNumOfColumns()) {
            // if this is a neighbor with a pass
            if (stateMat[row - 1][col + 1].getVal() == 0){
                // if one of the neighbors has a pass
                if (res.contains(stateMat[row][col+1])||res.contains(stateMat[row-1][col])){
                    res.add(stateMat[row - 1][col + 1]);
                    // set Priority to valid neighbors
                    if (algName == "Best First Search")
                        stateMat[row - 1][col + 1].setPriority(15);
                    else // if bfs or dfs Priority=1
                        stateMat[row - 1][col + 1].setPriority(1);
                }

            }
        }
        // corner stateMat[row + 1][col + 1]
        if (row+1 >= 0 && row+1 < m_maze.getNumOfRows() && col+1 >= 0 && col+1 < m_maze.getNumOfColumns()) {
            // if this is a neighbor with a pass
            if (stateMat[row + 1][col + 1].getVal() == 0){
                // if one of the neighbors has a pass
                if (res.contains(stateMat[row+1][col])||res.contains(stateMat[row][col+1])){
                    res.add(stateMat[row + 1][col + 1]);
                    // set Priority to valid neighbors
                    if (algName == "Best First Search")
                        stateMat[row + 1][col + 1].setPriority(15);
                    else // if bfs or dfs Priority=1
                        stateMat[row + 1][col + 1].setPriority(1);
                }

            }
        }
        // corner stateMat[row + 1][col - 1]
        if (row+1 >= 0 && row+1 < m_maze.getNumOfRows() && col-1 >= 0 && col-1 < m_maze.getNumOfColumns()) {
            // if this is a neighbor with a pass
            if (stateMat[row + 1][col - 1].getVal() == 0){
                // if one of the neighbors has a pass
                if (res.contains(stateMat[row+1][col])||res.contains(stateMat[row][col-1])){
                    res.add(stateMat[row + 1][col - 1]);
                    // set Priority to valid neighbors
                    if (algName == "Best First Search")
                        stateMat[row + 1][col - 1].setPriority(15);
                    else // if bfs or dfs Priority=1
                        stateMat[row + 1][col - 1].setPriority(1);
                }

            }
        }
        return res;
    }

    @Override
    public void ClearVisited() {
        for (int i = 0; i < stateMat.length; i++) {
            for (int j = 0; j < stateMat[0].length; j++) {
                this.stateMat[i][j].setVisited(false);
            }
        }
    }

}
