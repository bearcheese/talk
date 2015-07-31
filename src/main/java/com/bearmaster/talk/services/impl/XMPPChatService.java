package com.bearmaster.talk.services.impl;

import java.io.IOException;
import java.util.Collection;

import org.jivesoftware.smack.AbstractXMPPConnection;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.bearmaster.talk.services.ChatService;

@Service
public class XMPPChatService implements ChatService {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(XMPPChatService.class);
    
    private AbstractXMPPConnection connection;
    
    @Value("${gtalk.service.name}")
    private String serviceName;
    
    @Value("${gtalk.host}")
    private String host;
    
    @Value("${gtalk.port}")
    private int port;
    
    /* (non-Javadoc)
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
        XMPPTCPConnectionConfiguration config = 
                XMPPTCPConnectionConfiguration.builder()
                .setServiceName(serviceName)
                .setHost(host)
                .setPort(port)
                .build();
        
        return config;
    }
    
    /* (non-Javadoc)
     * @see com.bearmaster.talk.services.impl.ChatService#login(java.lang.String, java.lang.String)
     */
    @Override
    public void login(String username, String password) throws XMPPException, SmackException, IOException {
        if (connection != null) {
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
        } else {
            throw new IllegalStateException("Connection is not initialised!");
        }
    }
    
    /* (non-Javadoc)
     * @see com.bearmaster.talk.services.impl.ChatService#getRosterEntries()
     */
    @Override
    public Collection<RosterEntry> getRosterEntries() {
        if (connection != null) {
            Roster roster = Roster.getInstanceFor(connection);
            
            while (!roster.isLoaded()) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException ignore) {}
            }
            LOGGER.debug("Roster count: {}", roster.getEntryCount());
            return roster.getEntries();
        } else {
            throw new IllegalStateException("Connection is not initialised!");
        }
    }
    
    public void logoutAndDisconnect() {
        if (connection != null) {
            LOGGER.info("Destroying connection...");
            connection.disconnect();
            connection = null;
        }
    }
}
