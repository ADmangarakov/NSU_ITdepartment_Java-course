package ru.nsu.ccfit.Mangarakov.chatServer;

import org.xml.sax.SAXException;
import ru.nsu.ccfit.Mangarakov.Coder.Coder;
import ru.nsu.ccfit.Mangarakov.Coder.XMLServerCoder;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ConnectedClient implements Runnable {
    private final Socket socket; // Точка установленного соединения
    private final InputStream input;
    private final OutputStream output;
    private final Coder coder;

    public ConnectedClient(Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        output = socket.getOutputStream();
        input = socket.getInputStream();
        this.coder = new XMLServerCoder(input, output);
    }

    @Override
    public void run() {
        try {
            Message message;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Waiting message...");
                message = coder.decrypt();
                System.out.println("Got message:" + message.getCommandName());
                MessageManager.makeResponse(message, this, Thread.currentThread());
            }
            System.out.println("Handler finished");
        } catch (IOException | ClassNotFoundException | ParserConfigurationException | SAXException | TransformerConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Message message) {
        try {
            OutputStream output = socket.getOutputStream();
            coder.send(message);
        } catch (IOException | ParserConfigurationException | TransformerException exception) {
            exception.printStackTrace();
        }
    }

    public void close() throws IOException {
        input.close();
        output.close();
    }
}
