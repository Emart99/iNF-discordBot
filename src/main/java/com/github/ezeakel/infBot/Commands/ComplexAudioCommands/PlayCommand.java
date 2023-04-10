package com.github.ezeakel.infBot.Commands.ComplexAudioCommands;

import com.github.ezeakel.infBot.Audio.AudioLoadResultHandlerImplementation;
import com.github.ezeakel.infBot.Commands.Command;
import com.github.ezeakel.infBot.Commands.ComplexAudioCommand;
import com.github.ezeakel.infBot.Utils.UrlManager;
import com.github.ezeakel.infBot.Utils.YoutubeSearch;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.VoiceState;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.channel.Channel;
import discord4j.voice.AudioProvider;
import reactor.core.publisher.Mono;

import java.util.Arrays;

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
