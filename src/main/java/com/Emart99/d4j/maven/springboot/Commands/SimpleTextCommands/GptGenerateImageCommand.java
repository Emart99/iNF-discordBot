package com.Emart99.d4j.maven.springboot.Commands.SimpleTextCommands;

import com.Emart99.d4j.maven.springboot.Commands.Command;
import com.Emart99.d4j.maven.springboot.Utils.GptApiConsumer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public class GptGenerateImageCommand implements Command {
    GptApiConsumer gptApiConsumer;
    public GptGenerateImageCommand(GptApiConsumer gptApi){gptApiConsumer = gptApi;}

    @Override
    public Mono<Void> execute(MessageCreateEvent event){
        final String content = event.getMessage().getContent().replace("chatGpt ","");
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(gptApiConsumer.getImage(content)))
                .then();
    }
}
