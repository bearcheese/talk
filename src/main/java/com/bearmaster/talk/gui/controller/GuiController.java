package com.bearmaster.talk.gui.controller;

import javax.swing.JComponent;

public interface GuiController {

    JComponent getView();
    
    void destroyView();
    
}
