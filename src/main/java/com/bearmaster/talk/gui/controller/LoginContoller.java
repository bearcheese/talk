package com.bearmaster.talk.gui.controller;

import java.awt.event.ActionEvent;

import javax.swing.JComponent;

import org.jdesktop.application.Action;
import org.jdesktop.application.Application;

import com.bearmaster.talk.gui.component.LoginPanel;

public class LoginContoller {

    private Application application;
    
    private LoginPanel loginPanel;
    
    public LoginContoller(Application application) {
        this.application = application;
        initView();
    }
    
    private void initView() {
        loginPanel = new LoginPanel();
        application.getContext().getResourceMap(LoginPanel.class).injectComponents(loginPanel);
        javax.swing.Action loginAction = application.getContext().getActionManager().getActionMap(LoginContoller.class, this).get("loginAction");
        System.out.println(loginAction);
        loginPanel.setSubmitButtonAction(loginAction);
    }
    
    public JComponent getView() {
        return loginPanel;
    }
    
    @Action
    public void loginAction(ActionEvent event) {
        System.out.println("Login action called...");
        System.out.println("Source: " + event.getSource());
        
    }
}
