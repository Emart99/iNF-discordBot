package com.Emart99.d4j.maven.springbot.commands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.channel.VoiceChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;

import java.util.Objects;

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
    public void execute(MessageCreateEvent event) throws Exception {
            Guild guild = event.getGuild().block();
            VoiceChannel voiceChannel = event.getMember().orElse(null).getVoiceState().block().getChannel().block();
            assert guild != null;
            if (!isBotInVoiceChannel(guild)) {
                voiceChannel.join(spec -> spec.setProvider(provider)).block();
            }
            scheduler.addActualChannel(event.getMessage().getChannel().block());
    }

    public Boolean isBotInVoiceChannel(Guild guild){
        return Boolean.TRUE.equals(guild.getVoiceStates()
                .any(voiceState -> guild.getClient().getSelfId().equals(voiceState.getUserId()))
                .block());
    }
}
