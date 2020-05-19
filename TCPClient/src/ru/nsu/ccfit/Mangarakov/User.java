package ru.nsu.ccfit.Mangarakov;

import java.io.Serializable;

public class User implements Serializable {
    private final String userName;
    private final String chatClientName;
    private final int sessionID;

    public enum IDStatus {
        UNKNOWN_ID
    }

    public User(String userName, String chatClientName, int sessionID) {
        this.userName = userName;
        this.chatClientName = chatClientName;
        this.sessionID = sessionID;
    }

    public String getUserName() {
        return userName;
    }

    public String getChatClientName() {
        return chatClientName;
    }
    public int getSessionID() {
        return sessionID;
    }
}