package com.movie.felixster.utils;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

// image URL prefix
public class ImageURL {
    public static final String CONFIGURATION_API = "https://api.themoviedb.org/3/configuration?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

    String secureBaseURL;
    String posterSize;

    public ImageURL(){
        buildImageURLPrefix();
    }

    // build image url prefix
    public void buildImageURLPrefix(){
        // sending a GET request to the Configuration API and
        // retrieve the base url and the poster size from the API response
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.get(CONFIGURATION_API, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MainActivity", "onSuccess");
                try {
                    JSONObject imagesJsonObject = json.jsonObject.getJSONObject("images");

                    setSecureBaseURL(imagesJsonObject);
                    Log.i("MainActivity", "Base url: " + getSecureBaseURL());

                    setPosterSize(imagesJsonObject);
                    Log.i("MainActivity", "Poster size: " + getPosterSize());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MainActivity", "onFailure");
            }
        });
    }

    public  String getSecureBaseURL() {
        return secureBaseURL;
    }

    public void setSecureBaseURL(JSONObject jsonObject) throws JSONException {
        secureBaseURL = jsonObject.getString("secure_base_url");
    }

    public String getPosterSize() {
        return posterSize;
    }

    public void setPosterSize(JSONObject jsonObject) throws JSONException {
        JSONArray jsonArray = jsonObject.getJSONArray("poster_sizes");

        for (int i=0; i<jsonArray.length(); i++){
            String size = jsonArray.getString(i);
            if (size.equals("w342")){
                this.posterSize = size;
                break;
            }
        }
    }
}
