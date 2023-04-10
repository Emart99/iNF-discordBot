package com.github.ezeakel.infBot.Commands.SimpleTextCommands;

import com.github.ezeakel.infBot.Commands.Command;
import com.github.ezeakel.infBot.Utils.MemeApiConsumer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URISyntaxException;

public class MemeCommand implements Command {
    MemeApiConsumer memeApiConsumer;
    public MemeCommand(MemeApiConsumer memeApi){
        memeApiConsumer = memeApi;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) throws IOException, URISyntaxException, InterruptedException {
        String memeLink = memeApiConsumer.getMeme("https://meme-api.com/gimme");
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(memeLink))
                .then();
    }
}
