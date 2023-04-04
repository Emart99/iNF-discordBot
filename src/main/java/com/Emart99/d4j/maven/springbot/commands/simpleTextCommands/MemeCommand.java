package com.Emart99.d4j.maven.springbot.commands.simpleTextCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import com.Emart99.d4j.maven.springbot.utils.MemeApiConsumer;
import discord4j.core.event.domain.message.MessageCreateEvent;

public class MemeCommand implements Command {
    MemeApiConsumer memeApiConsumer;
    public MemeCommand(MemeApiConsumer memeApi){
        memeApiConsumer = memeApi;
    }
    @Override
    public String getName() {
        return "*meme";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        String memeLink = memeApiConsumer.getMeme("https://meme-api.com/gimme");
        event.getMessage()
                .getChannel()
                .block()
                .createMessage(memeLink)
                .block();
    }
}
