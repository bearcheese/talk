package com.bearmaster.talk.gui.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;

import org.jdesktop.application.Application.ExitListener;
import org.jdesktop.application.SingleFrameApplication;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.roster.RosterEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.FriendListPanel;
import com.bearmaster.talk.gui.listener.ConfirmExitListener;
import com.bearmaster.talk.model.User;
import com.bearmaster.talk.services.impl.XMPPChatService;

@Controller
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private SingleFrameApplication application;
    
    @Autowired
    private LoginController loginController;
    
    @Autowired
    private XMPPChatService chatService;
    
    @PostConstruct
    protected void init() {
        loginController.addPropertyChangeListener("loginActionFired", new LoginActionPropertyChangeListener());
    }

    public JComponent getInitialView() {
        return loginController.getView();
    }
    
    private class LoginActionPropertyChangeListener implements PropertyChangeListener {

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            
            try {
                User user = loginController.getUser();
                LOGGER.info("Logging in with user: {}", user);
                chatService.initConnection();
                chatService.login(user.getName(), user.getPassword());
                application.getMainView().setComponent(new FriendListPanel(new ArrayList<RosterEntry>(chatService.getRosterEntries())));
                application.show(application.getMainView());
                loginController.destroyView();
                
                for(RosterEntry entry : chatService.getRosterEntries()) {
                    LOGGER.info("{}", entry);
                }
            } catch (XMPPException | SmackException | IOException e) {
                LOGGER.error("Error during login!", e);
            }
            
        }
        
    }

    public ExitListener getExitListener() {
        return new ConfirmExitListener(this);
    }

    public void shutdown() {
        chatService.logoutAndDisconnect();
    }

}
