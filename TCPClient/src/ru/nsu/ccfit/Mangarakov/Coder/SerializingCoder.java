package ru.nsu.ccfit.Mangarakov.Coder;



import ru.nsu.ccfit.Mangarakov.Messages.Message;

import java.io.*;

public class SerializingCoder implements Coder {
    private final ObjectInputStream reader;
    private final ObjectOutputStream writer;

    public SerializingCoder(InputStream inputStream, OutputStream outputStream) throws IOException {
        writer = new ObjectOutputStream(outputStream);
        reader = new ObjectInputStream(inputStream);
    }

    @Override
    public Message decrypt() throws IOException, ClassNotFoundException {
        Message msg = (Message) reader.readObject();
        return msg;
    }

    @Override
    public void send(Message message) throws IOException {
        writer.writeObject(message);
        writer.flush();
    }
}
