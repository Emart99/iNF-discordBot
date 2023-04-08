package com.Emart99.d4j.maven.springbot.utils;

import com.google.gson.Gson;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MemeApiConsumer {
    public String getMeme(String _url) throws IOException, ParseException, URISyntaxException, InterruptedException {
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(new URI(_url))
                .build();
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> getResponse = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        Gson gson = new Gson();
        Meme meme = gson.fromJson(getResponse.body(), Meme.class);
        return meme.getUrl();
    }
}