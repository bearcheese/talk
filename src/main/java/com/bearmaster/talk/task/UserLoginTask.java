package com.bearmaster.talk.task;

import org.jdesktop.application.Application;
import org.jdesktop.application.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bearmaster.talk.model.User;
import com.bearmaster.talk.services.ChatService;

public class UserLoginTask extends Task<Void, Void> {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginTask.class);
    
    private ChatService chatService;
    
    private User user;
    
    public UserLoginTask(Application application, ChatService chatService) {
        super(application);
        this.chatService = chatService;
    }

    @Override
    protected Void doInBackground() throws Exception {
        if (user == null) {
            throw new IllegalStateException("User is not set!");
        }
        
        LOGGER.info("Logging in with user: {}", user);
        chatService.initConnection();
        chatService.login(user.getName(), user.getPassword());
        return null;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
