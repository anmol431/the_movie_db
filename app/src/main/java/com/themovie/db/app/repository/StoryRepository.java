package com.themovie.db.app.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.themovie.db.app.model.StoryDTO;
import com.themovie.db.app.service.ApiRequest;
import com.themovie.db.app.service.RetrofitRequest;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryRepository {
    private final ApiRequest apiRequest;

    public StoryRepository() {
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
    }

    public MutableLiveData<List<Integer>> getTopStories() {
        final MutableLiveData<List<Integer>> topStories = new MutableLiveData<>();
        apiRequest.getTopStories().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Integer>> call, @NonNull Response<List<Integer>> response) {
                topStories.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Integer>> call, @NonNull Throwable t) {
                topStories.setValue(Collections.emptyList());
            }
        });
        return topStories;
    }

    public LiveData<StoryDTO> getStoryArticle(int storyId) {
        final MutableLiveData<StoryDTO> storyDto = new MutableLiveData<>();
        apiRequest.getStory(storyId).enqueue(new Callback<StoryDTO>() {
            @Override
            public void onResponse(@NonNull Call<StoryDTO> call, @NonNull Response<StoryDTO> response) {
                storyDto.postValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<StoryDTO> call, @NonNull Throwable t) {
                storyDto.setValue(null);
            }
        });
        return storyDto;
    }
}
