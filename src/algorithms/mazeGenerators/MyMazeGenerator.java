package algorithms.mazeGenerators;

import java.util.ArrayList;
import java.util.LinkedList;

public class MyMazeGenerator extends AMazeGenerator {

    @Override
    public Maze generate(int rows, int columns) {
        int[][] mat = new int[rows][columns];
        //if the Maze is 3X3 - EmptyMaze
        if (rows < 3 || columns < 3) {
            for (int i=0; i<rows; i++ ){
                for (int j=0; j<columns; j++){
                    mat[i][j] = 0;
                }
            }
        }
        else {
            //put 1 in all (creates mat of walls)
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < columns; j++) {
                    mat[i][j] = 1;
                }
            }
        }
        //create a list of neighbors
        LinkedList<Position[]> neighbors = new LinkedList<>();
        int x = (int) (Math.random() * (rows));
        int y = (int) (Math.random() * (columns));
        Position pos = new Position(x, y);
        neighbors.add(new Position[]{pos, pos});

        while (!neighbors.isEmpty()) {
            // choose random position from the neighbors list
            Position[] n = neighbors.remove((int) (Math.random() * (neighbors.size())));
            Position Pos2 = n[1];
            int x2 = Pos2.getRowIndex();
            int y2 = Pos2.getColumnIndex();
            Position Pos1 = n[0];
            int x1 = Pos1.getRowIndex();
            int y1 = Pos1.getColumnIndex();

            Position tempPos1;
            Position tempPos2;

            //add neighbors of the chosen position
            if (mat[x2][y2] == 1) {
                mat[x1][y1] = mat[x2][y2] = 0;
                if (x2 >= 2 && mat[x2 - 2][y2] == 1) {
                    tempPos1 = new Position(x2 - 1, y2);
                    tempPos2 = new Position(x2 - 2, y2);
                    neighbors.add(new Position[]{tempPos1, tempPos2});
                }
                if (y2 >= 2 && mat[x2][y2 - 2] == 1) {
                    tempPos1 = new Position(x2, y2 - 1);
                    tempPos2 = new Position(x2, y2 - 2);
                    neighbors.add(new Position[]{tempPos1, tempPos2});
                }
                if (x2 < rows - 2 && mat[x2 + 2][y2] == 1) {
                    tempPos1 = new Position(x2 + 1, y2);
                    tempPos2 = new Position(x2 + 2, y2);
                    neighbors.add(new Position[]{tempPos1, tempPos2});
                }
                if (y2 < columns - 2 && mat[x2][y2 + 2] == 1) {
                    tempPos1 = new Position(x2, y2 + 1);
                    tempPos2 = new Position(x2, y2 + 2);
                    neighbors.add(new Position[]{tempPos1, tempPos2});
                }
            }
        }
        Position[] startEnd = creatStartEnd(rows, columns);
        Position start = startEnd[0];
        Position end = startEnd[1];

        ArrayList<Position> StartNeighbors = getAllPossiblePosition(start, mat);
        ArrayList<Position> EndNeighbors = getAllPossiblePosition(end, mat);

        //check valid start and end
        while((StartNeighbors.isEmpty() && mat[start.getRowIndex()][start.getColumnIndex()]!=0)||(EndNeighbors.isEmpty() && mat[end.getRowIndex()][end.getColumnIndex()]!=0))
        {
                startEnd = creatStartEnd(rows, columns);
                start = startEnd[0];
                end = startEnd[1];
                StartNeighbors = getAllPossiblePosition(start, mat);
                EndNeighbors = getAllPossiblePosition(end, mat);

        }

        //set start and end as 0
        mat[start.getRowIndex()][start.getColumnIndex()]=0;
        mat[end.getRowIndex()][end.getColumnIndex()]=0;

        // create maze
        Maze newMaze = new Maze(mat, start, end);
        return newMaze;
    }

    // find 0 Neighbors of start and end
    public ArrayList<Position> getAllPossiblePosition (Position p, int[][] mat){
        int row = p.getRowIndex();
        int col = p.getColumnIndex();
        ArrayList<Position> res = new ArrayList<Position>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                // if the state is valid
                if (i >= 0 && i < mat.length && j >= 0 && j < mat[0].length) {
                    // if this is a neighbor with a pass
                    if (!(i == row && j == col) && mat[i][j] == 0 && (row - i == 0 || col - j == 0)) {
                        Position pos = new Position(i, j);
                        res.add(pos);

                    }
                }
            }
        }
        return res;
    }
}



