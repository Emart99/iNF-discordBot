package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;

public class ShuffleCommand extends ComplexAudioCommand {
    public ShuffleCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }

    @Override
    public String getName() {
        return "*shuffle";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception{
        try{
            scheduler.shufflePlaylist();
            event.getMessage().addReaction(ReactionEmoji.unicode("\uD83D\uDD00")).block();
        }
        catch (NullPointerException ex){
            event.getMessage().addReaction(ReactionEmoji.unicode("\u274C")).block();
            event.getMessage().getChannel().block().createMessage("Error, no se puede mezclar una cola vacia").block();
        }
    }

}
