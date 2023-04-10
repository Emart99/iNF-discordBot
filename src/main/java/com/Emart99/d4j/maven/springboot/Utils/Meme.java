package com.Emart99.d4j.maven.springboot.Utils;

import java.util.List;

public class Meme {

    private String postLink;
    private String subreddit;
    private String title;
    private String url;
    private Boolean nswf;
    private Boolean spoiler;
    private String author;
    private Integer ups;
    private List<String> preview;

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getNswf() {
        return nswf;
    }

    public void setNswf(Boolean nswf) {
        this.nswf = nswf;
    }

    public Boolean getSpoiler() {
        return spoiler;
    }

    public void setSpoiler(Boolean spoiler) {
        this.spoiler = spoiler;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        author = author;
    }

    public List<String> getPreview() {
        return preview;
    }

    public void setPreview(List<String> preview) {
        this.preview = preview;
    }
    public Integer getUps() { return ups; }
    public void setUps(Integer ups) { this.ups = ups; }
}
