package com.github.ezeakel.infBot.Commands.SimpleTextCommands;

import com.github.ezeakel.infBot.Commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class PorroCommand implements Command {

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("\uD83D\uDE17\uD83D\uDC4C\uD83D\uDEAC"))
                .then();
    }
}
