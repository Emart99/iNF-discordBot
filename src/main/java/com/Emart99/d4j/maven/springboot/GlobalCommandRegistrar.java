package com.Emart99.d4j.maven.springboot;

import com.Emart99.d4j.maven.springboot.Audio.AudioLoadResultHandlerImplementation;
import com.Emart99.d4j.maven.springboot.Audio.LavaPlayerAudioProvider;
import com.Emart99.d4j.maven.springboot.Audio.TrackScheduler;
import com.Emart99.d4j.maven.springboot.Commands.Command;
import com.Emart99.d4j.maven.springboot.Commands.ComplexAudioCommands.*;
import com.Emart99.d4j.maven.springboot.Commands.SimpleTextCommands.*;
import com.Emart99.d4j.maven.springboot.Commands.InformativeCommands.HelpCommand;
import com.Emart99.d4j.maven.springboot.Commands.SimpleAudioCommands.PauseCommand;
import com.Emart99.d4j.maven.springboot.Commands.SimpleAudioCommands.ResumeCommand;
import com.Emart99.d4j.maven.springboot.Commands.SimpleAudioCommands.StopCommand;
import com.Emart99.d4j.maven.springboot.Listeners.BotLeavingListener;
import com.Emart99.d4j.maven.springboot.Listeners.CommandListener;
import com.Emart99.d4j.maven.springboot.Utils.GptApiConsumer;
import com.Emart99.d4j.maven.springboot.Utils.MemeApiConsumer;
import com.Emart99.d4j.maven.springboot.Utils.YoutubeSearch;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.playback.NonAllocatingAudioFrameBuffer;
import com.theokanning.openai.service.OpenAiService;
import discord4j.core.GatewayDiscordClient;
import discord4j.rest.RestClient;
import discord4j.voice.AudioProvider;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

@Component
public class GlobalCommandRegistrar implements ApplicationRunner {
    private static final HashMap<String, Command> commands = new HashMap<>();
    private final RestClient client;
    private final GatewayDiscordClient discordClient;
    public GlobalCommandRegistrar(RestClient client, GatewayDiscordClient discordClient) {
        this.client = client;
        this.discordClient = discordClient;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
        // This is an optimization strategy that Discord4J can utilize.
        // It is not important to understand
        playerManager.getConfiguration().setFrameBufferFactory(NonAllocatingAudioFrameBuffer::new);
        // Allow playerManager to parse remote sources like YouTube links
        AudioSourceManagers.registerRemoteSources(playerManager);
        // Create an AudioPlayer so Discord4J can receive audio data
        final AudioPlayer player = playerManager.createPlayer();
        // We will be creating LavaPlayerAudioProvider in the next step
        AudioProvider provider = new LavaPlayerAudioProvider(player);
        TrackScheduler scheduler = new TrackScheduler(player, discordClient);
        player.addListener(scheduler);
        final AudioLoadResultHandlerImplementation audioLoadResult = new AudioLoadResultHandlerImplementation(player,scheduler);
        OpenAiService aiService = new OpenAiService(System.getenv("OPENAI_APIKEY"), Duration.ofSeconds(300));
        GptApiConsumer gptApiConsumer = new GptApiConsumer(aiService);
        MemeApiConsumer memeApiConsumer = new MemeApiConsumer();
        YoutubeSearch youtubeHelper = new YoutubeSearch(System.getenv("YOUTUBE_APIKEY"));

        commands.put("play", new PlayCommand(provider,playerManager,audioLoadResult,youtubeHelper));
        commands.put("repeat", new RepeatCommand(provider,playerManager,audioLoadResult,youtubeHelper));
        commands.put("skip", new SkipCommand(provider,playerManager,audioLoadResult));
        commands.put("seek", new SeekCommand(provider,playerManager,audioLoadResult));
        commands.put("shuffle", new ShuffleCommand(provider,playerManager,audioLoadResult));
        commands.put("stop", new StopCommand(player,discordClient,audioLoadResult));
        commands.put("pause", new PauseCommand(player));
        commands.put("resume", new ResumeCommand(player));
        commands.put("help", new HelpCommand());
        commands.put("porro", new PorroCommand());
        commands.put("perown", new PeroWnCommand());
        commands.put("godno", new GodNoCommand());
        commands.put("baldu", new BalduCommand());
        commands.put("fuckthat", new FuckThatCommand());
        commands.put("coinflip", new CoinflipCommand());
        commands.put("meme", new MemeCommand(memeApiConsumer));
        commands.put("coinflipv2", new CoinflipV2Command());
        commands.put("chatGpt", new GptChatCommand(gptApiConsumer));
        //generateGptImage was disabled due to Token consumption of GptApi.
        //commands.put("generateGptImage",new GptGenerateImageCommand(gptApiConsumer));

        new CommandListener(commands,discordClient).handle();
        new BotLeavingListener(discordClient,player,audioLoadResult).handle();
        //new NosVamosLevanthunderScheduled().execute(discordClient);

    }
}
