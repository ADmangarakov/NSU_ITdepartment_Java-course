package TCPServer.Messages.ClientMessage;

import TCPServer.Messages.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Disconnect extends Message {
    private final int sessionID;

    public Disconnect(int sessionID) {
        super("logout", MessageType.USER_MESSAGE);
        this.sessionID = sessionID;
    }

    public int getSessionID() {
        return sessionID;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement("command");
        root.setAttribute("name", super.getCommandName());
        XMLRepresentation.appendChild(root);
        Element message = XMLRepresentation.createElement("session");
        message.setTextContent(Integer.toString(this.sessionID));
        root.appendChild(message);
        return XMLRepresentation;
    }
}
