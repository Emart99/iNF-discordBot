package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class BalduCommand implements Command {
    @Override
    public String getName() {
        return "*baldu";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        event.getMessage()
                .getChannel()
                .block()
                .createMessage("https://i.imgur.com/m60bqP3.gif")
                .block();
    }
}
