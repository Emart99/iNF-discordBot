package com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommands;

import com.Emart99.d4j.maven.springboot.Audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommand;
import com.Emart99.d4j.maven.springboot.Utils.UrlManager;
import com.Emart99.d4j.maven.springboot.Utils.YoutubeSearch;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Message;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

public class RepeatCommand extends ComplexAudioCommand {
    private final YoutubeSearch youtubeHelper;
    public RepeatCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler, YoutubeSearch youtubeHelper) {
        super(provider, playerManager, scheduler);
        this.youtubeHelper = youtubeHelper;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        try {
            scheduler.addActualChannel(event.getMessage().getChannel().block(), event);
            final List<String> message = Arrays.asList(event.getMessage().getContent().split(" "));
            final String urlOrSomethingToPlay = message.get(2);
            final Integer numberOfRepeat = Integer.valueOf(message.get(1));
            scheduler.setRepeat(true, numberOfRepeat);
            if (UrlManager.verifyUrl(urlOrSomethingToPlay)) {
                Mono<Void> loadItemsMono = Mono.fromRunnable(() -> {
                    for (int i = 0; i < numberOfRepeat; i++) {
                        playerManager.loadItem(urlOrSomethingToPlay.replace(" ", ""), scheduler);
                    }
                });
                return super.execute(event).then(loadItemsMono);
            } else {
                final String youtubeUrl = UrlManager.constructYoutubeUri(youtubeHelper.getResults(urlOrSomethingToPlay, 1).get(0).getCode());
                Mono<Void> loadItemsMono = Mono.fromRunnable(() -> {
                    for (int i = 0; i < numberOfRepeat; i++) {
                        playerManager.loadItem(youtubeUrl, scheduler);
                    }
                });
                return super.execute(event).then(loadItemsMono);
            }
        }
        catch (Exception e){
            return Mono.justOrEmpty(event.getMessage())
                    .flatMap(Message::getChannel)
                    .flatMap(channel -> channel.createMessage("Algo salio mal: parametros invalidos")).then();
        }
    }
}
