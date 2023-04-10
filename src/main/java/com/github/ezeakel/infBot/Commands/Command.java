package com.github.ezeakel.infBot.Commands;

import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;

public interface Command {
    Mono<Void> execute(MessageCreateEvent event) throws IOException, URISyntaxException, InterruptedException;
}
