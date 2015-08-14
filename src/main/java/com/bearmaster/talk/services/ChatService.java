package com.bearmaster.talk.services;

import java.util.List;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;

import com.bearmaster.talk.exception.TalkException;
import com.bearmaster.talk.model.Friend;

public interface ChatService {

    void initConnection();

    void login(String username, String password) throws TalkException;

    List<Friend> getFriendList();

    void logoutAndDisconnect();

    Chat createChat(String jid, ChatMessageListener listener);

    void addChatListener(ChatManagerListener listener);

}