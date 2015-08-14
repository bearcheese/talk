package com.bearmaster.talk.services.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.bearmaster.talk.exception.TalkException;
import com.bearmaster.talk.model.Friend;
import com.bearmaster.talk.services.ChatService;

@Primary
@Service
public class XMPPChatService implements ChatService {

    private static final String ERROR_CONNECTION_IS_NOT_INITIALISED = "Connection is not initialised!";

    private static final Logger LOGGER = LoggerFactory.getLogger(XMPPChatService.class);

    private AbstractXMPPConnection connection;

    @Value("${gtalk.service.name}")
    private String serviceName;

    @Value("${gtalk.host}")
    private String host;

    @Value("${gtalk.port}")
    private int port;

    /*
     * (non-Javadoc)
     * 
     * @see com.bearmaster.talk.services.impl.ChatService#initConnection()
     */
    @Override
    public void initConnection() {
        if (connection == null) {
            XMPPTCPConnectionConfiguration config = prepareConnectionConfiguration();
            connection = new XMPPTCPConnection(config);
        } else {
            LOGGER.warn("Connection is already initialised!");
        }
    }

    protected XMPPTCPConnectionConfiguration prepareConnectionConfiguration() {
        return XMPPTCPConnectionConfiguration.builder().setServiceName(serviceName).setHost(host).setPort(port).build();
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.bearmaster.talk.services.impl.ChatService#login(java.lang.String,
     * java.lang.String)
     */
    @Override
    public void login(String username, String password) throws TalkException {
        if (connection != null) {
            try {
                if (!connection.isConnected()) {
                    LOGGER.debug("Connecting...");
                    connection.connect();
                }
                LOGGER.debug("Attemtping login...");
                connection.login(username, password);
                LOGGER.debug("Login completed.");
                Presence presence = new Presence(Presence.Type.available);
                presence.setMode(Presence.Mode.available);
                connection.sendStanza(presence);
                LOGGER.debug("Presence set to available");
            } catch (SmackException | IOException | XMPPException e) {
                throw new TalkException("Error during login", e);
            }
        } else {
            throw new IllegalStateException(ERROR_CONNECTION_IS_NOT_INITIALISED);
        }
    }

    protected Collection<RosterEntry> getRosterEntries() {
        if (connection != null) {
            Roster roster = Roster.getInstanceFor(connection);

            while (!roster.isLoaded()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignore) {
                    // ignore
                }
            }
            LOGGER.debug("Roster count: {}", roster.getEntryCount());
            return roster.getEntries();
        } else {
            throw new IllegalStateException(ERROR_CONNECTION_IS_NOT_INITIALISED);
        }
    }

    @Override
    public List<Friend> getFriendList() {
        Collection<RosterEntry> rosterEntries = getRosterEntries();
        List<Friend> friendList = new ArrayList<>(rosterEntries.size());
        Roster roster = Roster.getInstanceFor(connection);

        for (RosterEntry entry : rosterEntries) {
            Presence presence = roster.getPresence(entry.getUser());
            Friend friend = new Friend(entry, presence);

            friendList.add(friend);
        }

        return friendList;
    }

    @Override
    public void logoutAndDisconnect() {
        if (connection != null) {
            LOGGER.info("Destroying connection...");
            connection.disconnect();
            connection = null;
        }
    }

    @Override
    public Chat createChat(String jid, ChatMessageListener listener) {
        if (connection != null) {
            ChatManager chatmanager = ChatManager.getInstanceFor(connection);
            return chatmanager.createChat(jid, listener);
        } else {
            throw new IllegalStateException(ERROR_CONNECTION_IS_NOT_INITIALISED);
        }
    }
    
    @Override
    public void addChatListener(ChatManagerListener listener) {
        ChatManager chatmanager = ChatManager.getInstanceFor(connection);
        
        chatmanager.addChatListener(listener);
    }
}
