package com.bearmaster.talk.gui.controller;

import java.awt.event.ActionEvent;

import javax.annotation.PostConstruct;
import javax.swing.JComponent;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.LoginPanel;
import com.bearmaster.talk.model.User;

@Controller
public class LoginController extends AbstractController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Application application;
    
    @Autowired
    private LoginPanel loginPanel;
    
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
    
    public User getUser() {
        return loginPanel.getUser();
    }
    
    @Action
    public void loginAction(ActionEvent event) {
        LOGGER.debug("Login action called with by {}", event.getSource());
        propertyChangeSupport.firePropertyChange("loginActionFired", false, true);
    }
}
