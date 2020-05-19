package ru.nsu.ccfit.Mangarakov.chatServer;


import javafx.util.Pair;
import ru.nsu.ccfit.Mangarakov.Messages.ClientMessage.UserMsg;
import ru.nsu.ccfit.Mangarakov.User;

import java.io.IOException;
import java.util.*;

public class ServerDB {
    private final int BUFF_SIZE = 10;
    // Хранящиеся на сервере сообщения
    private final ArrayDeque<UserMsg> textMessages = new ArrayDeque<>(BUFF_SIZE);
    //
    private final Map<User, ConnectedClient> userSessions = new HashMap<>();
    private final Map<User, Thread> userProcessors = new HashMap<>();
    // Последний доступный ID
    private int lastID = 0;

    // Возвращает ID сесси при удачном добавлении пользователя.
    // Иначе возвращает -1
    public int addUser(String userName, ConnectedClient newSession, Thread userProcessor) {
        for (User user : userSessions.keySet()) {
            if (user.getChatClientName().equalsIgnoreCase(userName)) return -1;
        }
        User newUser = new User(userName, userName, lastID);
        userSessions.put(newUser, newSession);
        userProcessors.put(newUser, userProcessor);
        return lastID++;
    }

    public void delUser(int sessionID) {
        for (User user : userSessions.keySet()) {
            if (user.getSessionID() == sessionID) {
                userProcessors.get(user).interrupt();
                try {
                    userSessions.get(user).close();
                } catch (IOException exception) {
                    exception.printStackTrace();
                }

                userProcessors.remove(user, userProcessors.get(user));
                userSessions.remove(user, userSessions.get(user));
            }
        }
    }

    public void addMsg(UserMsg msg) {
        if (textMessages.size() == BUFF_SIZE) {
            textMessages.poll();
        }
        msg.setName(getUserName(msg.getSessionID()));
        textMessages.add(msg);
    }

    public List<Pair<String, String>> getMsgs() {
        List<Pair<String, String>> msgs = new ArrayList<>();
        for (UserMsg msg : textMessages) {
            msgs.add(new Pair<>(msg.getName(), msg.getMessage()));
        }
        return msgs;
    }

    public Set<User> getUserSessions() {
        return userSessions.keySet();
    }

    public String getUserName(int sessionID) {
        for (User user : userSessions.keySet()) {
            if (user.getSessionID() == sessionID) {
                return user.getUserName();
            }
        }
        return null;
    }

    public List<ConnectedClient> getProcessors() {
        return new ArrayList<ConnectedClient>(userSessions.values());
    }
}
