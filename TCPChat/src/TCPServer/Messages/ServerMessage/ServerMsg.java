package TCPServer.Messages.ServerMessage;

import TCPServer.Messages.ClientMessage.UserMsg;
import TCPServer.Messages.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ServerMsg extends Message {
    private final String message;
    private final String name;

    public ServerMsg(String message, String name) {
        super("message", MessageType.SERVER_MESSAGE);
        this.message = message;
        this.name = name;
    }

    public ServerMsg(UserMsg inputMessage) {
        super("message", MessageType.SERVER_MESSAGE);
        this.message = inputMessage.getMessage();
        this.name = "CHAT_NAME_FROM";
    }

    public String getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement("event");
        root.setAttribute("name", super.getCommandName());
        XMLRepresentation.appendChild(root);
        Element message = XMLRepresentation.createElement("message");
        message.setTextContent(this.message);
        root.appendChild(message);
        Element name = XMLRepresentation.createElement("name");
        name.setTextContent(this.name);
        root.appendChild(name);
        return XMLRepresentation;
    }
}
