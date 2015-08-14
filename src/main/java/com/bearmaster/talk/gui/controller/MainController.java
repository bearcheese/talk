package com.bearmaster.talk.gui.controller;

import java.util.EventObject;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;

import org.jdesktop.application.Application.ExitListener;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskEvent;
import org.jivesoftware.smack.sasl.SASLErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.services.ChatService;
import com.bearmaster.talk.util.TaskListenerAdapter;

@Controller
public class MainController implements ExitListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    private SingleFrameApplication application;
    
    @Autowired
    private LoginController loginController;
    
    @Autowired
    private FriendListController friendListController;
    
    @Autowired
    private ChatService chatService;
    
    @PostConstruct
    protected void init() {
        loginController.addLoginTaskListener(new UserLoginTaskListener());
    }

    public JComponent getInitialView() {
        return loginController.getView();
    }
    
    private class UserLoginTaskListener extends TaskListenerAdapter<Void, Void> {
        
        @Override
        public void succeeded(TaskEvent<Void> event) {
            LOGGER.debug("Login task finished, initiating friend list panel");
            application.getMainView().setComponent(friendListController.getView());
            application.show(application.getMainView());
            loginController.destroyView();
        }
        
        @Override
        public void failed(TaskEvent<Throwable> event) {
            if (event.getValue() instanceof SASLErrorException) {
                loginController.displayError("User authentication failed.");
            } else {
                loginController.displayError("General error occured.");
            }
        }
    }

    public void shutdown() {
        chatService.logoutAndDisconnect();
    }

    @Override
    public boolean canExit(EventObject event) {
        return true;
    }

    @Override
    public void willExit(EventObject event) {
        shutdown();
    }

}
