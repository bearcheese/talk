package com.bearmaster.talk.gui.controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.jdesktop.application.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.LoginPanel;
import com.bearmaster.talk.model.User;
import com.bearmaster.talk.services.ChatService;
import com.bearmaster.talk.task.UserLoginTask;

@Controller
public class LoginController extends AbstractController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Application application;
    
    @Autowired
    private LoginPanel loginPanel;
    
    @Autowired
    private ChatService chatService;
    
    private List<TaskListener<Void, Void>> taskListenerHolderList = new ArrayList<>();
    
    private boolean initialised = false;
    
    @PostConstruct
    protected void initView() {
        application.getContext().getResourceMap(LoginPanel.class).injectComponents(loginPanel);
        javax.swing.Action loginAction = application.getContext().getActionManager().getActionMap(LoginController.class, this).get("loginAction");
        loginPanel.setSubmitButtonAction(loginAction);
        initialised = true;
    }
    
    @Override
    public JComponent getView() {
        if (!initialised) {
            initView();
        }   
        return loginPanel;
    }
    
    @Override
    public void destroyView() {
        loginPanel = null;
    }
    
    @Action
    public Task<Void, Void> loginAction(ActionEvent event) {
        LOGGER.debug("Login action called with by {}", event.getSource());
        propertyChangeSupport.firePropertyChange("loginActionFired", false, true);
        
        User user = loginPanel.getUser();
        
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            loginPanel.displayError("Username and password are mandatory!");
            return null;
        }
        
        UserLoginTask loginTask = new UserLoginTask(application, chatService);
        loginTask.setUser(user);
        
        for (TaskListener<Void, Void> listener : taskListenerHolderList) {
            loginTask.addTaskListener(listener);
        }
        
        return loginTask;
    }
    
    public void addLoginTaskListener(TaskListener<Void, Void> taskListener) {
        taskListenerHolderList.add(taskListener);
    }

    public void displayError(String errorMsg) {
        loginPanel.displayError(errorMsg);
    }
}
