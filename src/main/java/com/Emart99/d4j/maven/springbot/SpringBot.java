package com.Emart99.d4j.maven.springbot;

import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.presence.ClientActivity;
import discord4j.core.object.presence.ClientPresence;
import discord4j.rest.RestClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBot {
    public static void main(String[] args) {
        //Start spring application
        new SpringApplicationBuilder(SpringBot.class)
            .build()
            .run(args);
    }
    @Bean
    public GatewayDiscordClient gatewayDiscordClient() {
        return DiscordClientBuilder.create(System.getenv("DISCORD_TOKEN")).build()
                .gateway()
                .setInitialPresence(ignore -> ClientPresence.online(ClientActivity.playing("\uD83D\uDE17\uD83D\uDC4C\uD83D\uDEAC")))
                .login()
                .block();
    }
    @Bean
    public RestClient discordRestClient(GatewayDiscordClient client) {
        return client.getRestClient();
    }
}
