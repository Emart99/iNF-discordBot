package com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommands;

import com.Emart99.d4j.maven.springboot.Audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;

public class SkipCommand extends ComplexAudioCommand {
    public SkipCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }

    @Override
        public Mono<Void> execute (MessageCreateEvent event){
            return Mono.just(event.getGuild())
                    .publishOn(Schedulers.boundedElastic())
                    .filter(guild -> isBotInVoiceChannel(Objects.requireNonNull(guild.block())))
                    .filter(guild -> scheduler.isPlaying())
                    .flatMap(guild -> {
                        scheduler.nextTrack();
                        return event.getMessage().addReaction(ReactionEmoji.unicode("\u2705"));
                    })
                    .then();

    }
}
