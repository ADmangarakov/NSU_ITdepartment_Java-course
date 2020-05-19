package ru.nsu.ccfit.Mangarakov.Messages.ServerMessage;

import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class MessageList extends Message {
    private final List<Pair<String, String>> msgs;
    public MessageList(List<Pair<String, String>> msgs) {
        super("messagelist", MessageType.SERVER_MESSAGE);
        this.msgs = msgs;
    }

    public List<Pair<String, String>> getMessages() {
        return msgs;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement(super.getCommandName());
        XMLRepresentation.appendChild(root);
        Element listusers = XMLRepresentation.createElement("messagelist");
        for (Pair<String, String> msg : msgs) {
            Element userEl = XMLRepresentation.createElement("message");
            Element name = XMLRepresentation.createElement("name");
            name.setTextContent(msg.getKey());
            Element type = XMLRepresentation.createElement("text");
            type.setTextContent(msg.getValue());
            userEl.appendChild(name);
            userEl.appendChild(type);
            listusers.appendChild(userEl);
        }
        root.appendChild(listusers);
        return XMLRepresentation;
    }
}
