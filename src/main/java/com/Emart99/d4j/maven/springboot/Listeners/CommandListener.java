package com.Emart99.d4j.maven.springboot.Listeners;

import com.Emart99.d4j.maven.springboot.Commands.Command;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;

public class CommandListener {
    private final HashMap<String, Command> commands;
    private final GatewayDiscordClient client;
    public CommandListener(HashMap<String,Command> commands, GatewayDiscordClient client) {
        this.commands = commands;
        this.client = client;
    }
    public void handle() {
        client.getEventDispatcher().on(MessageCreateEvent.class)
                // 3.1 Message.getContent() is a String
                .flatMap(event -> Mono.just(event.getMessage().getContent())
                        .flatMap(content -> Flux.fromIterable(commands.entrySet())
                                // We will be using ! as our "prefix" to any command in the system.
                                .filter(entry -> Arrays.asList(content.split(" ")).get(0).equals('*' + entry.getKey()))
                                .flatMap(entry -> {
                                    try {
                                        return entry.getValue().execute(event);
                                    } catch (IOException | InterruptedException | URISyntaxException e) {
                                        return Flux.error(new RuntimeException(e));
                                    }
                                })
                                .next()))
                .subscribe();

    }

}
