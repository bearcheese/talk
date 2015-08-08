package com.bearmaster.talk.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.FriendListPanel;
import com.bearmaster.talk.model.Friend;
import com.bearmaster.talk.services.ChatService;

@Controller
public class FriendListController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendListController.class);
        
    @Autowired
    private ChatService chatService;
    
    @Autowired
    private ChatController chatController;
    
    @Autowired
    private FriendListPanel friendListPanel;
    
    @Override
    public JComponent getView() {
        friendListPanel.setFriendList(chatService.getFriendList());
        friendListPanel.setCommonActionListener(new FriendListButtonActionListener());
        return friendListPanel.getComponent();
    }

    @Override
    public void destroyView() {
        friendListPanel = null;

    }
    
    private class FriendListButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() instanceof JButton) {
                Friend targetFriend = friendListPanel.getFriendForButton((JButton) e.getSource());
                if (targetFriend != null) {
                    chatController.openNewChatWindowWithFriend(targetFriend);
                } else {
                    LOGGER.warn("Target friend is null for button: {}", e.getSource());
                }
            }
        }
        
    }

}
