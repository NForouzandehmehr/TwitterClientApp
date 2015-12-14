package com.codepath.apps.nftweetsapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by najmeh.f on 12/13/2015.
 */
public class Tweet {
    private String body;
    private long uid;
    private User user;

    public User getUser() {
        return user;
    }

    private String createdAt;
    public static Tweet fromJSON(JSONObject jsonObject){
      Tweet tweet= new Tweet();
        try {
            tweet.body= jsonObject.getString("text");
            tweet.uid=jsonObject.getLong("id");
            tweet.createdAt=jsonObject.getString("created_at");
            tweet.user=User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getBody() {
        return body;
    }
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray){
        ArrayList<Tweet> tweets= new ArrayList<>();
        for (int i=0; i< jsonArray.length(); i++){
            JSONObject tweetJson= null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet=Tweet.fromJSON(tweetJson);
                if(tweet != null){
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return tweets;
    }
}
