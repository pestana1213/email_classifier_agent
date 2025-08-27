package com.langchain.agent.Config;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileReader;
import java.util.Collections;

@Configuration
public class GmailConfig {

    private static final String APPLICATION_NAME = "My Email Classifier";
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/client_secret.json";
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    @Bean
    public Gmail gmail() throws Exception {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

        // Load client secrets
        // var in = new FileInputStream(CREDENTIALS_FILE_PATH);
        String credentialsPath = System.getenv("GMAIL_CREDENTIALS");
        var clientSecrets = com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets.load(jsonFactory,  new FileReader(CREDENTIALS_FILE_PATH));
        var scopes = Collections.singleton(GmailScopes.GMAIL_MODIFY);

        // Build flow
        var flow = new com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, scopes)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        var credential = new com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp(
                flow, new com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver())
                .authorize("user");

        return new Gmail.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
}
