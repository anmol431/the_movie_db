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

public class MovieDetailsRepository {
    private final ApiRequest apiRequest;

    public MovieDetailsRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<ApiResponseDTO> getPopularMovies(int page) {
        final MutableLiveData<ApiResponseDTO> responseDto = new MutableLiveData<>();
        apiRequest.getPopularMovies(API_KEY, page).enqueue(new Callback<ApiResponseDTO>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponseDTO> call, @NonNull Response<ApiResponseDTO> response) {
                System.out.println("raw : "+response.raw());
                System.out.println("body : "+response.body());
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
