# Gmail Email Classifier Agent

This project is a **Spring Boot service** that listens to your Gmail inbox, classifies incoming emails using an AI model (Ollama via LangChain4j), and organizes them with Gmail labels. Emails remain **unread**, and only new emails are processed.

---

## Features

- Poll Gmail inbox for **unread emails**.
- Extract email body safely, including multipart messages.
- Classify emails with an AI model using **LangChain4j + Ollama**.
- Automatically create or apply **Gmail labels** based on classification.
- Keeps a **thread-safe pool of messages** to avoid duplicate processing.
- Supports **retry and timeout handling** for classification.
- Fully **Spring Boot** ready and configurable via scheduled tasks.

---

## Prerequisites

- Java 21+
- Maven
- Gmail account with API access
- Ollama API or local AI model running
- Spring Boot 3.5.x
