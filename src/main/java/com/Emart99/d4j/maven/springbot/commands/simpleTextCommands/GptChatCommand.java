package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import com.Emart99.d4j.maven.springbot.utils.GptApiConsumer;
import com.Emart99.d4j.maven.springbot.utils.MemeApiConsumer;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class GptChatCommand implements Command {
    GptApiConsumer gptApiConsumer;
    public GptChatCommand(GptApiConsumer gptApi){
        gptApiConsumer = gptApi;
    }
    @Override
    public String getName() {
        return "*gptChat";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        final String content = event.getMessage().getContent().replace("*gptChat ","");

        event.getMessage()
                .getChannel()
                .block()
                .createMessage(gptApiConsumer.getChatMessage(content))
                .block();
    }
}
