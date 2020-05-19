package ru.nsu.ccfit.Mangarakov.Messages.ClientMessage;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class UserListRequest extends Message {
    private final int sessionID;
    public UserListRequest(int sessionID) {
        super("list", Message.MessageType.USER_MESSAGE);
        this.sessionID = sessionID;
    }

    public int getSessionID(){
        return sessionID;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement("command");
        root.setAttribute("name", super.getCommandName());
        XMLRepresentation.appendChild(root);
        Element session = XMLRepresentation.createElement("session");
        session.setTextContent(Integer.toString(this.sessionID));
        root.appendChild(session);
        return XMLRepresentation;
    }
}
