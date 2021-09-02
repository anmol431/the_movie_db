package com.themovie.db.app.service;

import com.themovie.db.app.model.StoryDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiRequest {

    @GET("topstories.json?print=pretty")
    Call<List<Integer>> getTopStories();

    @GET("item/{storyId}.json?print=pretty")
    Call<StoryDTO> getStory(@Path("storyId") int storyId);
}
