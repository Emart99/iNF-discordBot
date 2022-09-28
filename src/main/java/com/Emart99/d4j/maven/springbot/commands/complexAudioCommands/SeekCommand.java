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
        try{
            String time = Arrays.asList(event.getMessage().getContent().split(" ")).get(1);
            //System.out.print(scheduler.getActualTrackDuration());
            final Pattern patternWithHours = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
            final Pattern patternWithoutHours = Pattern.compile("\\d{2}:\\d{2}");
            if(patternWithoutHours.matcher(time).matches()){
                final String zero = "00:";
                time = zero.concat(time);
                System.out.println(time);
            }

            if(isBotInVoiceChannel(event.getGuild().block()) && scheduler.isPlaying() && patternWithHours.matcher(time).matches()){
                scheduler.seek(TimeParser.fronStringToLong(time));
                event.getMessage().addReaction(ReactionEmoji.unicode("\u2705")).block();
            }
        }catch (NullPointerException | FriendlyException e){
            event.getMessage()
                    .getChannel().block()
                    .createMessage("Valor invalido, el formato debe ser 00:00 o 00:00:00, y no puede ser mayor a la duracion del tema").block();
        }
        catch (ArrayIndexOutOfBoundsException e){
            event.getMessage()
                    .getChannel().block()
                    .createMessage("Error, no ha indicado el tiempo.").block();
        }
    }

}
