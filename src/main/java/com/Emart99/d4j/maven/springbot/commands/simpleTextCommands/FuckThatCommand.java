package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class FuckThatCommand implements Command {
    @Override
    public String getName() {
        return "*fuckthat";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        event.getMessage()
                .getChannel()
                .block()
                .createMessage("https://i.imgur.com/DuAfmIi.png")
                .block();
    }
}
