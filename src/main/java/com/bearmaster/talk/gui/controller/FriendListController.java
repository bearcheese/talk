package com.bearmaster.talk.gui.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;

import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManagerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.FriendListPanel;
import com.bearmaster.talk.model.Friend;
import com.bearmaster.talk.services.ChatService;
import com.bearmaster.talk.util.DefaultFriendComparator;

@Controller
public class FriendListController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendListController.class);
        
    @Autowired
    private ChatService chatService;
    
    @Autowired
    private ChatController chatController;
    
    @Autowired
    private FriendListPanel friendListPanel;
    
    private Comparator<Friend> friendComparator = new DefaultFriendComparator();
    
    @Override
    public JComponent getView() {
        List<Friend> filteredFriendList = filterFriends(chatService.getFriendList());
        friendListPanel.setFriendList(filteredFriendList);
        friendListPanel.setCommonActionListener(new FriendListButtonActionListener());
        
        chatService.addChatListener(new ChatManagerListener() {
            
            @Override
            public void chatCreated(Chat chat, boolean createdLocally) {
                if (!createdLocally) {
                    chatController.openNewChatWindowWithFriend(chat);
                }
            }
        });
        
        return friendListPanel.getComponent();
    }

    @Override
    public void destroyView() {
        friendListPanel = null;

    }
    
    private List<Friend> filterFriends(List<Friend> friendList) {
        List<Friend> filteredList = new ArrayList<>(friendList.size());
        
        for (Friend friend : friendList) {
            if (friend.getName() != null) {
                filteredList.add(friend);
            }
        }
        
        Collections.sort(filteredList, friendComparator);
        
        return filteredList;
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
