package com.bearmaster.talk.gui.component;

import java.awt.Color;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bearmaster.talk.model.User;
import com.jgoodies.forms.builder.FormBuilder;
import com.jgoodies.forms.factories.Paddings;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class LoginPanel extends JPanel {

    private static final long serialVersionUID = 2620793246331084370L;

    private JTextField usernameField;
    
    private JPasswordField passwordField;
    
    private JButton submitButton;
    
    private JCheckBox rememberMeCheckBox;

    private JLabel usernameLabel;

    private JLabel passwordLabel;
    
    private JLabel rememberMeLabel;
    
    private JLabel errorLabel;
    
    public LoginPanel() {
        initComponents();
    }
    
    private void initComponents() {
        
        errorLabel = new JLabel();
        errorLabel.setFont(errorLabel.getFont().deriveFont(Font.BOLD));
        errorLabel.setForeground(Color.RED);
        
        usernameLabel = new JLabel();
        usernameLabel.setName("usernameLabel");
        
        passwordLabel = new JLabel();
        passwordLabel.setName("passwordLabel");
        
        rememberMeLabel = new JLabel();
        rememberMeLabel.setName("rememberMeLabel");
        
        usernameField = new JTextField();
        usernameField.setName("usernameField");
        usernameField.setColumns(15);
        
        passwordField = new JPasswordField();
        passwordField.setName("passwordField");
        passwordField.setColumns(15);
        
        submitButton = new JButton();
        submitButton.setName("loginButton");
        
        rememberMeCheckBox = new JCheckBox();
        rememberMeCheckBox.setName("rememberMeCheckBox");
        rememberMeCheckBox.setEnabled(false);
        
        usernameLabel.setLabelFor(usernameField);
        
        passwordLabel.setLabelFor(passwordField);
        
        rememberMeLabel.setLabelFor(rememberMeCheckBox);
        
        FormBuilder.create()
        .columns("10dlu, pref, left:pref:grow, 10dlu")
        .rows("5dlu, pref, 5dlu, pref, 1dlu, pref, 1dlu, pref, 1dlu, pref, 1dlu, pref, 2dlu, pref, pref:grow")
        .panel(this)
        .name("loginPanel")
        .padding(Paddings.DIALOG)
        .add(errorLabel).xyw(2, 2, 2)
        .add(usernameLabel).xyw(2, 4, 2)
        .add(usernameField).xyw(2, 6, 2)
        .add(passwordLabel).xyw(2, 8, 2)
        .add(passwordField).xyw(2, 10, 2)
        .add(rememberMeCheckBox).xy(2, 12)
        .add(rememberMeLabel).xy(3, 12)
        .add(submitButton).xyw(2, 14, 2)
        .build();
    }
    
    public JComponent getComponent() {
        return this;
    }
    
    public void setSubmitButtonAction(Action action) {
        usernameField.setAction(action);
        passwordField.setAction(action);
        submitButton.setAction(action);
    }

    //TODO security???
    public User getUser() {
        return new User(usernameField.getText(), new String(passwordField.getPassword()));
    }

    public void displayError(String errorMsg) {
        errorLabel.setText(errorMsg);
    }

}
