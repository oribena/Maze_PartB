package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMazeGenerator;
import algorithms.search.*;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
     AtomicInteger amount;
    ArrayList<Position[]> startEndSize;

    public ServerStrategySolveSearchProblem() {
        amount = new AtomicInteger(0);
        startEndSize = new ArrayList<Position[]>();
    }


    @Override
    public synchronized void clientHandler(InputStream in, OutputStream out) throws IOException {
        try {
            ObjectInputStream from = new ObjectInputStream(in);
            ObjectOutputStream to = new ObjectOutputStream(out);
            to.flush();
            Maze maze = (Maze) from.readObject();
            //check if sol already exists
            Solution sol = solFound(maze);
            if (sol == null) {
                //if not, create new sol
                sol = newSol(maze);
            }
            to.writeObject(sol);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private Solution solFound(Maze maze) throws IOException {
        try {
            String tempDirectoryPath = System.getProperty("java.io.tmpdir");
            //System.out.println(tempDirectoryPath);

            for (int i = 0; i < startEndSize.size(); i++) {
                Position currSize = new Position(maze.getNumOfRows(), maze.getNumOfColumns());
                boolean equalStart = maze.getStartPosition().equals(startEndSize.get(i)[0]);
                boolean equalEnd = maze.getGoalPosition().equals(startEndSize.get(i)[1]);
                boolean equalSize = currSize.equals(startEndSize.get(i)[2]);
                if (equalStart&&equalEnd&&equalSize){
                    FileInputStream fisM =  new FileInputStream(tempDirectoryPath + "mazeNum" + i);
                    ObjectInputStream mazeFromFile = new ObjectInputStream(fisM);
                    if (mazeFromFile.readObject().equals(maze)){
                        FileInputStream fisS = new FileInputStream(tempDirectoryPath + "solutionNum" + i);
                        Object sol = new ObjectInputStream(fisS).readObject();
                        return (Solution)sol;
                        }
                    else
                        return null;
                    }
                }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


    private Solution newSol(Maze maze)  {

        String tempDirectoryPath = System.getProperty("java.io.tmpdir");

        try {
            FileOutputStream mazeFile=new FileOutputStream(tempDirectoryPath+"mazeNum"+amount);
            ObjectOutputStream out=new ObjectOutputStream(mazeFile);
            out.writeObject(maze);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ISearchingAlgorithm solver = new BreadthFirstSearch();
//        String sol = Configurations.getProperty("SearchingAlgorithm");
//        if (sol.equals("BestFirstSearch"))
//            solver = new BestFirstSearch();
//        else if (sol.equals("DepthFirstSearch"))
//            solver = new DepthFirstSearch();
//        else
//            solver = new BreadthFirstSearch();

        SearchableMaze newMaze=new SearchableMaze(maze);
        Solution solution= solver.solve(newMaze);
        try {
            FileOutputStream solveFile=new FileOutputStream(tempDirectoryPath+"solutionNum"+amount);
            ObjectOutputStream out=new ObjectOutputStream(solveFile);
            out.writeObject(solution);
            Position[] newData = new Position[3];
            newData[0] = maze.getStartPosition();
            newData[1] = maze.getGoalPosition();
            newData[2] = new Position(maze.getNumOfRows(), maze.getNumOfColumns());
            startEndSize.add(newData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        amount.incrementAndGet();
        return solution;
    }
}
