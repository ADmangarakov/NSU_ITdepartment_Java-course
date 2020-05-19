package TCPServer.Coder;

import TCPServer.Messages.*;
import TCPServer.Messages.ClientMessage.Disconnect;
import TCPServer.Messages.ClientMessage.LoginMsg;
import TCPServer.Messages.ClientMessage.UserListRequest;
import TCPServer.Messages.ClientMessage.UserMsg;
import TCPServer.Messages.ServerMessage.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.ByteBuffer;

public class XMLServerCoder implements Coder {
    @Override
    public Message decrypt(InputStream inputStream) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException {
        int msgLen = ByteBuffer.wrap(inputStream.readNBytes(4)).getInt();
        byte[] rawMsg = new byte[msgLen];
        if (inputStream.read(rawMsg) != msgLen) return null;

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        ByteArrayInputStream XMLStream = new ByteArrayInputStream(rawMsg);
        Document doc = builder.parse(XMLStream);

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
    public void send(Message message, OutputStream outputStream) throws IOException, ParserConfigurationException, TransformerException {
        Document doc = message.toXML();
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        DOMSource source = new DOMSource(doc);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(bos);
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.transform(source, result);
        byte[] outputBytesXML = bos.toByteArray();
        outputStream.write(outputBytesXML.length);
        outputStream.flush();
        outputStream.write(outputBytesXML);
        outputStream.flush();
    }


}
