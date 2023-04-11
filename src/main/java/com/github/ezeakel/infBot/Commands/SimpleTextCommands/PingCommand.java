package com.github.ezeakel.infBot.Commands.SimpleTextCommands;

import com.github.ezeakel.infBot.Commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class PingCommand implements Command {
    @Override
    public Mono<Void> execute(MessageCreateEvent event) throws IOException, URISyntaxException, InterruptedException {
        String ping = (Instant.now().minus(event.getMessage().getTimestamp().toEpochMilli(), ChronoUnit.MILLIS).toString())+"ms";
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(messageChannel -> messageChannel.createMessage(ping))
                .then();
    }
}
