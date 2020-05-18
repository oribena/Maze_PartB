package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class Server {
    private int port;//The port
    private int listeningInterval;//The port
    private IServerStrategy serverStrategy;//The strategy for handling clients
    private volatile boolean stop;
    private ExecutorService executor;
    int threadPoolSize;

    public Server(int port, int listeningInterval, IServerStrategy serverStrategy) {
        this.port = port;
        this.listeningInterval = listeningInterval;
        this.serverStrategy = serverStrategy;
        this.stop = false;
        this.threadPoolSize = Integer.parseInt(Configurations.getProperty("Server.threadPoolSize"));
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start() {
        Thread t = new Thread( () -> {  runServer();  });
        t.start();
    }
    private void runServer() {
        try {
            /*String temp = Configurations.getProperty("Server.threadPoolSize");
            if (temp != null) {
                 threadPoolSize = Integer.parseInt(Configurations.getProperty("Server.threadPoolSize"));
                 executor = Executors.newFixedThreadPool(threadPoolSize);
            }
            else
               executor = Executors.newFixedThreadPool(5);*/

            ServerSocket server = new ServerSocket(port);
            server.setSoTimeout(listeningInterval);
            //System.out.println(String.format("Server started! (port: %s)", port));
            while (!stop) {
                try {
                    Socket clientSocket = server.accept(); // blocking call
                    //System.out.println("Client excepted:"+clientSocket.toString());
                    executor.execute(() -> clientHandle(clientSocket));

                } catch (SocketTimeoutException e) {
                }
            }
            executor.shutdown();
            /*try { ///// check error

                executor.awaitTermination(1, TimeUnit.HOURS); //wait maximum one hour for all tasks to complete. After one hour, exit.

            } catch (InterruptedException e) {

                System.out.println(String.format("Error await termination for ThreadPool", e));

            }*/
            server.close();
        } catch (IOException e) {
        }
    }

    private void clientHandle(Socket clientSocket) {
        try {
            //System.out.println("client exepted");
            //System.out.println("Handling client with socket:" +clientSocket.toString());
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
        //System.out.println("The server has stopped!");
        this.stop = true;
    }
}
