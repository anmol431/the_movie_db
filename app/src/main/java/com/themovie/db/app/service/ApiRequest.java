package com.themovie.db.app.service;

import com.themovie.db.app.model.ApiResponseDTO;
import com.themovie.db.app.model.StoryDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("movie/popular")
    Call<ApiResponseDTO> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

}
