package com.themovie.db.app.repository;

import static com.themovie.db.app.constants.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.themovie.db.app.model.ApiResponseDTO;
import com.themovie.db.app.service.ApiRequest;
import com.themovie.db.app.service.RetrofitRequest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRepository {
    private final ApiRequest apiRequest;

    public MoviesRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<ApiResponseDTO> getPopularMovies(int page) {
        final MutableLiveData<ApiResponseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getPopularMovies(API_KEY, page).enqueue(new Callback<ApiResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseDTO> call, @NonNull Response<ApiResponseDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }

    public MutableLiveData<ApiResponseDTO> getNowPlayingMovies(int page) {
        final MutableLiveData<ApiResponseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getNowPlayingMovies(API_KEY, page).enqueue(new Callback<ApiResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseDTO> call, @NonNull Response<ApiResponseDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }

    public MutableLiveData<ApiResponseDTO> getUpComingMovies(int page) {
        final MutableLiveData<ApiResponseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getUpComingMovies(API_KEY, page).enqueue(new Callback<ApiResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseDTO> call, @NonNull Response<ApiResponseDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }

    public MutableLiveData<ApiResponseDTO> getTopRatedMovies(int page) {
        final MutableLiveData<ApiResponseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getTopRatedMovies(API_KEY, page).enqueue(new Callback<ApiResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseDTO> call, @NonNull Response<ApiResponseDTO> response) {
                responseDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponseDTO> call, @NonNull Throwable t) {
                responseDto.postValue(null);
            }
        });
        return responseDto;
    }
}
