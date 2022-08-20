package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.voice.AudioProvider;
import org.springframework.stereotype.Component;

public class SanguchotoCommand extends ComplexAudioCommand {

    public SanguchotoCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }

    @Override
    public String getName() {
        return "*sanguchoto";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        try{
            super.execute(event);
            playerManager.loadItem("https://www.youtube.com/watch?v=3B3KhpcD4I0&ab_channel=EzequielMartino", scheduler);
        }catch (NullPointerException ignore){
            event.getMessage().getChannel().block().createMessage("Error, no puede usar este comando sin estar en un canal").block();
        }
    }
}
