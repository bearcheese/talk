package com.bearmaster.talk.util;

import static org.jivesoftware.smack.packet.Presence.Mode;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

import org.jivesoftware.smack.packet.Presence;

import com.bearmaster.talk.model.Friend;

public class DefaultFriendComparator implements Comparator<Friend> {

    private Map<Presence.Mode, Integer> presenceModeMap;
    
    public DefaultFriendComparator() {
        presenceModeMap = new EnumMap<>(Mode.class);
        presenceModeMap.put(Mode.chat, 1);
        presenceModeMap.put(Mode.available, 2);
        presenceModeMap.put(Mode.away, 3);
        presenceModeMap.put(Mode.xa, 4);
        presenceModeMap.put(Mode.dnd, 5);
    }
    
    @Override
    public int compare(Friend f1, Friend f2) {
        
        int f1Score = availabilityScore(f1.getPresence());
        int f2Score = availabilityScore(f2.getPresence());
        
        if (f1Score == f2Score) {
            return f1.getName().compareToIgnoreCase(f2.getName());
        }
        
        return f1Score - f2Score;
    }
    
    private int availabilityScore(Presence presence) {
        int score = 100;
        if (presence.getType() == Presence.Type.available) {
            score = presenceModeMap.get(presence.getMode());
        }
        
        return score;
    }

}
