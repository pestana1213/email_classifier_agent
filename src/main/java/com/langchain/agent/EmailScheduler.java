package com.langchain.agent;

import com.langchain.agent.Services.GmailListenerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private final GmailListenerService listener;

    public EmailScheduler(GmailListenerService listener) {
        this.listener = listener;
    }

    @Scheduled(fixedDelay = 6000)
    public void poll() throws Exception {
        listener.checkNewEmails();
    }
}