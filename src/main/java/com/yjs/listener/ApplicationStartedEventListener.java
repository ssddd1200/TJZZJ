package com.yjs.listener;

import com.yjs.panel.ContentFrame;
import com.yjs.panel.MainPanel;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {

    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        new ContentFrame();

    }
}
