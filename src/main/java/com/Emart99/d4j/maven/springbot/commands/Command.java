package com.Emart99.d4j.maven.springbot.commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public interface Command {
    String getName();
    void execute(MessageCreateEvent event) throws Exception;
}
