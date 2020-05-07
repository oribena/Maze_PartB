package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

    /*
    private int port;
    private int listeningInterval;
    private IServerStrategy serverStrategy;
    private volatile boolean stop;
    private ThreadPoolExecutor threadPool;
    */

public class Server {
    private int port;//The port
    private int interval;//The port
    private IServerStrategy serverStrategy;//The strategy for handling clients
    private volatile boolean stop;
    private ThreadPoolExecutor threadPoolExecutor;

    public Server(int port, int interval, IServerStrategy serverStrategy) {
        this.port = port;
        this.interval=interval;
        this.serverStrategy = serverStrategy;
        this.stop = false;
        this.threadPoolExecutor=(ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void start() {
        Thread t = new Thread( () -> {  runServer();  });
        t.start();
    }
    private void runServer() {
        try {
            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(interval);
            System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    System.out.println("Client excepted:"+clientSocket.toString());
                    threadPoolExecutor.execute(new Thread(() -> {
                        clientHandle(clientSocket);
                    }) );

                } catch (SocketTimeoutException e) {
                    // LOG.debug("SocketTimeout - No clients pending!");
                }
            }
            threadPoolExecutor.shutdown();
            server.close();
        } catch (IOException e) {
            // LOG.error("IOException", e);
        }
    }

    /**
     * This function receives client socket and handles it
     * @param clientSocket - The client socket
     */
    private void clientHandle(Socket clientSocket) {
        try {
            System.out.println("client exepted");
            System.out.println("Handling client with socket:" +clientSocket.toString());
            InputStream inFromClient = clientSocket.getInputStream();
            OutputStream outToClient = clientSocket.getOutputStream();
            this.serverStrategy.clientHandler(inFromClient, outToClient);

            inFromClient.close();
            outToClient.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stop()
    {
        System.out.println("The server has stopped!");
        this.stop = true;
    }
}
