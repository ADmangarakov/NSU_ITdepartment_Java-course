package ru.nsu.ccfit.Mangarakov.Coder;


import org.xml.sax.SAXException;
import ru.nsu.ccfit.Mangarakov.Messages.Message;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface Coder {
    Message decrypt() throws IOException, ClassNotFoundException, ParserConfigurationException, SAXException, TransformerConfigurationException;
    void send(Message message) throws IOException, ParserConfigurationException, TransformerException;
}
