package com.bearmaster.talk.gui.controller;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;

import org.jdesktop.application.Application.ExitListener;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.TaskEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.listener.ConfirmExitListener;
import com.bearmaster.talk.services.ChatService;
import com.bearmaster.talk.util.TaskListenerAdapter;

@Controller
public class MainController {

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
        loginController.getLoginTask().addTaskListener(new UserLoginTaskListener());
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
    }

    public ExitListener getExitListener() {
        return new ConfirmExitListener(this);
    }

    public void shutdown() {
        chatService.logoutAndDisconnect();
    }

}
