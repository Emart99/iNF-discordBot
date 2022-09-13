package com.Emart99.d4j.maven.springbot.utils;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import java.io.IOException;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class YoutubeSearch {
    private final YouTube.Search.List search;
    private final Queue<String> keyQueue;
    private volatile boolean hasValidKey = true;

    public YoutubeSearch(String youtubeApiKey) {
        keyQueue = new LinkedList<>();
        Collections.addAll(keyQueue, youtubeApiKey);
        YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), (HttpRequest request) -> {
        }).setApplicationName("iNF-bot").build();
        YouTube.Search.List tmp = null;
        try {
            tmp = youtube.search().list(Collections.singletonList("id,snippet"));
            tmp.setOrder("relevance");
            tmp.setVideoCategoryId("10");

        } catch (IOException ex) {
            System.out.println("Failed to initialize");
        }

        search = tmp;
        if (search != null) {
            search.setType(Collections.singletonList("video"));
            search.setFields("items(id/kind,id/videoId,snippet/title)");
        }
        setupNextKey();
    }

    private synchronized boolean setupNextKey() {
        if (keyQueue.size() > 0) {
            String key = keyQueue.poll();
            if (key != null) {
                search.setKey(key);
                hasValidKey = true;
                return true;
            }
        }
        hasValidKey = false;
        return false;
    }

    public List<SimpleResult> getResults(String query, int numresults) {
        List<SimpleResult> urls = new ArrayList<>();
        search.setQ(query);
        search.setMaxResults((long) numresults);

        SearchListResponse searchResponse;
        try {
            searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            searchResultList.forEach((sr) -> urls.add(new SimpleResult(sr.getId().getVideoId(), sr.getSnippet().getTitle())));
        } catch (GoogleJsonResponseException e) {
            if (e.getMessage().contains("quotaExceeded") || e.getMessage().contains("keyInvalid")) {
                if (setupNextKey()) {
                    return getResults(query, numresults);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error");
            return null;
        }
        return urls;
    }
    public static class SimpleResult {
        private final String code;
        private final String title;

        public SimpleResult(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public String getCode() {
            return code;
        }
    }
}