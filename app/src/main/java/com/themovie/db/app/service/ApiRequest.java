package com.themovie.db.app.service;

import com.themovie.db.app.model.GenreDTO;
import com.themovie.db.app.model.StoryDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("movie/popular")
    Call<GenreDTO> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);


    @GET("item/{storyId}.json?print=pretty")
    Call<StoryDTO> getStory(@Path("storyId") int storyId);
}
