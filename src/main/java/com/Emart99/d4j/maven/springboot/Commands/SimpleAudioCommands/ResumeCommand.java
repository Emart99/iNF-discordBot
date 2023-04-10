package com.Emart99.d4j.maven.springboot.Commands.SimpleAudioCommands;

import com.Emart99.d4j.maven.springboot.Commands.SimpleAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class ResumeCommand extends SimpleAudioCommand {
    public ResumeCommand(AudioPlayer player) {
        super(player);
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.fromRunnable(() ->
                {
                    if(isPlaying()){
                        player.setPaused(false);
                    }
                }
        ).then(super.execute(event));
    }
}
