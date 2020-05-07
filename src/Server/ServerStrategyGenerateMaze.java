package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;
import java.io.IOException;
import java.io.*;
import java.util.logging.LogManager;

public class ServerStrategyGenerateMaze implements IServerStrategy {


    //private LogManager Configurations; //////////////////////

    @Override
    public void clientHandler(InputStream in, OutputStream out) throws IOException {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(in);
            ObjectOutputStream toClient = new ObjectOutputStream(out);
            toClient.flush();

            int[] RowsCols = (int[]) fromClient.readObject();
            int rows = RowsCols[0];
            int cols = RowsCols[1];

            AMazeGenerator mazeGenerator= new MyMazeGenerator();
//            if (Configurations.getProperty("MazeGenerator")=="SimpleMazeGenerator") //TODO: check confi
//                mazeGenerator = new SimpleMazeGenerator();
//            else
//                mazeGenerator = new MyMazeGenerator();

            Maze maze = mazeGenerator.generate(rows, cols);

            /// TODO: check all of this
           // OutputStream outByteArray = new ByteArrayOutputStream();
            OutputStream compressed = new MyCompressorOutputStream(toClient);
            compressed.write(maze.toByteArray());
            System.out.print("wooohoooo\n");
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

