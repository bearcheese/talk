package com.bearmaster.talk.gui.component;

import java.awt.event.ActionListener;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Type;
import org.jivesoftware.smack.packet.Presence.Mode;
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

    private static final Map<Mode, ImageIcon> STATE_ICON_MAP;

    static {
        STATE_ICON_MAP = new EnumMap<>(Mode.class);

        STATE_ICON_MAP.put(Mode.chat, createImageIcon("/com/bearmaster/talk/gui/resources/online.png", "chat"));
        STATE_ICON_MAP.put(Mode.available, createImageIcon("/com/bearmaster/talk/gui/resources/online.png", "online"));
        STATE_ICON_MAP.put(Mode.away, createImageIcon("/com/bearmaster/talk/gui/resources/away.png", "away"));
        STATE_ICON_MAP.put(Mode.xa, createImageIcon("/com/bearmaster/talk/gui/resources/away.png", "extended away"));
        STATE_ICON_MAP.put(Mode.dnd, createImageIcon("/com/bearmaster/talk/gui/resources/busy.png", "do not disturb"));

    }

    private void initComponents() {

        FormBuilder friendListPanelBuilder = FormBuilder.create().columns("fill:pref:grow").rows("").panel(this)
                .name("friendListPanel").debug(true);

        LOGGER.debug("About to render {} entries", friendList.size());

        int i = 1;

        for (Friend friend : friendList) {
            ImageIcon stateIcon = getStateIcon(friend.getPresence());
            final JButton button = new JButton();
            button.add(new JLabel(friend.getName(), stateIcon, SwingConstants.CENTER));
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

    private ImageIcon getStateIcon(Presence presence) {
        ImageIcon icon = createImageIcon("/com/bearmaster/talk/gui/resources/offline.png", "unavailable");

        if (presence.getType() == Type.available) {
            icon = STATE_ICON_MAP.get(presence.getMode());
        }

        return icon;
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

    // TODO use central resource manager
    protected static ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = ChatFrame.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            LOGGER.warn("Couldn't find image file: {}", path);
            return null;
        }
    }

}
