package com.Emart99.d4j.maven.springbot.utils;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GptApiConsumer {
    OpenAiService aiService;
    public GptApiConsumer(OpenAiService aiService){
        this.aiService = aiService;
    }
    public String getChatMessage(String message){
        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), message);
        messages.add(systemMessage);
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .logitBias(new HashMap<>())
                .build();

        return this.aiService.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }
}
