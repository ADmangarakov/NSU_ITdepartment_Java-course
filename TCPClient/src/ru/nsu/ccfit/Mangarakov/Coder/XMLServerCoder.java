package ru.nsu.ccfit.Mangarakov.Coder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.Mangarakov.Coder.util.XMLReceiver;
import ru.nsu.ccfit.Mangarakov.Coder.util.XMLSender;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.Disconnect;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.LoginMsg;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.UserListRequest;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.UserMsg;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;

public class XMLServerCoder implements Coder {
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    public XMLServerCoder(InputStream input, OutputStream output) {
        inputStream = new DataInputStream(input);
        outputStream = new DataOutputStream(output);
    }
    @Override
    public Message decrypt() throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException, TransformerConfigurationException {
        Document doc = XMLReceiver.receive(inputStream);
        //Normalize the XML Structure; It's just too important !!
        doc.getDocumentElement().normalize();

        Message msg = null;

        Element command = (Element) doc.getElementsByTagName("command").item(0);
        String msgType = command.getAttribute("name");
        System.out.println("Message type : " + msgType);
        if (msgType.equalsIgnoreCase("login"))
            msg = new LoginMsg(
                    command.getElementsByTagName("name").item(0).getTextContent(),
                    command.getElementsByTagName("type").item(0).getTextContent()
            );
        else if (msgType.equalsIgnoreCase("list"))
            msg = new UserListRequest(
                    Integer.parseInt(command.getElementsByTagName("session").item(0).getTextContent())
            );
        else if (msgType.equalsIgnoreCase("message"))
            msg = new UserMsg(
                    command.getElementsByTagName("message").item(0).getTextContent(),
                    Integer.parseInt(command.getElementsByTagName("session").item(0).getTextContent())
            );
        else if (msgType.equalsIgnoreCase("logout"))
            msg = new Disconnect(
                    Integer.parseInt(command.getElementsByTagName("session").item(0).getTextContent())
            );
        return msg;
    }

    @Override
    public void send(Message message) throws IOException, ParserConfigurationException, TransformerException {
        Document doc = message.toXML();
        XMLSender.send(doc, outputStream);
    }


}
