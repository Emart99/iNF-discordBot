package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
public class PorroCommand implements Command {
    @Override
    public String getName() {
        return "*porro";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        event.getMessage()
                .getChannel()
                .block()
                .createMessage("\uD83D\uDE17\uD83D\uDC4C\uD83D\uDEAC")
                .block();

    }
}
