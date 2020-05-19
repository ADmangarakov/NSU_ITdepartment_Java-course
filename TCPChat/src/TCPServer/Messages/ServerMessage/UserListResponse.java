package TCPServer.Messages.ServerMessage;


import TCPServer.Messages.Message;
import TCPServer.User;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashSet;
import java.util.Set;

public class UserListResponse extends Message {
    @NotNull
    private final Set<User> userList;

    public UserListResponse(@NotNull Set<User> userList) {
        super("list", MessageType.SERVER_MESSAGE);
        this.userList = new HashSet<>(userList);
    }

    public Set<User> getUserList() {
        return userList;
    }

    @Override
    public Document toXML() throws ParserConfigurationException {
        Document XMLRepresentation = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Element root = XMLRepresentation.createElement("success");
        XMLRepresentation.appendChild(root);
        Element listusers = XMLRepresentation.createElement("listusers");
        for (User user : userList) {
            Element userEl = XMLRepresentation.createElement("user");
            Element name = XMLRepresentation.createElement("name");
            name.setTextContent(user.getUserName());
            Element type = XMLRepresentation.createElement("type");
            type.setTextContent(user.getChatClientName());
            userEl.appendChild(name);
            userEl.appendChild(type);
            listusers.appendChild(userEl);
        }
        root.appendChild(listusers);
        return XMLRepresentation;
    }
}
