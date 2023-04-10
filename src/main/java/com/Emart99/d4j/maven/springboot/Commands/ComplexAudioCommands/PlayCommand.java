package com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommands;

import com.Emart99.d4j.maven.springboot.Audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommand;
import com.Emart99.d4j.maven.springboot.Utils.UrlManager;
import com.Emart99.d4j.maven.springboot.Utils.YoutubeSearch;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;

public class PlayCommand extends ComplexAudioCommand {
    private final YoutubeSearch youtubeHelper;
    public PlayCommand(
            AudioProvider provider,
            AudioPlayerManager playerManager,
            AudioLoadResultHandlerImplementation scheduler,
            YoutubeSearch youtubeHelper){
        super(provider,playerManager,scheduler);
        this.youtubeHelper = youtubeHelper;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        scheduler.addActualChannel(event.getMessage().getChannel().block(),event);
        Mono<Void> playSong = Mono.justOrEmpty(event.getMessage().getContent())
                .map(content -> content.replace("*play ",""))
                .map(content -> {
                    if(UrlManager.verifyUrl(content)){
                   playerManager.loadItem(content,scheduler);
                }else {
                    final String youtubeUrl = UrlManager.constructYoutubeUri(youtubeHelper.getResults(content,1).get(0).getCode());
                    playerManager.loadItem(youtubeUrl,scheduler);
                }
                    return Mono.empty();
                })
                .doOnError(e -> System.out.println(e.getMessage()))
                .onErrorResume(e -> Mono.empty())
                .then();

        return super.execute(event).then(playSong);
    }
}
