package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import com.Emart99.d4j.maven.springbot.utils.GptApiConsumer;
import com.Emart99.d4j.maven.springbot.utils.MemeApiConsumer;
import com.Emart99.d4j.maven.springbot.utils.SplitStringEveryNthChar;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;

public class GptChatCommand implements Command {
    GptApiConsumer gptApiConsumer;
    public GptChatCommand(GptApiConsumer gptApi){
        gptApiConsumer = gptApi;
    }
    @Override
    public String getName() {
        return "*chatGpt";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        final String content = event.getMessage().getContent().replace("*chatGpt ","");
        final String chatGptResponse = gptApiConsumer.getChatMessage(content);
        final MessageChannel discordChannel = event.getMessage().getChannel().block();
        if(chatGptResponse.length() < 2000){
            discordChannel.createMessage(chatGptResponse).block();
        }
        else{
            SplitStringEveryNthChar.splitMethod(chatGptResponse,2000)
                .forEach(message -> discordChannel.createMessage(message).block());
        }

    }
}
