package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.voice.AudioProvider;
import org.springframework.stereotype.Component;

public class AceiteDeCocoCommand extends ComplexAudioCommand {

    public AceiteDeCocoCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }
    @Override
    public String getName() {
        return "*aceitedecoco";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        try{
            super.execute(event);
            playerManager.loadItem("https://www.youtube.com/watch?v=ZtD6nfmfDZQ&ab_channel=TeQRY", scheduler);
        }catch (NullPointerException ignore){
            event.getMessage().getChannel().block().createMessage("Error, no puede usar este comando sin estar en un canal").block();
        }
    }
}
