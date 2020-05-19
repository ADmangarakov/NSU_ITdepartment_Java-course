package ru.nsu.ccfit.Mangarakov.Coder;


import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.Mangarakov.Coder.util.XMLReceiver;
import ru.nsu.ccfit.Mangarakov.Coder.util.XMLSender;
import ru.nsu.ccfit.Mangarakov.Messages.Message;
import ru.nsu.ccfit.Mangarakov.Messages.ServerMessage.*;
import ru.nsu.ccfit.Mangarakov.User;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XMLUserCoder implements Coder {

    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public XMLUserCoder(InputStream input, OutputStream output) {
        inputStream = new DataInputStream(input);
        outputStream = new DataOutputStream(output);
    }

    @Override
    public Message decrypt() throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException, TransformerConfigurationException {
        Document doc = XMLReceiver.receive(inputStream);
        //Normalize the XML Structure; It's just too important !!
        doc.getDocumentElement().normalize();

        //Here comes the root node
        Element root = doc.getDocumentElement();
        System.out.println(root.getNodeName());
        Message msg = null;

        Element command;
        if (root.getNodeName().equalsIgnoreCase("event")) {
            command = (Element) doc.getElementsByTagName("event").item(0);
            String msgType = command.getAttribute("name");
            System.out.println("Message type : " + msgType);
            if (msgType.equalsIgnoreCase("userlogin"))
                msg = new UserLogin(
                        command.getElementsByTagName("name").item(0).getTextContent()
                );
            else if (msgType.equalsIgnoreCase("userlogout"))
                msg = new UserLogout(
                        command.getElementsByTagName("name").item(0).getTextContent()
                );
            else if (msgType.equalsIgnoreCase("message"))
                msg = new ServerMsg(
                        command.getElementsByTagName("message").item(0).getTextContent(),
                        command.getElementsByTagName("name").item(0).getTextContent()
                );
        } else if (root.getNodeName().equalsIgnoreCase("error")) {
            command = (Element) doc.getElementsByTagName("error").item(0);
            msg = new ErrorAns(
                    command.getElementsByTagName("message").item(0).getTextContent()
            );
        } else if (root.getNodeName().equalsIgnoreCase("messagelist")) {
//            command = (Element) doc.getElementsByTagName("messagelist").item(0);
            NodeList msgList = doc.getElementsByTagName("message");
            List<Pair<String, String>> msgs = new ArrayList<>();
            for (int temp = 0; temp < msgList.getLength(); temp++) {
                Node node = msgList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    msgs.add(new Pair<>(
                            eElement.getElementsByTagName("name").item(0).getTextContent(),
                            eElement.getElementsByTagName("text").item(0).getTextContent()
                    ));
                }
            }
            msg = new MessageList(msgs);
        } else if (root.getNodeName().equalsIgnoreCase("success")) {
            command = (Element) doc.getElementsByTagName("success").item(0);
            NodeList userList = doc.getElementsByTagName("user");
            if (userList.getLength() > 0) {
                Set<User> users = new HashSet<>();
                for (int temp = 0; temp < userList.getLength(); temp++) {
                    Node node = userList.item(temp);
                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) node;
                        users.add(new User(
                                eElement.getElementsByTagName("name").item(0).getTextContent(),
                                eElement.getElementsByTagName("type").item(0).getTextContent(),
                                -1 //User session id is unknown
                        ));
                    }
                }
                msg = new UserListResponse(users);
            } else if (doc.getElementsByTagName("session") != null)
                msg = new SuccessAns(
                        Integer.parseInt(command.getElementsByTagName("session").item(0).getTextContent()),
                        true
                );
            else
                msg = new SuccessAns(-1, false);
        }
        return msg;
    }

    @Override
    public void send(Message message) throws IOException, ParserConfigurationException, TransformerException {
        Document doc = message.toXML();
        XMLSender.send(doc, outputStream);
    }


}
