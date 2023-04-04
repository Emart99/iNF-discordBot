package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import com.Emart99.d4j.maven.springbot.utils.GptApiConsumer;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class GptGenerateImageCommand implements Command {
    GptApiConsumer gptApiConsumer;
    public GptGenerateImageCommand(GptApiConsumer gptApi){gptApiConsumer = gptApi;}
    @Override
    public String getName() {
        return "*generateImageGpt";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        final String content = event.getMessage().getContent().replace("*chatGpt ","");
        event.getMessage()
                .getChannel()
                .block()
                .createMessage(gptApiConsumer.getImage(content))
                .block();

    }
}
