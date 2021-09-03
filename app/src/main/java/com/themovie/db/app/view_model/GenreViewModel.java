package com.themovie.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.themovie.db.app.model.GenreDTO;
import com.themovie.db.app.repository.GenreRepository;

public class GenreViewModel extends AndroidViewModel {

    private final GenreRepository genreRepository;
    private LiveData<GenreDTO> genreDTOLiveData;

    public GenreViewModel(@NonNull Application application) {
        super(application);
        genreRepository = new GenreRepository();
    }

    public void getPopularMovies(int page) {
        genreDTOLiveData = genreRepository.getPopularMovies(page); // fetch genre from server
    }

    public LiveData<GenreDTO> getGenreList() {
        return genreDTOLiveData;
    }
}
