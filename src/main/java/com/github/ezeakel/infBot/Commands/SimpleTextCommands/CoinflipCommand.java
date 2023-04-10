package com.github.ezeakel.infBot.Commands.SimpleTextCommands;

import com.github.ezeakel.infBot.Commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.util.Random;

public class CoinflipCommand implements Command {
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        int randomBetweenOneAndCero = new Random().nextInt(2);
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> randomBetweenOneAndCero == 1 ? channel.createMessage("cruz") : channel.createMessage("cara"))
                .then();
    }
}
