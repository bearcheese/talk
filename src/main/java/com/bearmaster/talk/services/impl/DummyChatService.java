package com.bearmaster.talk.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bearmaster.talk.model.Friend;
import com.bearmaster.talk.services.ChatService;

//@Primary
@Service
public class DummyChatService implements ChatService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DummyChatService.class);
    
    private boolean initialised = false;
    
    private boolean loggedIn = false;
    
    private List<Friend> friendList;
    
    public DummyChatService() {
       friendList = new ArrayList<>();
       
       friendList.add(new Friend("testjid1", "test user", new Presence(Type.available)));
       friendList.add(new Friend("testjid2", "logged out user", new Presence(Type.unavailable)));
       friendList.add(new Friend("testjid3", "away user", new Presence(Type.unavailable)));
    }
    
    @Override
    public void initConnection() {
        initialised = true;
        LOGGER.info("Dumy chat service initialised");
    }

    @Override
    public void login(String username, String password) throws XMPPException, SmackException, IOException {
        if (!initialised) {
            throw new IllegalStateException("Dummy chat service connection is not initialised!");
        }
        loggedIn = true;
        LOGGER.info("Dummy chat service logged in");
    }

    @Override
    public List<Friend> getFriendList() {
        if (!loggedIn) {
            throw new IllegalStateException("Dummy chat service is not logged in!");
        }
        return new ArrayList<>(friendList);
    }

    @Override
    public void logoutAndDisconnect() {
        loggedIn = false;
        initialised = false;
    }

    @Override
    public Chat createChat(String jid, ChatMessageListener listener) {
        return null;
    }

    
}
