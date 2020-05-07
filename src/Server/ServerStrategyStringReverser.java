package Server;

import java.io.*;

public class ServerStrategyStringReverser implements IServerStrategy {

    @Override
    public void clientHandler(InputStream in, OutputStream out) throws IOException {
        BufferedReader fromClient = new BufferedReader(new InputStreamReader(in));
        BufferedWriter toClient = new BufferedWriter(new PrintWriter(out));
        String clientCommand;
        try {
            while (fromClient!=null && !(clientCommand = fromClient.readLine()).equals("exit")){
                Thread.sleep(5000);
                toClient.write(new StringBuilder(clientCommand).reverse().toString() + "\n");
                toClient.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
