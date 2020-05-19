package ru.nsu.ccfit.Mangarakov.chatClient;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;
import ru.nsu.ccfit.Mangarakov.User;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatController {
    @FXML
    ListView<String> userList;
    @FXML
    TextArea textArea;
    @FXML
    TextField messageArea;
    @FXML
    Button sendBtn;
    @FXML
    Label myNameLbl;
    @FXML
    Label errLbl;

    @FXML
    private void click(ActionEvent event) throws IOException, TransformerException, ParserConfigurationException {
        MessageManager.sendMsg(messageArea.getText());
        messageArea.setText("");
    }

    public void updateUList(Set<User> userList) {
        this.userList.getItems().clear();
        for (User user : userList) {
            this.userList.getItems().add(user.getUserName());
        }
    }

    public void addUList(String name) {
        this.userList.getItems().add(name);
    }

    public void addServerMessage(String msgText, String name) {
        this.textArea.appendText("<" + msgText + ":" + name + ">\n");
    }

    public void rmvUList(String name) {
        ObservableList<String> oldUList = new FilteredList<>(this.userList.getItems());
        ArrayList<String> newUList = new ArrayList<>();
        for (String user : oldUList) {
            if (!user.equals(name)){
                newUList.add(user);
            }
        }
        this.userList.getItems().clear();
        this.userList.getItems().addAll(newUList);
    }

    public void writeError(String message) {
        errLbl.setText(message);
    }

    public void addUserMessage(String msgText, String name) {
        this.textArea.appendText( name + ":" + msgText + "\n");
    }

    public void addOldMsg(List<Pair<String, String>> messages) {
        for(Pair<String, String> msg : messages) {
            addUserMessage(msg.getValue(), msg.getKey());
        }
    }

    public void setMyName(String myName) {
        myNameLbl.setText(myName);
    }
}
