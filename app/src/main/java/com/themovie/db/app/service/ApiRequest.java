package com.themovie.db.app.service;

import com.themovie.db.app.model.ApiResponseDTO;
import com.themovie.db.app.model.CreditsDTO;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.model.ReviewsBaseDTO;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("movie/popular")
    Call<ApiResponseDTO> getPopularMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/now_playing")
    Call<ApiResponseDTO> getNowPlayingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/top_rated")
    Call<ApiResponseDTO> getTopRatedMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/upcoming")
    Call<ApiResponseDTO> getUpComingMovies(@Query("api_key") String api_key, @Query("page") int page);

    @GET("movie/{movie_id}")
    Call<MoviesDTO> getMovieDetails(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/credits?")
    Call<CreditsDTO> getCast(@Path("movie_id") int movie_id, @Query("api_key") String api_key);

    @GET("movie/{movie_id}/reviews?")
    Call<ReviewsBaseDTO> getReviews(@Path("movie_id") int movie_id, @Query("api_key") String api_key,
                                    @Query("page") int page);

}
