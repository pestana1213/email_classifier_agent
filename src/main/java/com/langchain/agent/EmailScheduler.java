package com.langchain.agent;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {

    private final GmailListenerService listener;

    public EmailScheduler(GmailListenerService listener) {
        this.listener = listener;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void poll() throws Exception {
        listener.checkNewEmails();
    }
}