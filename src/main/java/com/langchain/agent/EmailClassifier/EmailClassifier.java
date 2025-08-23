package com.langchain.agent.EmailClassifier;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;

public interface EmailClassifier {

    @SystemMessage("You are an assistant that classifies emails into categories like: Work, Personal, Spam, Newsletter, Urgent, or if you don't know Unknown. Answer in a single word only.")
    String classify(@UserMessage String emailContent);

}
