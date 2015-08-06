package com.bearmaster.talk.model;

import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.roster.RosterEntry;

public class Friend {
    
    private String jid;
    
    private String name;
    
    private Presence presence;

    public Friend(RosterEntry rosterEntry, Presence presence) {
        this(rosterEntry.getUser(), rosterEntry.getName(), presence);
    }
    
    public Friend(String jid, String name, Presence presence) {
        this.jid = jid;
        this.name = name;
        this.presence = presence;
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }
    
    @Override
    public String toString() {
        return "Friend [jid=" + jid + ", name=" + name + ", presence=" + presence + "]";
    }
}
