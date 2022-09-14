package com.Emart99.d4j.maven.springbot.commands.scheduledSimpleCommands;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class NosVamosLevanthunderCommand {
    public void execute(GatewayDiscordClient client){
        Timer timer = new Timer();
        Calendar date = Calendar.getInstance();
        date.set(Calendar.HOUR_OF_DAY, Integer.parseInt(System.getenv("LEVANTHUNDERHOUR")));
        date.set(Calendar.MINUTE,0);
        date.set(Calendar.SECOND,0);
        date.set(Calendar.MILLISECOND,0);
        MessageChannel textChannel = client.getChannelById(Snowflake.of(442868635711832068L)).cast(TextChannel.class).block();
        Long twitIconId = 689959593698394198L;
        ReactionEmoji twitIconReaction = ReactionEmoji.of(twitIconId,"twitchicono",false);
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        textChannel.createMessage("nos vamo levanthunder <:twitchicono:689959593698394198>").block().addReaction(twitIconReaction).block();
                    }
                },
                date.getTime(),
                1000 * 60 * 60 * 24
        );
        System.out.println(date.getTime());
    }
}
