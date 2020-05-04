package algorithms.mazeGenerators;

public abstract class AMazeGenerator implements IMazeGenerator {

    public long measureAlgorithmTimeMillis(int rows, int columns){
        //measureAlgorithmTimeMillis = with maze - without maze
        long firstTime = System.currentTimeMillis();
        generate(rows, columns);
        long secondTime = System.currentTimeMillis();
        return secondTime-firstTime;
    }

    public abstract Maze generate(int rows, int columns);


    public Position[] creatStartEnd(int rows, int columns) {
        Position startPos;
        Position endPos;

        int randStart = (int) (Math.random() * (4));
//        int randEnd = (int) (Math.random() * (4));
//        // if start and end are both on the same edge
//        while (randStart==randEnd){
//            randEnd = (int) (Math.random() * (4));
//        }

        //create start position
        if (randStart == 0) {
            int row = (int) (Math.random() * (rows));
            startPos = new Position(row, 0);
        } else if (randStart == 1) {
            int col = (int) (Math.random() * (columns));
            startPos = new Position(0, col);
        } else if (randStart == 2) {
            int row = (int) (Math.random() * (rows));
            ;
            startPos = new Position(row, columns - 1);
        } else {
            int col = (int) (Math.random() * (columns));
            startPos = new Position(rows - 1, col);
        }

//        create end position
        int row = (int) (Math.random() * (rows));
        int col = (int) (Math.random() * (columns));

        //if end and start on the same edge/side
        while (row == startPos.getRowIndex() || col == startPos.getColumnIndex()){
            row = (int) (Math.random() * (rows));
            col = (int) (Math.random() * (rows));
        }
        endPos = new Position(row, col);

        //if end is on the edge/side

        //create end position
//        endPos = new Position(startPos.getRowIndex(), startPos.getColumnIndex());
//        while(endPos.getColumnIndex()==startPos.getColumnIndex() || endPos.getRowIndex()==startPos.getRowIndex()) {
//            if (randEnd == 0) {
//                int row = (int) (Math.random() * (rows));
//                //if end and start on the same edge/side;
//                endPos = new Position(row, 0);
//            } else if (randEnd == 1) {
//                int col = (int) (Math.random() * (columns));
//                endPos = new Position(0, col);
//            } else if (randEnd == 2) {
//                int row = (int) (Math.random() * (rows));
//                endPos = new Position(row, columns - 1);
//            } else {
//                int col = (int) (Math.random() * (columns));
//                endPos = new Position(rows - 1, col);
//            }
//        }

        Position[] startEnd = new Position[2];
        startEnd[0] = startPos;
        startEnd[1] = endPos;

        return startEnd;
    }
}
