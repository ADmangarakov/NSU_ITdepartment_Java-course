package TCPServer.Messages;

import TCPServer.TCPServer;
import org.w3c.dom.Document;

import javax.xml.parsers.ParserConfigurationException;
import java.io.Serializable;

public abstract class Message implements Serializable {
    private final String commandName;
    private final MessageType messageType;

    public abstract Document toXML() throws ParserConfigurationException;

    public enum MessageType {
        USER_MESSAGE,
        SERVER_MESSAGE
    }

    protected Message(String commandName, MessageType messageType) {
        this.commandName = commandName;
        this.messageType = messageType;
    }

    public String getCommandName() {
        return commandName;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
