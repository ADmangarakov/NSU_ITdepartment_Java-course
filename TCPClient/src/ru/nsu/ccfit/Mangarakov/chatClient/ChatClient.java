package ru.nsu.ccfit.Mangarakov.chatClient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class ChatClient extends Application {
    private static Thread clientProcessor;
    private static Stage primaryStage;
    private static Parent chatParent;
    private static Client clientSession;
    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        FXMLLoader regfxmlLoader = new FXMLLoader(getClass().getResource("registration.fxml"));
        Parent regParent = regfxmlLoader.load();
        RegController regController = (RegController) regfxmlLoader.getController();
        RegView.init(regController, primaryStage);
        FXMLLoader chatfxmlLoader = new FXMLLoader(getClass().getResource("chat.fxml"));
        chatParent = chatfxmlLoader.load();
        ChatController chatController = (ChatController) chatfxmlLoader.getController();
        ChatView.init(chatController, primaryStage);
        primaryStage.setOnCloseRequest(event -> {
            System.out.println("Stage is closing");
            try {
                clientSession.stop();
                clientProcessor.interrupt();
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (TransformerException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
            try {
                clientProcessor.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            primaryStage.close();
        });

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(regParent));
        primaryStage.show();
    }

    public static void initConnection(String myName)  {
        Client client = null;
        try {
            client = new Client(myName);
        } catch (IllegalArgumentException e){
            RegView.setIllegalNameError();
            return;
        } catch (Exception e) {
            RegView.setServerNotAvailableError();
            return;
        }
        clientSession = client;
        clientProcessor = new Thread(client);
        clientProcessor.start();
        MessageManager.init(client);
        try {
            MessageManager.reqUserList();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        primaryStage.setScene(new Scene(chatParent));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
