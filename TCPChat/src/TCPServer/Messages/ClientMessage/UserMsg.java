package TCPServer.Messages.ClientMessage;

import TCPServer.Messages.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class UserMsg extends Message {
    private final String message;
    private final int sessionID;
    public UserMsg(String message, int sessionID) {
        super("message", MessageType.USER_MESSAGE);
        this.message = message;
        this.sessionID = sessionID;
    }

    public String getMessage() {
        return message;
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
        Element message = XMLRepresentation.createElement("message");
        message.setTextContent(this.message);
        root.appendChild(message);
        Element session = XMLRepresentation.createElement("session");
        session.setTextContent(Integer.toString(this.sessionID));
        root.appendChild(session);
        return XMLRepresentation;
    }
}
