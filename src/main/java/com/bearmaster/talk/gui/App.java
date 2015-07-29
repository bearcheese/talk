package com.bearmaster.talk.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.EventObject;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.jdesktop.application.Action;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.View;

import com.bearmaster.talk.gui.component.LoginPanel;
import com.bearmaster.talk.gui.controller.LoginContoller;
import com.bearmaster.talk.gui.listener.ConfirmExitListener;

public class App extends SingleFrameApplication {
    
    private LoginContoller loginController;

    @Override
    protected void startup() {
        loginController = new LoginContoller(this);
        
        addExitListener(new ConfirmExitListener());
        
        View view = getMainView();
        
        view.setComponent(loginController.getView());
        
        System.out.println(getContext().getResourceMap(LoginPanel.class).keySet());
        
        show(view);
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
    
    //@Action
    public void loginAction() {
        System.out.println("Main login action...");
    }

}
