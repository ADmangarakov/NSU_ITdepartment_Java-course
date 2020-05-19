package TCPServer.Coder;

import TCPServer.Messages.Message;
import TCPServer.TCPServer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Coder {
    Message decrypt(InputStream inputStream) throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException;
    void send(Message message, OutputStream outputStream) throws IOException, ParserConfigurationException, TransformerException;
}
