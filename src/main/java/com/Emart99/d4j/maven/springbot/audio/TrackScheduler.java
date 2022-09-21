package com.Emart99.d4j.maven.springbot.audio;

import com.Emart99.d4j.maven.springbot.utils.TimeParser;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.Member;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import discord4j.voice.VoiceConnection;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private MessageChannel messageChannel;
    private Boolean isActive = true;
    private GatewayDiscordClient client;
    private MessageCreateEvent event;

    public TrackScheduler(AudioPlayer player, GatewayDiscordClient client) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.client = client;
    }

    public void queue(AudioTrack track) {
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }
    public void destroyQueue(){
        queue.clear();
    }

    public void nextTrack() {
        if(hasNext()){
            player.startTrack(queue.poll(),false);
        }
    }
    public boolean hasNext(){
        return queue.size() > 0;
    }
    public void shuffleQueue(){
            var queueToArrayList =  new ArrayList<AudioTrack>();
            queue.drainTo(queueToArrayList);
            Collections.shuffle(queueToArrayList);
            queueToArrayList.forEach(track -> this.queue(track));
    }
    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        if (endReason.mayStartNext){
            nextTrack();
        }
        if(!hasNext()){
            isActive = false;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run(){
                    if(!isActive){
                        Mono.justOrEmpty(event.getMember())
                                .flatMap(Member::getVoiceState)
                                .flatMap(voiceState -> client.getVoiceConnectionRegistry().getVoiceConnection(voiceState.getGuildId())
                                        .flatMap(VoiceConnection::disconnect)).subscribe();
                    }
                    timer.cancel();
                }
            }, 300000, 300000);
        }
    }
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track){
        isActive = true;
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.author(track.getInfo().author,null,null);
        builder.thumbnail("https://cdn.discordapp.com/emojis/689959593698394198.webp?size=96&quality=lossless");
        builder.title(track.getInfo().title);
        builder.url(track.getInfo().uri);
        builder.addField("duracion: ", TimeParser.fromLongToHoursMinutesSeconds(track.getDuration()),true);
        builder.timestamp(Instant.now());
        messageChannel.createMessage(builder.build()).subscribe();
    }


    public void setChannel(MessageCreateEvent event){
        this.event = event;
        this.messageChannel = event.getMessage().getChannel().block();
    }
}

