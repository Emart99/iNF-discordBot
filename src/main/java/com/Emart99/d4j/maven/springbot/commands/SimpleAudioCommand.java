package com.Emart99.d4j.maven.springbot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;

public abstract class SimpleAudioCommand implements Command{
    public AudioPlayer player;
    public SimpleAudioCommand(AudioPlayer player){
        this.player = player;
    }
    public Boolean isPlaying(){
        return player.getPlayingTrack() != null;
    }
}
