package com.github.ezeakel.infBot.Listeners;

import com.github.ezeakel.infBot.Audio.AudioLoadResultHandlerImplementation;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.VoiceStateUpdateEvent;

public class BotLeavingListener {
    private final GatewayDiscordClient client;
    private final AudioLoadResultHandlerImplementation scheduler;
    private final AudioPlayer player;

    public BotLeavingListener(GatewayDiscordClient client, AudioPlayer player, AudioLoadResultHandlerImplementation scheduler) {
        this.client = client;
        this.player = player;
        this.scheduler = scheduler;
    }

    public void handle(){
        client.getEventDispatcher()
                .on(VoiceStateUpdateEvent.class)
                .subscribe(event -> {
                    if(event.getCurrent().getData().userId().asLong()==1006017503794839622l && event.isLeaveEvent()){
                        scheduler.destroyQueue();
                        player.destroy();
                    }
                });
    }



}
