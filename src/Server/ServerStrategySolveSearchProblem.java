package Server;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;

import java.io.*;
import java.util.ArrayList;

public class ServerStrategySolveSearchProblem implements IServerStrategy {
    int amount;
    ArrayList<Position> size;
    ArrayList<Position> start;
    ArrayList<Position> end;
//
    public ServerStrategySolveSearchProblem() {
        this.amount = 0;
        this.size = new ArrayList<Position>();
        this.start = new ArrayList<Position>();
        this.end = new ArrayList<Position>();
    }


    @Override
    public void serverStrategy(InputStream in, OutputStream out) throws IOException {
        try {
            ObjectInputStream from = new ObjectInputStream(in);
            ObjectOutputStream to = new ObjectOutputStream(out);
            to.flush();
            Maze maze = (Maze) from.readObject();
            Solution sol = isExist(maze);
            if (sol == null) {
                sol = aNewMazeArrive(maze);
            }
            to.writeObject(sol);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Solution aNewMazeArrive(Maze maze) {
        return null;
    }

    private Solution isExist(Maze maze) {
        return null;
    }
}
