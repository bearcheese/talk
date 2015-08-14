package com.bearmaster.talk.gui.listener;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.JOptionPane;

import org.jdesktop.application.Application;

public class ConfirmExitListener implements Application.ExitListener {
    
    @Override
    public boolean canExit(EventObject event) {
        Object source = (event != null) ? event.getSource() : null;
        Component owner = (source instanceof Component) ? (Component) source : null;
        int option = JOptionPane.showConfirmDialog(owner, "Really Exit?", "Confirm", JOptionPane.YES_NO_OPTION);
        return option == JOptionPane.YES_OPTION;
    }

    @Override
    public void willExit(EventObject event) {
        //do nothing
    }

}
