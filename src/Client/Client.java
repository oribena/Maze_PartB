package Client;

import java.net.InetAddress;
import java.net.Socket;

public class Client{
    private InetAddress serverIP;
    private int serverPort;
    private IClientStrategy clientStrategy;

    public Client(InetAddress IP, int port, IClientStrategy clientStrategy) {
        this.serverIP = IP;
        this.serverPort = port;
        this.clientStrategy = clientStrategy;
    }

    public void communicateWithServer(){
        try {
            //Thread.sleep(10000);
            Socket serverSocket = new Socket(serverIP, serverPort);
            //System.out.println(String.format("Client is connected to server (IP: %s, Port: %s)",serverIP,serverPort));
            clientStrategy.clientStrategy(serverSocket.getInputStream(),serverSocket.getOutputStream());
            serverSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

