package com.Emart99.d4j.maven.springbot.commands.complexAudioCommands;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.commands.ComplexAudioCommand;
import com.Emart99.d4j.maven.springbot.utils.UrlManager;
import com.Emart99.d4j.maven.springbot.utils.YoutubeSearch;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.voice.AudioProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
public class PlayCommand extends ComplexAudioCommand {
    private final YoutubeSearch youtubeHelper;
    public PlayCommand(AudioProvider provider, AudioPlayerManager playerManager, AudioLoadResultHandlerImplementation scheduler, YoutubeSearch youtubeHelper) {
        super(provider, playerManager, scheduler);
        this.youtubeHelper = youtubeHelper;
    }
    @Override
    public String getName() {
        return "*play";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        try{
            final String message = event.getMessage().getContent().replace("*play ","");
            super.execute(event);
            if(isBotInVoiceChannel((Objects.requireNonNull(event.getGuild().block())))){
                if(UrlManager.verifyUrl(message)){
                    playerManager.loadItem(message, scheduler);
                }
                else{

                    System.out.println(message);
                    final String youtubeUrl = UrlManager.constructYoutubeUri(youtubeHelper.getResults(message,1).get(0).getCode());
                    playerManager.loadItem(youtubeUrl,scheduler);
                }
            }
            else{
                event.getMessage().addReaction(ReactionEmoji.unicode("\u274C")).block();
            }
        }
        catch (NullPointerException ignored){
            event.getMessage().getChannel().block().createMessage("Error, no puede usar este comando sin estar en un canal").block();
        }
        catch (IndexOutOfBoundsException  ignored){
            event.getMessage().getChannel().block().createMessage("Error, cantidad de parametros incorrecta").block();
        }
        catch( FriendlyException friendlyException){
            event.getMessage().getChannel().block().createMessage("Algo se rompio relacionado al reproductor, vuelva a intentar").block();
        }
    }
}
