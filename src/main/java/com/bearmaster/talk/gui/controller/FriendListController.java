package com.bearmaster.talk.gui.controller;

import java.util.ArrayList;

import javax.swing.JComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.FriendListPanel;
import com.bearmaster.talk.services.ChatService;

@Controller
public class FriendListController extends AbstractController {

    @Autowired
    private ChatService chatService;
    
    @Autowired
    private FriendListPanel friendListPanel;
    
    @Override
    public JComponent getView() {
        friendListPanel.setRosterEntries(new ArrayList<>(chatService.getRosterEntries()));
        return friendListPanel.getComponent();
    }

    @Override
    public void destroyView() {
        friendListPanel = null;

    }

}
