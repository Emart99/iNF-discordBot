package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import com.Emart99.d4j.maven.springbot.utils.ApiConsumer;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class MemeCommand implements Command {
    @Override
    public String getName() {
        return "*meme";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        String memeLink = ApiConsumer.consume("https://meme-api.herokuapp.com/gimme");
        event.getMessage()
                .getChannel()
                .block()
                .createMessage(memeLink)
                .block();
    }
}
