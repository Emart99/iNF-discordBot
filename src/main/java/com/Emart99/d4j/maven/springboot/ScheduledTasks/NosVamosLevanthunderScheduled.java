package com.Emart99.d4j.maven.springboot.ScheduledTasks;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.entity.channel.TextChannel;
import discord4j.core.object.reaction.ReactionEmoji;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class NosVamosLevanthunderScheduled {

    public void execute(GatewayDiscordClient client){
        MessageChannel textChannel = client.getChannelById(Snowflake.of(442868635711832068L)).cast(TextChannel.class).block();
        Long twitIconId = 689959593698394198L;
        ReactionEmoji twitIconReaction = ReactionEmoji.of(twitIconId,"twitchicono",false);

        ZonedDateTime buenosAiresTime =  ZonedDateTime.now(ZoneId.of("America/Buenos_Aires"));
        ZonedDateTime nextRun = buenosAiresTime.withHour(8).withMinute(0).withSecond(0);
        if(buenosAiresTime.compareTo(nextRun) > 0){
            nextRun = nextRun.plusDays(1);
        }
        Duration duration = Duration.between(buenosAiresTime,nextRun);
        Long initialDelay = duration.getSeconds();

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(
                new Runnable() {
                    @Override
                    public void run() {
                        textChannel.createMessage("nos vamo levanthunder <:twitchicono:689959593698394198>").block().addReaction(twitIconReaction).block();
                    }
                }, initialDelay, TimeUnit.DAYS.toSeconds(1), TimeUnit.SECONDS);
    }
}
