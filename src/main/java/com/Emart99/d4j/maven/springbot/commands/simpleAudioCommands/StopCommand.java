package com.Emart99.d4j.maven.springbot.commands.simpleAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.SimpleAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.VoiceConnection;
import org.springframework.stereotype.Component;
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
    public String getName() {
        return "*stop";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        scheduler.destroyQueue();
        player.destroy();
        Mono.justOrEmpty(event.getMember())
                .flatMap(Member::getVoiceState)
                .flatMap(voiceState -> client.getVoiceConnectionRegistry().getVoiceConnection(voiceState.getGuildId())
                .flatMap(VoiceConnection::disconnect)).block();
        super.execute(event);
    }
}
