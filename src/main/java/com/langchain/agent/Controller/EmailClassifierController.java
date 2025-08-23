package com.langchain.agent.Controller;

import com.langchain.agent.EmailClassifier.OllamaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/emails")
public class EmailClassifierController {

    private final OllamaService service;

    public EmailClassifierController(OllamaService service) {
        this.service = service;
    }

    @PostMapping("/classify")
    public String classify(@RequestBody String emailContent) {
        return service.classifyEmail(emailContent);
    }
}