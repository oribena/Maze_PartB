package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.IOException;
import java.io.*;
import java.util.logging.LogManager;

public class ServerStrategyGenerateMaze implements IServerStrategy {


    private LogManager Configurations; //////////////////////

    public void serverStrategy(InputStream in, OutputStream out) {
        try {
            ObjectInputStream from = new ObjectInputStream(in);
            ObjectOutputStream to = new ObjectOutputStream(out);
            to.flush();

            int[] RowsCols = (int[]) from.readObject();
            int rows = RowsCols[0];
            int cols = RowsCols[1];

            AMazeGenerator mazeGenerator;
            if (Configurations.getProperty("MazeGenerator")=="SimpleMazeGenerator") //TODO: check confi
                mazeGenerator = new SimpleMazeGenerator();
            else
                mazeGenerator = new MyMazeGenerator();

            Maze maze = mazeGenerator.generate(rows, cols);

            /// TODO: check all of this
            OutputStream outByteArray = new ByteArrayOutputStream();
            OutputStream compressed = new MyCompressorOutputStream(outByteArray);
            compressed.write(maze.toByteArray());
            ByteArrayOutputStream ByteArrayToSend = (ByteArrayOutputStream) outByteArray;
            byte[] toSend = ByteArrayToSend.toByteArray();
            to.writeObject(toSend);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

