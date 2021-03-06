package ru.nsu.ccfit.Mangarakov.Coder.util;

import org.w3c.dom.Document;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class XMLSender {

    public static void send(Document tosend, OutputStream channel) throws TransformerConfigurationException, IOException {
        XMLOutputStream out = new XMLOutputStream(channel);

        StreamResult sr = new StreamResult(out);
        DOMSource ds = new DOMSource(tosend);
        Transformer tf = TransformerFactory.newInstance().newTransformer();

        try {
            tf.transform(ds, sr);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLSender.class.getName()).log(Level.SEVERE, null, ex);
        }

        out.send();
    }
}