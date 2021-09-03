package com.themovie.db.app.repository;

import static com.themovie.db.app.constants.Constants.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.themovie.db.app.model.GenreDTO;
import com.themovie.db.app.model.StoryDTO;
import com.themovie.db.app.service.ApiRequest;
import com.themovie.db.app.service.RetrofitRequest;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreRepository {
    private final ApiRequest apiRequest;

    public GenreRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<GenreDTO> getPopularMovies(int page) {
        final MutableLiveData<GenreDTO> genreDto = new MutableLiveData<>();
        apiRequest.getPopularMovies(API_KEY, page).enqueue(new Callback<GenreDTO>() {
            @Override
            public void onResponse(@NonNull Call<GenreDTO> call, @NonNull Response<GenreDTO> response) {
                System.out.println("raw : "+response.raw());
                System.out.println("body : "+response.body());
               // genreDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<GenreDTO> call, @NonNull Throwable t) {
                genreDto.setValue(null);
            }
        });
        return genreDto;
    }
}
