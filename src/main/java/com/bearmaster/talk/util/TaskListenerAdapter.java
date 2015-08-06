package com.bearmaster.talk.util;

import java.util.List;

import org.jdesktop.application.TaskEvent;
import org.jdesktop.application.TaskListener;

public class TaskListenerAdapter<T, V> implements TaskListener<T, V> {

    @Override
    public void doInBackground(TaskEvent<Void> event) {}

    @Override
    public void process(TaskEvent<List<V>> event) {}

    @Override
    public void succeeded(TaskEvent<T> event) {}

    @Override
    public void failed(TaskEvent<Throwable> event) {}

    @Override
    public void cancelled(TaskEvent<Void> event) {}

    @Override
    public void interrupted(TaskEvent<InterruptedException> event) {}

    @Override
    public void finished(TaskEvent<Void> event) {}

}
