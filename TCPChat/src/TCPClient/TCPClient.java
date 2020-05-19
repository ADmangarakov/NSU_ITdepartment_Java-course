package TCPClient;

import java.io.*;
import java.net.Socket;

public class TCPClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 2048);
        //ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream());
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = "";
        do {
            System.out.println("Enter a line");
            line = consoleReader.readLine();
            if (line.equalsIgnoreCase("exit")) break;
            writer.write(line + "\n");
            writer.flush();
            line = reader.readLine();
            System.out.println("Got answer:" + line);
        } while (true);
        writer.close();
    }

}
