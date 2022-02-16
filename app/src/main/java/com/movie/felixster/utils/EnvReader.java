package com.movie.felixster.utils;

public class EnvReader {

    public static final String URL_NOW_PLAYING = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String YOUTUBE_API_KEY = "AIzaSyD0_t9POZb-IS5ZtmfAarf4kYUiWC7gK_U";
    private static final String VIDEOS_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    public static String getUrlNowPlaying(){
        return URL_NOW_PLAYING;
    }

    public static String getYoutubeApiKey(){
        return YOUTUBE_API_KEY;
    }

    public static String getVideosUrl(){
        return VIDEOS_URL;
    }
}
