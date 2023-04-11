package com.github.ezeakel.infBot.Commands.SimpleTextCommands;

import com.github.ezeakel.infBot.Commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.rest.util.Color;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class PingCommand implements Command {
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(messageChannel ->  messageChannel.createMessage("Pong: "+ (System.currentTimeMillis() - event.getMessage().getTimestamp().toEpochMilli() ) + "ms"))
                .then();
    }
}

