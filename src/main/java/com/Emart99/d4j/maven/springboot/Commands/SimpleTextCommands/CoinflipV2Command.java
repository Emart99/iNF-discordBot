package com.Emart99.d4j.maven.springboot.Commands.SimpleTextCommands;

import com.Emart99.d4j.maven.springboot.Commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class CoinflipV2Command implements Command {
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage().getContent())
                .flatMap(content -> {
                    List<String> optionsToRandomize = Arrays.asList(content.split(" "));
                    if (optionsToRandomize.size() > 2) {
                        int randomBetweenN = (int) Math.floor(Math.random() * (optionsToRandomize.size() - 2 + 1) + 1);
                        return event
                                .getMessage()
                                .getChannel()
                                .flatMap(messageChannel -> messageChannel.createMessage(optionsToRandomize.get(randomBetweenN)))
                                .then();
                    } else {
                        return event
                                .getMessage()
                                .getChannel()
                                .flatMap(messageChannel -> messageChannel.createMessage("Error, cantidad de opciones invalidas, requiere al menos 2"))
                                .then();
                    }
                });

    }
}
