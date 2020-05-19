package TCPServer.Messages.ServerMessage;

import TCPServer.Messages.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class UserLogin extends Message {
    private final String name;

    public UserLogin(String name) {
        super("userlogin", MessageType.SERVER_MESSAGE);
        this.name = name;
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
        Element name = XMLRepresentation.createElement("name");
        name.setTextContent(this.name);
        root.appendChild(name);
        return XMLRepresentation;
    }
}
