package com.Emart99.d4j.maven.springbot.listeners;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.discordjson.json.MessageActivityData;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

public class CommandListener {
    private final HashMap<String,Command> commands;
    private final GatewayDiscordClient client;
    public CommandListener(HashMap<String,Command> commands, GatewayDiscordClient client) {
        this.commands = commands;
        this.client = client;
    }

    public void handle() {
        client.getEventDispatcher().on(MessageCreateEvent.class).subscribe(event -> {
        final String content = event.getMessage().getContent();
        final List<String> command = Arrays.asList(content.split(" "));
        if (command.get(0).startsWith("*")) {
            try {
                final String commandWithoutAsterisk = command.get(0).replace("*","");
                if(commands.get(commandWithoutAsterisk) != null){
                    commands.get(command.get(0).replace("*","")).execute(event);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        });
    }
}
