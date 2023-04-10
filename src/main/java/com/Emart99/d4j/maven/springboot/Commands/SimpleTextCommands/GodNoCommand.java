package com.Emart99.d4j.maven.springboot.Commands.SimpleTextCommands;

import com.Emart99.d4j.maven.springboot.Commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class GodNoCommand implements Command {
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("https://i.imgur.com/QTIZW0z.jpg"))
                .then();
    }
}
