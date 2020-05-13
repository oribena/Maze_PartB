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
            if (Configurations.getProperty("MazeGenerator")=="SimpleMazeGenerator")
                mazeGenerator = new SimpleMazeGenerator();
            else
                mazeGenerator = new MyMazeGenerator();

//            AMazeGenerator mazeGenerator;
//            switch (Configurations.getProperty("MazeGenerator")) {
//                case "MyMazeGenerator":
//                    mazeGenerator = new MyMazeGenerator();
//                    break;
//                case "SimpleMazeGenerator":
//                    mazeGenerator = new SimpleMazeGenerator();
//                    break;
//                default:
//                    mazeGenerator = new MyMazeGenerator();
//                    break;
//            }

            Maze maze = mazeGenerator.generate(rows, cols);

            /// TODO: check all of this
           // OutputStream outByteArray = new ByteArrayOutputStream();
            OutputStream compressed = new MyCompressorOutputStream(toClient);
            compressed.write(maze.toByteArray());
//            ByteArrayOutputStream ByteArrayToSend = (ByteArrayOutputStream) outByteArray;
//            byte[] toSend = ByteArrayToSend.toByteArray();
//            toClient.writeObject(toSend);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

