package com.bearmaster.talk.gui.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bearmaster.talk.model.Friend;
import com.jgoodies.forms.builder.FormBuilder;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
public class FriendListPanel extends JPanel {
    
    private static final long serialVersionUID = 6386270562096783819L;

    private static final Logger LOGGER = LoggerFactory.getLogger(FriendListPanel.class);

    private List<Friend> friendList;

    private JScrollPane scrollPanel;
    
    private Map<JButton, Friend> buttonToFriendMap;
    
    private ActionListener commonActionListener;
    
    private boolean initialised = false;

    private void initComponents() {

        FormBuilder friendListPanelBuilder = FormBuilder.create()
                .columns("fill:pref:grow")
                .rows("")
                .panel(this)
                .name("friendListPanel")
                .debug(true);

        LOGGER.debug("About to render {} entries", friendList.size());
        
        int i = 1;
        
        for (Friend friend : friendList) {
            final JButton button = new JButton(friend.getPresence().getType() + " - " + friend.getName());
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.addActionListener(commonActionListener);
            buttonToFriendMap.put(button, friend);
            friendListPanelBuilder.appendRows("pref");
            friendListPanelBuilder.add(button).xy(1, i++);
        }

        friendListPanelBuilder.build();

        scrollPanel = new JScrollPane(this);
        
        initialised = true;
    }

    public JComponent getComponent() {
        if (!initialised) {
            initComponents();
        }
        return scrollPanel;
    }

    public void setFriendList(List<Friend> friendList) {
        this.friendList = friendList;
        this.buttonToFriendMap = new HashMap<>(friendList.size());
    }
    
    public void setCommonActionListener(ActionListener listener) {
        this.commonActionListener = listener;
    }
    
    public Friend getFriendForButton(JButton button) {
        return buttonToFriendMap.get(button);
    }

}
