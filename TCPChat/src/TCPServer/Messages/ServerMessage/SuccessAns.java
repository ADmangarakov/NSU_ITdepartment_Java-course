package TCPServer.Messages.ServerMessage;

import TCPServer.Messages.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class SuccessAns extends Message {
    private final int sessionID;
    private final boolean regAns;

    public SuccessAns(int sessionID, boolean regAns) {
        super("success", MessageType.SERVER_MESSAGE);
        this.sessionID = sessionID;
        this.regAns = regAns;
    }

    public int getSessionID() {
        return sessionID;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement(super.getCommandName());
        XMLRepresentation.appendChild(root);
        if (regAns) {
            Element session = XMLRepresentation.createElement("session");
            session.setTextContent(Integer.toString(this.sessionID));
            root.appendChild(session);
        }
        return XMLRepresentation;
    }
}
