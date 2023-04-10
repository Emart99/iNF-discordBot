package com.github.ezeakel.infBot.Commands.SimpleAudioCommands;

import com.github.ezeakel.infBot.Audio.AudioLoadResultHandlerImplementation;
import com.github.ezeakel.infBot.Commands.SimpleAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.voice.VoiceConnection;
import reactor.core.publisher.Mono;

public class StopCommand extends SimpleAudioCommand {
    final private GatewayDiscordClient client;
    final private AudioLoadResultHandlerImplementation scheduler;
    public StopCommand(AudioPlayer player, GatewayDiscordClient client, AudioLoadResultHandlerImplementation scheduler) {
        super(player);
        this.client = client;
        this.scheduler = scheduler;
    }
    @Override
    public Mono<Void> execute(MessageCreateEvent event){
        scheduler.destroyQueue();
        player.destroy();
        return Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(voiceState -> client.getVoiceConnectionRegistry().getVoiceConnection(voiceState.getGuildId())
                        .flatMap(VoiceConnection::disconnect)).then(super.execute(event));
    }
}
