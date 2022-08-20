package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;
import org.springframework.stereotype.Component;

public class SkipCommand extends ComplexAudioCommand {
    public SkipCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }

    @Override
    public String getName() {
        return "*skip";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        try{
            if(isBotInVoiceChannel(event.getGuild().block()) && scheduler.isPlaying()){
                scheduler.nextTrack();
                event.getMessage().addReaction(ReactionEmoji.unicode("✅")).block();
            }
        }catch (NullPointerException ignore){
            event.getMessage().getChannel().block().createMessage("Error, no puede usar este comando sin estar en un canal").block();
        }
    }
}
