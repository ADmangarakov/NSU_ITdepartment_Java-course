package ru.nsu.ccfit.Mangarakov.chatServer;


import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.Disconnect;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.LoginMsg;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.UserMsg;
import ru.nsu.ccfit.Mangarakov.Messages.Message;
import ru.nsu.ccfit.Mangarakov.Messages.ServerMessage.*;

import java.util.logging.Logger;

public class MessageManager {
    private final static Logger log = Logger.getLogger(MessageManager.class.getName());
    public static ServerDB serverDB;

    public static void init(ServerDB serverDB1) {
        serverDB = serverDB1;
    }

    public static synchronized void makeResponse(Message inputMessage, ConnectedClient userSession, Thread userProcessor) {
        if (inputMessage.getCommandName().equalsIgnoreCase("login")) {
            LoginMsg regMsg = (LoginMsg) inputMessage;
            log.info("Got login request with name <"+regMsg.getName()+">");
            int sessionID = serverDB.addUser(regMsg.getName(), userSession, userProcessor);
            if (sessionID == -1) userSession.sendMessage(new ErrorAns("User name is engaged"));
            else {
                userSession.sendMessage(new SuccessAns(sessionID, true));
                userSession.sendMessage(new MessageList(serverDB.getMsgs()));
                broadcast(new UserLogin(regMsg.getName()));
            }
        } else if (inputMessage.getCommandName().equalsIgnoreCase("list")) {
            log.info("Got list request");
            userSession.sendMessage(new UserListResponse(serverDB.getUserSessions()));
        } else if (inputMessage.getCommandName().equalsIgnoreCase("message")) {
            log.info("Got message from " + serverDB.getUserName(((UserMsg) inputMessage).getSessionID()));
            serverDB.addMsg((UserMsg) inputMessage);
            ServerMsg output = new ServerMsg((UserMsg) inputMessage, serverDB.getUserName(((UserMsg) inputMessage).getSessionID()));
            broadcast(output);
        } else if (inputMessage.getCommandName().equalsIgnoreCase("logout")) {
            log.info(serverDB.getUserName(((Disconnect) inputMessage).getSessionID()) + " disconnected");
            int sessionID = ((Disconnect) inputMessage).getSessionID();
            String userName = serverDB.getUserName(sessionID);
            serverDB.delUser(sessionID);
            //userSession.sendMessage(new SuccessAns(sessionID, false));
            broadcast(new UserLogout(userName));
        }
    }

    // Рассылка сообщения по всем клиентам
    private static void broadcast(Message message) {
        for (ConnectedClient client : serverDB.getProcessors()) {
            client.sendMessage(message);
        }
    }

}
