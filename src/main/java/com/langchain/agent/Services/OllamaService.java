package com.langchain.agent.Services;

import com.langchain.agent.EmailClassifier.EmailClassifier;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.stereotype.Service;

@Service
public class OllamaService {

    EmailClassifier emailClassifier;

    public OllamaService() {
        OllamaChatModel ollamaChatModel = OllamaChatModel.builder()
                .baseUrl("http://192.168.1.195:11434")
                .modelName("mistral")
                .build();

        this.emailClassifier = AiServices.create(EmailClassifier.class, ollamaChatModel);
    }

    public String classifyEmail(String emailContent) {
        return emailClassifier.classify(emailContent);
    }
}

