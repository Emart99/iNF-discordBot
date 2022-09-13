package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.Emart99.d4j.maven.springbot.utils.TimeParser;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SeekCommand extends ComplexAudioCommand {

    public SeekCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }

    @Override
    public String getName() {
        return "*seek";
    }

    @Override
    public void execute(MessageCreateEvent event){
        final String time = Arrays.asList(event.getMessage().getContent().split(" ")).get(1);
        final Pattern p = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
        try{
            if(isBotInVoiceChannel(event.getGuild().block()) && scheduler.isPlaying() && p.matcher(time).matches() ){
                scheduler.seek(TimeParser.fronStringToLong(time));
                event.getMessage().addReaction(ReactionEmoji.unicode("✅")).block();
            }
        }catch (FriendlyException e){
            event.getMessage()
                    .getChannel().block()
                    .createMessage("Valor invalido, el formato debe ser 00:00:00, y no puede ser mayor a la duracion del tema").block();
        }
    }

}
