package com.bearmaster.talk.services;

import java.io.IOException;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;

import com.bearmaster.talk.model.Friend;

public interface ChatService {

    void initConnection();

    void login(String username, String password) throws XMPPException, SmackException, IOException;

    List<Friend> getFriendList();

    void logoutAndDisconnect();

    Chat createChat(String jid, ChatMessageListener listener);

}