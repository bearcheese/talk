package com.bearmaster.talk.gui;

import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.bearmaster.talk.gui.controller.MainController;
import com.bearmaster.talk.gui.listener.ConfirmExitListener;
import com.bearmaster.talk.util.AppConfig;

public class App extends SingleFrameApplication {
    
    private MainController mainController;
    
    private AnnotationConfigApplicationContext springContext;

    @Override
    protected void startup() {
        springContext = initSpringContext();
        
        mainController = springContext.getBean(MainController.class);
        
        addExitListener(new ConfirmExitListener());
        addExitListener(mainController);
        
        View view = getMainView();
        
        view.setComponent(mainController.getInitialView());
        
        show(view);
    }
    
    private AnnotationConfigApplicationContext initSpringContext() {
        AppConfig.setApplication(this);
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        
        return ctx;
    }
    
    public ApplicationContext getSpringApplicationContext() {
        return springContext;
    }

    public static void main(String[] args) {
        launch(App.class, args);
    }
}
