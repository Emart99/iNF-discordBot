package com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommands;

import com.Emart99.d4j.maven.springboot.Utils.TimeParser;
import com.Emart99.d4j.maven.springboot.Audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Guild;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.regex.Pattern;

public class SeekCommand extends ComplexAudioCommand {
    public SeekCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }
    public Mono<Void> execute(MessageCreateEvent event) {
        final Pattern patternWithHours = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
        final Pattern patternWithoutHours = Pattern.compile("\\d{2}:\\d{2}");

        return Mono.just(event)
                .map(MessageCreateEvent::getMessage)
                .map(Message::getContent)
                .map(content -> Arrays.asList(content.split(" ")).get(1))
                .map(time -> {
                    if(patternWithoutHours.matcher(time).matches()){
                        final String zero = "00:";
                        time = zero.concat(time);
                        System.out.println(time);
                    }
                    return time;
                })
                .flatMap(time -> {
                    try {
                        Guild guild = event.getGuild().block();
                        if(isBotInVoiceChannel(guild) && scheduler.isPlaying() && patternWithHours.matcher(time).matches()){
                            scheduler.seek(TimeParser.fronStringToLong(time));
                            return event.getMessage().addReaction(ReactionEmoji.unicode("\u2705"));
                        } else {
                            return Mono.empty();
                        }
                    } catch (FriendlyException e) {
                        return Mono.error(e);
                    }
                })
                .onErrorResume(e -> {
                    String message;
                    if (e instanceof NullPointerException || e instanceof FriendlyException) {
                        message = "Valor invalido, el formato debe ser 00:00 o 00:00:00, y no puede ser mayor a la duracion del tema";
                    } else if (e instanceof ArrayIndexOutOfBoundsException) {
                        message = "Error, no ha indicado el tiempo.";
                    } else {
                        message = "Error desconocido: " + e.getMessage();
                    }
                    return Mono.justOrEmpty(event.getMessage())
                            .flatMap(Message::getChannel)
                            .flatMap(channel -> channel.createMessage(message))
                            .then();
                })
                .then();
    }
    }

