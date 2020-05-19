package ru.nsu.ccfit.Mangarakov.Coder.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import java.io.IOException;
import java.io.InputStream;

public class XMLReceiver {

    public static Document receive(InputStream channel) throws ParserConfigurationException, TransformerConfigurationException, IOException, SAXException {

        DocumentBuilderFactory docBuilderFact = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFact.newDocumentBuilder();
        Document request = null;


        XMLInputStream xmlin = new XMLInputStream(channel);

        xmlin.recive();

        request = docBuilder.parse(xmlin);

        return request;
    }
}
