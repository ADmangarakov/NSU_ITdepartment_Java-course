package TCPServer.Coder;

import TCPServer.Coder.Coder;
import TCPServer.Messages.Message;
import TCPServer.TCPServer;

import java.io.*;

public class SerializingCoder implements Coder {
    @Override
    public Message decrypt(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream reader = new ObjectInputStream(inputStream);
        return (Message) reader.readObject();
    }

    @Override
    public void send(Message message, OutputStream outputStream) throws IOException {
        ObjectOutputStream writer = new ObjectOutputStream(outputStream);
        writer.writeObject(message);
    }
}
