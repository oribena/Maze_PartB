package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.IOException;
import java.io.*;
import java.util.logging.LogManager;

public class ServerStrategyGenerateMaze implements IServerStrategy {

    @Override
    public void clientHandler(InputStream in, OutputStream out) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            toClient.flush();

            int[] RowsCols = (int[]) fromClient.readObject();
            int rows = RowsCols[0];
            int cols = RowsCols[1];

            AMazeGenerator mazeGenerator;
            String maze2 = Configurations.getProperty("MazeGenerator");
            if (maze2.equals("MyMazeGenerator"))
                mazeGenerator = new MyMazeGenerator();
            else
                mazeGenerator = new SimpleMazeGenerator();

            Maze maze = mazeGenerator.generate(rows, cols);
            OutputStream compressed = new MyCompressorOutputStream(toClient);
            compressed.write(maze.toByteArray());
            out.flush();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

