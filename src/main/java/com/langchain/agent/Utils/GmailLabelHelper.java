package com.langchain.agent.Utils;

import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GmailLabelHelper {

    private final Gmail gmail;
    private final Map<String, String> labelCache = new HashMap<>();

    public GmailLabelHelper(Gmail gmail) {
        this.gmail = gmail;
    }

    public String getLabelIdForCategory(String category) throws IOException {
        if (labelCache.containsKey(category)) {
            return labelCache.get(category);
        }

        ListLabelsResponse labelsResponse = gmail.users().labels().list("me").execute();
        for (Label label : labelsResponse.getLabels()) {
            if (label.getName() != null && label.getName().trim().equalsIgnoreCase(category.trim())) {
                labelCache.put(category, label.getId());
                return label.getId();
            }
        }

        Label newLabel = new Label()
                .setName(category)
                .setLabelListVisibility("labelShow")
                .setMessageListVisibility("show");
        Label createdLabel = gmail.users().labels().create("me", newLabel).execute();

        labelCache.put(category, createdLabel.getId());
        return createdLabel.getId();
    }
}
