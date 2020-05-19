package TCPServer;

import TCPServer.Messages.*;
import TCPServer.Messages.ClientMessage.Disconnect;
import TCPServer.Messages.ClientMessage.LoginMsg;
import TCPServer.Messages.ClientMessage.UserListRequest;
import TCPServer.Messages.ClientMessage.UserMsg;
import TCPServer.Messages.ServerMessage.*;

public class MessageManager {
    public static ServerDB serverDB;

    public static void init(ServerDB serverDB1) {
        serverDB = serverDB1;
    }

    public static synchronized void makeResponse(Message inputMessage, ConnectedClient userSession, Thread userProcessor) {
        if (inputMessage.getCommandName().equalsIgnoreCase("login")) {
            LoginMsg regMsg = (LoginMsg) inputMessage;
            int sessionID = serverDB.addUser(regMsg.getName(), userSession, userProcessor);
            if (sessionID == -1) userSession.sendMessage(new ErrorAns("User name is engaged"));
            else {
                userSession.sendMessage(new SuccessAns(sessionID, true));
                broadcast(new UserLogin(regMsg.getName()));
            }
        } else if (inputMessage.getCommandName().equalsIgnoreCase("list")) {
            userSession.sendMessage(new UserListResponse(serverDB.getUserSessions()));
        } else if (inputMessage.getCommandName().equalsIgnoreCase("message")) {
            serverDB.addMsg((UserMsg) inputMessage);
            ServerMsg output = new ServerMsg((UserMsg) inputMessage);
            broadcast(output);
        } else if (inputMessage.getCommandName().equalsIgnoreCase("logout")) {
            int sessionID = ((Disconnect) inputMessage).getSessionID();
            String userName = serverDB.getUserName(sessionID);
            serverDB.delUser(sessionID);
            userSession.sendMessage(new SuccessAns(sessionID, false));
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
