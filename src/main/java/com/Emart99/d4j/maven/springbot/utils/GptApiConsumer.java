package com.Emart99.d4j.maven.springbot.utils;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.image.CreateImageRequest;
import com.theokanning.openai.service.OpenAiService;

import java.util.*;

public class GptApiConsumer {
    OpenAiService aiService;
    public GptApiConsumer(OpenAiService aiService){
        this.aiService = aiService;
    }
    private List<ChatMessage> messages = new ArrayList<>();
    private  Timer timer ;

    public String getChatMessage(String message){
        final ChatMessage userMessage = new ChatMessage(ChatMessageRole.USER.value(), message);
        if(messages.isEmpty()){
            iniciarTImer();
            addMessage(userMessage);
        }
        else {
            resetearTimer();
            addMessage(userMessage);
        }
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(1024)
                .logitBias(new HashMap<>())
                .build();
        try{
            final ChatCompletionResult chatCompetion = this.aiService.createChatCompletion(chatCompletionRequest);
            System.out.println(messages);
            return chatCompetion.getChoices().get(0).getMessage().getContent();
        }
        catch (RuntimeException r){
            return "No soy capaz de generar una respuesta para esa pregunta, en un periodo de tiempo corto.";
        }
    }
    public String getImage(String message){
        CreateImageRequest request = CreateImageRequest.builder()
                .prompt(message)
                .build();
        return aiService.createImage(request).getData().get(0).getUrl();
    }


    // Clean the message list if there's no new messages in the last 5 minutes
    // this resets the conversation.
    private void iniciarTImer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                messages.clear();
                timer.cancel();
                System.out.println("entre");
            }
        },300000);
    }
    private void resetearTimer(){
        timer.cancel();
        iniciarTImer();
    }
    public synchronized void addMessage(ChatMessage message){
        messages.add(message);
    }
}
