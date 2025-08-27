package com.langchain.agent.Services;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePartHeader;
import com.google.api.services.gmail.model.ModifyMessageRequest;
import com.langchain.agent.Entities.Classifications;
import com.langchain.agent.Utils.GmailLabelHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GmailListenerService {

    private final Gmail gmail;
    private final OllamaService ollamaService;
    private final CopyOnWriteArrayList<Message> processingMessages = new CopyOnWriteArrayList<>();
    private final ClassificationService classificationService;

    public GmailListenerService(Gmail gmail, OllamaService classifier, ClassificationService classificationService) {
        this.gmail = gmail;
        this.ollamaService = classifier;
        this.classificationService = classificationService;
    }

    public void checkNewEmails() throws IOException {
        List<Message> newMessages = gmail.users().messages().list("me")
                .setQ("is:unread newer_than:1d")
                .execute()
                .getMessages();

        if (newMessages != null) {
            for (Message m : newMessages) {
                boolean alreadyProcessing = processingMessages.stream()
                        .anyMatch(msg -> msg.getId().equals(m.getId()));
                if (!alreadyProcessing) {
                    processingMessages.add(m);
                }
            }
        }

        if (processingMessages.isEmpty()) return;
        GmailLabelHelper labelHelper = new GmailLabelHelper(gmail);

        for (Message m : processingMessages) {
            Message fullMessage = gmail.users().messages().get("me", m.getId()).execute();
            String snippet = fullMessage.getSnippet();
            String body = getEmailBody(fullMessage);
            String input = snippet + " " + (body.length() > 1000 ? body.substring(0, 1000) : body);
            String category = ollamaService.classifyEmail(input);
            System.out.println("📩 Gmail API Email classified as: " + category);

            String labelId = labelHelper.getLabelIdForCategory(category);

            ModifyMessageRequest mods = new ModifyMessageRequest()
                    .setAddLabelIds(Collections.singletonList(labelId));
            gmail.users().messages().modify("me", fullMessage.getId(), mods).execute();
            processingMessages.removeIf(msg -> msg.getId().equals(fullMessage.getId()));

            List<MessagePartHeader> headers = fullMessage.getPayload().getHeaders();

            String sender = headers.stream()
                    .filter(h -> "From".equalsIgnoreCase(h.getName()))
                    .map(MessagePartHeader::getValue)
                    .findFirst()
                    .orElse("unknown");

            System.out.println("Sender: " + sender);
            Classifications classifications = Classifications.builder()
                    .classification(category)
                    .sender(sender)
                    .build();

            classificationService.save(classifications);
        }
    }


    private String getEmailBody(Message fullMessage) {
        var payload = fullMessage.getPayload();

        if (payload.getBody() != null && payload.getBody().getData() != null) {
            return new String(Base64.getUrlDecoder().decode(payload.getBody().getData()));
        }

        if (payload.getParts() != null) {
            for (var part : payload.getParts()) {
                if ("text/plain".equals(part.getMimeType()) && part.getBody() != null && part.getBody().getData() != null) {
                    return new String(Base64.getUrlDecoder().decode(part.getBody().getData()));
                }
            }
        }
        return "";
    }
}
