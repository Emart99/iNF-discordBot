package com.Emart99.d4j.maven.springbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;

public abstract class SimpleAudioCommand implements Command{
    public AudioPlayer player;
    public SimpleAudioCommand(AudioPlayer player){
        this.player = player;
    }
    public Boolean isPlaying(){
        return player.getPlayingTrack() != null;
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        event.getMessage().addReaction(ReactionEmoji.unicode("\u2705")).subscribe();
    }
}
