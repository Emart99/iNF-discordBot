package com.github.ezeakel.infBot.Audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.MessageChannel;

public class AudioLoadResultHandlerImplementation implements AudioLoadResultHandler {
    private final AudioPlayer player;
    private final TrackScheduler scheduler ;
    public MessageChannel messageChannel;
    private MessageCreateEvent event;
    private Boolean isRepeatableTrack = false;
    private Integer timesRepeated = 0;
    public AudioLoadResultHandlerImplementation(final AudioPlayer player, TrackScheduler scheduler){
        this.player = player;
        this.scheduler = scheduler;
    }

    public void addActualChannel(MessageChannel messageChannel,MessageCreateEvent event){
        this.event = event;
        this.messageChannel = messageChannel;
    }
    public void setRepeat(Boolean bool, Integer _timesRepeated){
        isRepeatableTrack = bool;
        timesRepeated = _timesRepeated;
    }
    public void destroyQueue(){
        this.scheduler.destroyQueue();
    }
    @Override
    public void trackLoaded(final AudioTrack track) {
        scheduler.setChannel(this.event);
        // esto es medio rustico, pero bueno la vida es dura
        System.out.println(isRepeatableTrack + " " + timesRepeated);
        if(isRepeatableTrack && timesRepeated != 0){
            isRepeatableTrack = false;
            this.messageChannel.createMessage(track.getInfo().title + " agregado a la cola " + timesRepeated + " veces").block();
            System.out.println(isRepeatableTrack + " " + timesRepeated);
        }
        if(!isRepeatableTrack && timesRepeated == 0){
            this.messageChannel.createMessage(track.getInfo().title + " agregado a la cola").block();
        }
        scheduler.queue(track);


    }
    @Override
    public void playlistLoaded(final AudioPlaylist playlist) {
        scheduler.setChannel(this.event);
        this.messageChannel.createMessage(playlist.getTracks().size() + " canciones agregadas al cola").block();
        AudioTrack firstTrack = playlist.getSelectedTrack();
        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }
        for(AudioTrack track : playlist.getTracks()){
            scheduler.queue(track);
        }
    }
    public void shufflePlaylist(){
        if(!isPlaying()){
            throw new NullPointerException();
        }
        scheduler.shuffleQueue();
    }
    public void nextTrack(){
        scheduler.nextTrack();
    }
    @Override
    public void noMatches(){
        messageChannel.createMessage("Cancion no encontrada" ).block();
    }

    @Override
    public void loadFailed(FriendlyException e) {messageChannel.createMessage("No es posible reproducir: " + e.getMessage() ).block();}

    public void seek(Long time){
        final long trackTotalDuration  = player.getPlayingTrack().getDuration() ;
        if(trackTotalDuration - time > 0 ){
            player.getPlayingTrack().setPosition(time);
        }
        else {
            throw new FriendlyException("Value not valid", FriendlyException.Severity.COMMON,new Throwable());
        }
    }

    public Boolean isPlaying(){
        return player.getPlayingTrack() != null;
    }


}
