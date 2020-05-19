package ru.nsu.ccfit.Mangarakov.chatClient;

import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.util.Pair;
import ru.nsu.ccfit.Mangarakov.User;

import java.util.List;
import java.util.Set;

public class ChatView {
    private static ChatController chatController;
    private static ObservableList<String> users;

    public static void init(ChatController newChatController, Stage primaryStage) {
        chatController = newChatController;
    }

    public static void updateUList(Set<User> userList) {
        chatController.updateUList(userList);
    }

    public static void addUList(String name) {
        chatController.addUList(name);
    }

    public static void addServerMessage(String msgText, String name) {
        chatController.addServerMessage(msgText, name);
    }

    public static void addUserMessage(String msgText, String useName) {
        chatController.addUserMessage(msgText, useName);
    }

    public static void rmvUList(String name) {
        chatController.rmvUList(name);
    }

    public static void writeError(String message) {
        chatController.writeError(message);
    }

    public static void addOldMsg(List<Pair<String, String>> messages) {
        chatController.addOldMsg(messages);
    }

    public static void setMyName(String myName) {
        chatController.setMyName(myName);
    }
}
