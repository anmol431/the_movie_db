package com.themovie.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.themovie.db.app.model.StoryDTO;
import com.themovie.db.app.repository.StoryRepository;

import java.util.List;

public class StoryViewModel extends AndroidViewModel {

    private LiveData<List<Integer>> topStoryIds;

    private final StoryRepository storyRepository;

    public StoryViewModel(@NonNull Application application) {
        super(application);
        storyRepository = new StoryRepository();
    }

    public void getStories() {
        topStoryIds = storyRepository.getTopStories(); // fetch top story id's from server
    }

    public LiveData<StoryDTO> getStoryLiveData(int storyId) {
        return storyRepository.getStoryArticle(storyId); // fetch stories from server based on id
    }

    public LiveData<List<Integer>> getStoryIdList() {
        return topStoryIds;
    }
}
