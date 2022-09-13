package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.voice.AudioProvider;
import org.springframework.stereotype.Component;

public class HitBenhaCommand extends ComplexAudioCommand {

    public HitBenhaCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler) {
        super(provider, playerManager, scheduler);
    }
    @Override
    public String getName() {
        return "*hitbenha";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        try{
            super.execute(event);
            playerManager.loadItem("https://www.youtube.com/watch?v=F16ipmKk1Vc&ab_channel=EzequielMartino", scheduler);
        }catch (NullPointerException ignore){
            event.getMessage().getChannel().block().createMessage("Error, no puede usar este comando sin estar en un canal").block();
        }

    }
}
