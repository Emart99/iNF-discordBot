package com.Emart99.d4j.maven.springboot.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlManager {
    public static Boolean verifyUrl(String string){
        String urlRegex = "((http:\\/\\/|https:\\/\\/)?(www.)?(([a-zA-Z0-9-]){2,}\\.){1,4}([a-zA-Z]){2,6}(\\/([a-zA-Z-_\\/\\.0-9#:?=&;,]*)?)?)";
        Pattern pattern = Pattern.compile(urlRegex);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }
    public static String constructYoutubeUri(String youtubeId){
        return "https://www.youtube.com/watch?v="+youtubeId;
    }
}
