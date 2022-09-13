package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class CoinflipCommand implements Command {
    @Override
    public String getName() {
        return "*cf";
    }

    @Override
    public void execute(MessageCreateEvent event) {
        int randomBetweenOneAndCero = new Random().nextInt(2);
        final MessageChannel channel = event.getMessage().getChannel().block();
        if(randomBetweenOneAndCero == 1){
            channel.createMessage("cruz").block();
        }
        else{
            channel.createMessage("seca").block();
        }
    }
}
