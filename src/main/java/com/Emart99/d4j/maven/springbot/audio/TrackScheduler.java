package com.Emart99.d4j.maven.springbot.audio;

import com.Emart99.d4j.maven.springbot.utils.TimeParser;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.spec.EmbedCreateSpec;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private MessageChannel messageChannel;

    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
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
        if (endReason.mayStartNext) {
            nextTrack();
        }
    }
    @Override
    public void onTrackStart(AudioPlayer player, AudioTrack track){
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.author(track.getInfo().author,null,null);
        builder.thumbnail("https://cdn.discordapp.com/emojis/689959593698394198.webp?size=96&quality=lossless");
        builder.title(track.getInfo().title);
        builder.url(track.getInfo().uri);
        builder.addField("duracion: ", TimeParser.fromLongToHoursMinutesSeconds(track.getDuration()),true);
        builder.timestamp(Instant.now());
        messageChannel.createMessage(builder.build()).subscribe();
    }


    public void setChannel(MessageChannel channel){
        this.messageChannel = channel;
    }
}

