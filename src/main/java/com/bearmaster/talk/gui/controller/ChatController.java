package com.bearmaster.talk.gui.controller;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

import org.jdesktop.application.SingleFrameApplication;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bearmaster.talk.gui.component.ChatFrame;
import com.bearmaster.talk.model.Friend;
import com.bearmaster.talk.services.ChatService;

@Controller
public class ChatController extends AbstractController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Autowired
    private SingleFrameApplication application;

    @Autowired
    private ChatService chatService;

    public JFrame openNewChatWindowWithFriend(Friend friend) {
        LOGGER.debug("Opening new chat frame for friend {}", friend);
        final ChatFrame chatFrame = new ChatFrame(application.getMainFrame(), friend);

        final Chat newChat = chatService.createChat(friend.getJid(), new ChatMessageListener() {

            @Override
            public void processMessage(Chat chat, Message message) {
                if (message.getBody() != null) {
                    LOGGER.debug("{} said: {}", chat.getParticipant(), message.getBody());
                    chatFrame.appendFriendMessageToChat(message.getBody());
                }
            }
        });

        chatFrame.addInputFieldKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                if (!event.isShiftDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = chatFrame.appendInputTextToChat();
                    try {
                        newChat.sendMessage(message);
                    } catch (NotConnectedException e) {
                        LOGGER.error("Cannot send message: {}", message, e);
                    }
                }
            }
        });

        return chatFrame;
    }
    
    public JFrame openNewChatWindowWithFriend(final Chat chat) {
        Friend friend = new Friend(null, chat.getParticipant(), null); //TODO not really legal move
        final ChatFrame chatFrame = new ChatFrame(application.getMainFrame(), friend);
        
        chat.addMessageListener(new ChatMessageListener() {
            
            @Override
            public void processMessage(Chat chat, Message message) {
                if (message.getBody() != null) {
                    LOGGER.debug("{} said: {}", chat.getParticipant(), message.getBody());
                    chatFrame.appendFriendMessageToChat(message.getBody());
                }
            }
        });
        
        chatFrame.addInputFieldKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent event) {
                if (!event.isShiftDown() && event.getKeyCode() == KeyEvent.VK_ENTER) {
                    String message = chatFrame.appendInputTextToChat();
                    try {
                        chat.sendMessage(message);
                    } catch (NotConnectedException e) {
                        LOGGER.error("Cannot send message: {}", message, e);
                    }
                }
            }
        });
        
        return chatFrame;
    }

    @Override
    public JComponent getView() {
        return null;
    }

    @Override
    public void destroyView() {
        // Started JFrames handle their lifecycle on their own
    }

}
