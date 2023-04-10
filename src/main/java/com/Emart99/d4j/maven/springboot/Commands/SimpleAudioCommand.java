package com.Emart99.d4j.maven.springboot.Commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import reactor.core.publisher.Mono;

public class SimpleAudioCommand implements Command{
    public AudioPlayer player;
    public SimpleAudioCommand(AudioPlayer player){
        this.player = player;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return Mono.justOrEmpty(event.getMessage())
                .flatMap(message -> message.addReaction(ReactionEmoji.unicode("\u2705")))
                .then();
    }

    public Boolean isPlaying(){return player.getPlayingTrack() != null;}

}
