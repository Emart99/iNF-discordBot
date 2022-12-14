package com.Emart99.d4j.maven.springbot.commands.simpleAudioCommands;

import com.Emart99.d4j.maven.springbot.commands.SimpleAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import org.springframework.stereotype.Component;

public class ResumeCommand extends SimpleAudioCommand {

    public ResumeCommand(AudioPlayer player) {
        super(player);
    }

    @Override
    public String getName() {
        return "*resume";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        if(isPlaying()){
            player.setPaused(false);
            super.execute(event);
        }
    }
}
