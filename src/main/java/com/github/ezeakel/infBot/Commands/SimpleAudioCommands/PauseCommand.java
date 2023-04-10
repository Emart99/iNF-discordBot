package com.github.ezeakel.infBot.Commands.SimpleAudioCommands;

import com.github.ezeakel.infBot.Commands.Command;
import com.github.ezeakel.infBot.Commands.SimpleAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import reactor.core.publisher.Mono;

public class PauseCommand extends SimpleAudioCommand {
    public PauseCommand(AudioPlayer player){
        super(player);
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.fromRunnable(() ->
                {
                    if(isPlaying()){
                        player.setPaused(true);
                    }
                }
        ).then(super.execute(event));
    }
}
