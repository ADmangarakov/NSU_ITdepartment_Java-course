package ru.nsu.ccfit.Mangarakov.chatClient;

import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.UserListRequest;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.UserMsg;
import ru.nsu.ccfit.Mangarakov.Messages.Message;
import ru.nsu.ccfit.Mangarakov.Messages.ServerMessage.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class MessageManager {
    private static ChatView chatBox;
    private static Client user;
    public static void init(Client newUser) {
        user = newUser;
    }

    public static void processMsg(Message msg) {
        String commName = msg.getCommandName();
        if (commName.equalsIgnoreCase("success"))
            ChatView.updateUList(((UserListResponse)msg).getUserList());
        else if (commName.equalsIgnoreCase("userlogin")) {
            ChatView.addUList(((UserLogin) msg).getName());
            ChatView.addServerMessage(((UserLogin) msg).getName(), "userlogin");
        } else if (commName.equalsIgnoreCase("userlogout")) {
            ChatView.rmvUList(((UserLogout) msg).getName());
            ChatView.addServerMessage(((UserLogout) msg).getName(), "userlogout");
        } else if (commName.equalsIgnoreCase("error"))
            ChatView.writeError(((ErrorAns)msg).getMessage());
        else if (commName.equalsIgnoreCase("message"))
            ChatView.addUserMessage(((ServerMsg)msg).getMessage(), ((ServerMsg)msg).getName());
        else if (commName.equalsIgnoreCase("messagelist"))
            ChatView.addOldMsg(((MessageList)msg).getMessages());
    }

    public static void sendMsg(String text) throws ParserConfigurationException, TransformerException, IOException {
        user.sendMessage(new UserMsg(text, user.getSessionID()));
    }

    public static void reqUserList() throws ParserConfigurationException, TransformerException, IOException {
        user.sendMessage(new UserListRequest(user.getSessionID()));
    }

    public static void procReg(String myName) {
        ChatView.setMyName(myName);
    }
}
