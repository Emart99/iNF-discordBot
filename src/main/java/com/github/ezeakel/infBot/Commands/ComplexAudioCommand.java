package com.github.ezeakel.infBot.Commands;

import com.github.ezeakel.infBot.Audio.AudioLoadResultHandlerImplementation;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;

public abstract class ComplexAudioCommand implements Command{
    public AudioProvider provider;
    public AudioPlayerManager playerManager;
    public AudioLoadResultHandlerImplementation scheduler;
    public ComplexAudioCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler){
        this.provider = provider;
        this.playerManager = playerManager;
        this.scheduler = scheduler;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event){
        return Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(VoiceState::getChannel)
                // join returns a VoiceConnection which would be required if we were
                // adding disconnection features, but for now we are just ignoring it.
                .flatMap(channel -> channel.join(spec -> spec.setProvider(provider))).then();
    }
    public Boolean isBotInVoiceChannel(Guild guild){
        return Boolean.TRUE.equals(guild.getVoiceStates()
                .any(voiceState -> guild.getClient().getSelfId().equals(voiceState.getUserId()))
                .block());
    }
}
