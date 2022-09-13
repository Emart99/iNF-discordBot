package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
public class CoinflipCommandV2 implements Command {
    @Override
    public String getName() {
        return "*cf2";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        final String content = event.getMessage().getContent();
        List<String> optionsToRandomize = Arrays.asList(content.split(" "));

        if(optionsToRandomize.size() > 2){
            int randomBetweenN = (int)Math.floor(Math.random()*(optionsToRandomize.size() - 2 + 1)+1);
            final MessageChannel channel = event.getMessage().getChannel().block();
            channel.createMessage(optionsToRandomize.get(randomBetweenN)).block();
        }
        else {
            final MessageChannel channel = event.getMessage().getChannel().block();
            channel.createMessage("Error, cantidad de opciones invalidas, requiere al menos 2").block();
        }

    }
}
