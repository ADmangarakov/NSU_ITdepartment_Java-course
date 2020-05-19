package ru.nsu.ccfit.Mangarakov.chatClient;

import org.xml.sax.SAXException;
import ru.nsu.ccfit.Mangarakov.Coder.Coder;
import ru.nsu.ccfit.Mangarakov.Coder.XMLUserCoder;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.Disconnect;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.LoginMsg;
import ru.nsu.ccfit.Mangarakov.Messages.Message;
import ru.nsu.ccfit.Mangarakov.Messages.ServerMessage.SuccessAns;
import ru.nsu.ccfit.Mangarakov.User;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client implements Runnable {
    private final Socket socket;
    private final InputStream input;
    private final OutputStream output;
    private final Coder coder;
    private User user;

    public Client(String myName) throws IOException, IllegalArgumentException {
        this.socket = new Socket("127.0.0.1", 2048);
        input = socket.getInputStream();
        output = socket.getOutputStream();
        this.coder = new XMLUserCoder(input, output);
        try {
            coder.send(new LoginMsg(myName, "Chat_User"));
            Message msg = coder.decrypt();
            MessageManager.procReg(myName);
            if (msg.getCommandName().equalsIgnoreCase("success")) {
                SuccessAns ans = (SuccessAns) msg;
                this.user = new User(myName, "Char_User", ans.getSessionID());
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IOException | ParserConfigurationException | TransformerException | ClassNotFoundException | SAXException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                MessageManager.processMsg(coder.decrypt());
            }
        } catch (IOException | ClassNotFoundException | ParserConfigurationException | SAXException | TransformerConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public int getSessionID() {
        return user.getSessionID();
    }

    public void sendMessage(Message msg) throws IOException, TransformerException, ParserConfigurationException {
        coder.send(msg);
    }

    public void stop() throws IOException, TransformerException, ParserConfigurationException {
        coder.send(new Disconnect(user.getSessionID()));
        input.close();
        output.close();
    }


}
