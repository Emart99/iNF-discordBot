package com.github.ezeakel.infBot.Commands.ComplexAudioCommands;

import com.github.ezeakel.infBot.Audio.AudioLoadResultHandlerImplementation;
import com.github.ezeakel.infBot.Commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;

public class ShuffleCommand extends ComplexAudioCommand {
    public ShuffleCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.just(event)
                .flatMap(e -> {
                    try {
                        scheduler.shufflePlaylist();
                        return e.getMessage().addReaction(ReactionEmoji.unicode("\uD83D\uDD00"));
                    } catch (NullPointerException ex) {
                        return Mono.error(ex);
                    }
                })
                .onErrorResume(e -> {
                    event.getMessage().addReaction(ReactionEmoji.unicode("\u274C")).block();
                    return Mono.justOrEmpty(event.getMessage())
                            .flatMap(Message::getChannel)
                            .flatMap(channel -> channel.createMessage("Error, no se puede mezclar una cola vacia"))
                            .then();
                })
                .then();
    }
}
