package TCPServer;

import TCPServer.Coder.Coder;
import TCPServer.Messages.Message;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private final Socket socket; // Точка установленного соединения
    private final Coder coder;

    public ConnectedClient(Socket clientSocket, Coder coder) {
        this.socket = clientSocket;
        this.coder = coder;
    }

    @Override
    public void run() {
        try {
            InputStream input = socket.getInputStream();
            Message message;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Waiting message...");
                message = coder.decrypt(input);
                System.out.println("Got message:" + message.getCommandName());
                MessageManager.makeResponse(message, this, Thread.currentThread());
            }
            input.close();
            System.out.println("Handler finished");
        } catch (IOException | ClassNotFoundException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            OutputStream output = socket.getOutputStream();
            coder.send(message, output);
        } catch (IOException | ParserConfigurationException | TransformerException exception) {
            exception.printStackTrace();
        }
    }
}
