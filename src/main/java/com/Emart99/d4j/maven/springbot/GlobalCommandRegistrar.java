package com.Emart99.d4j.maven.springbot;

import com.Emart99.d4j.maven.springbot.audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springbot.audio.LavaPlayerAudioProvider;
import com.Emart99.d4j.maven.springbot.audio.TrackScheduler;
import com.Emart99.d4j.maven.springbot.commands.Command;
import com.Emart99.d4j.maven.springbot.commands.complexAudioCommands.*;
import com.Emart99.d4j.maven.springbot.commands.informativeCommands.HelpCommand;
import com.Emart99.d4j.maven.springbot.commands.scheduledSimpleCommands.NosVamosLevanthunderCommand;
import com.Emart99.d4j.maven.springbot.commands.simpleAudioCommands.PauseCommand;
import com.Emart99.d4j.maven.springbot.commands.simpleAudioCommands.ResumeCommand;
import com.Emart99.d4j.maven.springbot.commands.simpleAudioCommands.StopCommand;
import com.Emart99.d4j.maven.springbot.commands.simpleTextCommands.*;
import com.Emart99.d4j.maven.springbot.listeners.CommandListener;
import com.Emart99.d4j.maven.springbot.utils.ScheduledPing;
import com.Emart99.d4j.maven.springbot.utils.YoutubeSearch;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.RestClient;
import discord4j.voice.AudioProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;

@Component
public class GlobalCommandRegistrar implements ApplicationRunner {
    private static final HashMap<String,Command> commands = new HashMap<>();
    private final RestClient client;
    private final GatewayDiscordClient discordClient;
    public GlobalCommandRegistrar(RestClient client, GatewayDiscordClient discordClient) {
        this.client = client;
        this.discordClient = discordClient;
    }
    //Este metodo corre on init (inicializa toda la app).
    @Override
    public void run(ApplicationArguments args) throws IOException {
        final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        AudioSourceManagers.registerRemoteSources(playerManager);
        final AudioPlayer player = playerManager.createPlayer();
        AudioProvider provider = new LavaPlayerAudioProvider(player);
        TrackScheduler scheduler = new TrackScheduler(player);
        player.addListener(scheduler);
        final AudioLoadResultHandlerImplementation audioLoadResult = new AudioLoadResultHandlerImplementation(player,scheduler);
        YoutubeSearch youtubeHelper = new YoutubeSearch(System.getenv("YOUTUBE_APIKEY"));

        commands.put("meme", new MemeCommand());
        commands.put("baldu", new BalduCommand());
        commands.put("godno",new GodNoCommand());
        commands.put("perown",new PeroWnCommand());
        commands.put("help",new HelpCommand());
        commands.put("porro",new PorroCommand());
        commands.put("cf",new CoinflipCommand());
        commands.put("cf2",new CoinflipCommandV2());
        commands.put("pause",new PauseCommand(player));
        commands.put("resume",new ResumeCommand(player));
        commands.put("stop",new StopCommand(player, discordClient,audioLoadResult));
        commands.put("play",new PlayCommand(provider,playerManager,audioLoadResult,youtubeHelper));
        commands.put("skip",new SkipCommand(provider,playerManager,audioLoadResult));
        commands.put("seek",new SeekCommand(provider,playerManager,audioLoadResult));
        commands.put("hitbenha",new HitBenhaCommand(provider,playerManager,audioLoadResult));
        commands.put("sanguchoto",new SanguchotoCommand(provider,playerManager,audioLoadResult));
        commands.put("aceitedecoco",new AceiteDeCocoCommand(provider,playerManager,audioLoadResult));

        new NosVamosLevanthunderCommand().execute(discordClient);
        ScheduledPing.pingHeroku();

        final CommandListener listener = new CommandListener(commands,discordClient);
        listener.handle();
    }
}

