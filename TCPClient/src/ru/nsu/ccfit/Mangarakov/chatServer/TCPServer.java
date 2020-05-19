package ru.nsu.ccfit.Mangarakov.chatServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class TCPServer {
    private final static Logger log = Logger.getLogger(TCPServer.class.getName());

    public static void main(String[] args) throws IOException {
        //Задаем конфигурацию логгера
        try {
            LogManager.getLogManager().readConfiguration(
                    TCPServer.class.getResourceAsStream("logging.properties"));
        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e.toString());
        }
        MessageManager.init(new ServerDB());

        //Создаем новый серверный Socket на порту 2048
        ServerSocket serverSocket = new ServerSocket(2048);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Got connection from:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            log.info("Got connection from:" + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            //Создаем и запускаем поток для обработки запроса
            Thread clientProcessor = new Thread(new ConnectedClient(clientSocket));
            System.out.println("Launch handler...");
            clientProcessor.start();
        }
    }
}
