package com.bearmaster.talk.gui.component;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {

    private static final long serialVersionUID = -6265572609877298275L;
    
    private JTextField usernameField;
    
    private JPasswordField passwordField;
    
    private JButton submitButton;

    private JLabel usernameLabel;

    private JLabel passwordLabel;
    
    public LoginPanel() {
        setName("loginPanel");
        initComponents();
    }
    
    private void initComponents() {
        usernameLabel = new JLabel();
        usernameLabel.setName("usernameLabel");
        
        passwordLabel = new JLabel();
        passwordLabel.setName("passwordLabel");
        
        usernameField = new JTextField();
        usernameField.setName("usernameField");
        
        passwordField = new JPasswordField();
        passwordField.setName("passwordField");
        
        submitButton = new JButton();
        submitButton.setName("loginButton");
        
        
        usernameLabel.setLabelFor(usernameField);
        add(usernameLabel);
        add(usernameField);
        
        passwordLabel.setLabelFor(passwordField);
        add(passwordLabel);
        add(passwordField);
        
        add(submitButton);
    }
    
    public void setSubmitButtonAction(Action action) {
        if (submitButton != null) {
            submitButton.setAction(action);
        }
    }

}
