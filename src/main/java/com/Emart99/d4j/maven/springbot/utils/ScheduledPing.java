package com.Emart99.d4j.maven.springbot.utils;

import java.io.IOException;
import java.net.*;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ScheduledPing {
    public static void pingHeroku() throws IOException {
        Timer timer = new Timer();
        timer.schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            HttpURLConnection connection = (HttpURLConnection) new URL("https://inf-bot-final.herokuapp.com/".replaceFirst("^https", "http")).openConnection();
                            connection.setConnectTimeout(3000);
                            connection.setReadTimeout(3000);
                            connection.setRequestMethod("GET");
                            connection.getResponseCode();
                            connection.disconnect();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                },
                Calendar.getInstance().getTime(),
                (15*60000)
        );
    }
}
