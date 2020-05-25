package test;

import IO.MyDecompressorInputStream;
import algorithms.mazeGenerators.*;

import java.io.FileInputStream;
import java.io.InputStream;

public class RunMazeGenerator {
    public static void main(String[] args) {
        //testMazeGenerator(new EmptyMazeGenerator());
        //testMazeGenerator(new SimpleMazeGenerator());
        testMazeGenerator(new MyMazeGenerator());
    }

    private static void testMazeGenerator(IMazeGenerator mazeGenerator) {
        // prints the time it takes the algorithm to run
        System.out.println(String.format("Maze generation time(ms): %s",
                mazeGenerator.measureAlgorithmTimeMillis(6/*rows*/,6/*columns*/)));
        // generate another maze
        Maze maze = mazeGenerator.generate(5/*rows*/, 5/*columns*/);

        //byte savedMazeBytes[] = new byte[0];
        //savedMazeBytes = new byte[maze.toByteArray().length];
        Maze maze2 = new Maze(maze.toByteArray());
        // prints the maze
        maze.print();
        System.out.println("\n");
        maze2.print();
        // get the maze entrance
        Position startPosition = maze.getStartPosition();
        // print the position
        System.out.println(String.format("maze1 Start Position: %s", maze.getStartPosition())); // format "{row,column}"
        // prints the maze exit position
        System.out.println(String.format("maze1 Goal Position: %s", maze.getGoalPosition()));

        System.out.println(String.format("maze1 rows: %s", maze.getNumOfRows()));
        System.out.println(String.format("maze1 cols: %s", maze.getNumOfColumns()));


        System.out.println(String.format("maze2 Start Position: %s", maze2.getStartPosition())); // format "{row,column}"
        // prints the maze exit position
        System.out.println(String.format("maze2 Goal Position: %s", maze2.getGoalPosition()));
        System.out.println(String.format("maze2 rows: %s", maze2.getNumOfRows()));
        System.out.println(String.format("maze2 cols: %s", maze2.getNumOfColumns()));

    }
}
