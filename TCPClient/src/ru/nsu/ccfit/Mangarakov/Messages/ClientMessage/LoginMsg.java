package ru.nsu.ccfit.Mangarakov.Messages.ClientMessage;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class LoginMsg extends Message {
    private final String name;
    private final String type;
    public LoginMsg(String name, String type) {
        super("login", Message.MessageType.USER_MESSAGE);
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
        Document XMLRepresentation = docBuilder.newDocument();
//        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement("command");
        root.setAttribute("name", super.getCommandName());
        XMLRepresentation.appendChild(root);
        Element name = XMLRepresentation.createElement("name");
        name.setTextContent(this.name);
        Element type = XMLRepresentation.createElement("type");
        type.setTextContent(this.type);
        root.appendChild(name);
        root.appendChild(type);
        return XMLRepresentation;
    }
}
