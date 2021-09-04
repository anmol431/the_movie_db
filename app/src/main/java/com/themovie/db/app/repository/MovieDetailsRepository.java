package com.themovie.db.app.repository;

import static com.themovie.db.app.constants.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.themovie.db.app.model.CreditsDTO;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.model.ReviewsBaseDTO;
import com.themovie.db.app.service.ApiRequest;
import com.themovie.db.app.service.RetrofitRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsRepository {
    private final ApiRequest apiRequest;

    public MovieDetailsRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<MoviesDTO> getMovieDetails(int id) {
        final MutableLiveData<MoviesDTO> responseDto = new MutableLiveData<>();
        apiRequest.getMovieDetails(id, API_KEY).enqueue(new Callback<MoviesDTO>() {
            @Override
            public void onResponse(@NonNull Call<MoviesDTO> call, @NonNull Response<MoviesDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<MoviesDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }

    public MutableLiveData<CreditsDTO> getCast(int id) {
        final MutableLiveData<CreditsDTO> responseDto = new MutableLiveData<>();
        apiRequest.getCast(id, API_KEY).enqueue(new Callback<CreditsDTO>() {
            @Override
            public void onResponse(@NonNull Call<CreditsDTO> call, @NonNull Response<CreditsDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<CreditsDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }

    public MutableLiveData<ReviewsBaseDTO> getReviews(int id) {
        final MutableLiveData<ReviewsBaseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getReviews(id, API_KEY, 1).enqueue(new Callback<ReviewsBaseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ReviewsBaseDTO> call, @NonNull Response<ReviewsBaseDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ReviewsBaseDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }
}
