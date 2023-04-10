package com.github.ezeakel.infBot.Commands.SimpleTextCommands;

import com.github.ezeakel.infBot.Commands.Command;
import com.github.ezeakel.infBot.Utils.GptApiConsumer;
import com.github.ezeakel.infBot.Utils.SplitStringEveryNthChar;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GptChatCommand implements Command {
    GptApiConsumer gptApiConsumer;
    public GptChatCommand(GptApiConsumer gptApi){
        gptApiConsumer = gptApi;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        final String content = event.getMessage().getContent().replace("*chatGpt ","");
        return Mono.fromSupplier(() -> gptApiConsumer.getChatMessage(content))
                .flatMap(chatGptResponse -> {
                    MessageChannel discordChannel = event.getMessage().getChannel().block();
                    if (chatGptResponse.length() < 2000) {
                        return discordChannel.createMessage(chatGptResponse).then();
                    } else {
                        return Flux.fromIterable(SplitStringEveryNthChar.splitMethod(chatGptResponse,2000))
                                .flatMap(message -> discordChannel.createMessage(message))
                                .then();
                    }
                });
    }
}
