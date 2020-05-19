package ru.nsu.ccfit.Mangarakov.Messages.ServerMessage;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class ErrorAns extends Message {
    private final String message;
    public ErrorAns(String message) {
        super("error" ,MessageType.SERVER_MESSAGE);
        this.message = message;
    }
    public String getMessage() {
        return message;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement(super.getCommandName());
        XMLRepresentation.appendChild(root);
        Element message = XMLRepresentation.createElement("message");
        message.setTextContent(this.message);
        root.appendChild(message);
        return XMLRepresentation;
    }
}
