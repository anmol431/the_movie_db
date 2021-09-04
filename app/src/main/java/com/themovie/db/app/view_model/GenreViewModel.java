package com.themovie.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.themovie.db.app.model.ApiResponseDTO;
import com.themovie.db.app.repository.MoviesRepository;

public class GenreViewModel extends AndroidViewModel {

    private final MoviesRepository moviesRepository;
    private LiveData<ApiResponseDTO> popularMoviesResponseDto;

    public GenreViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository();
    }

    public void getPopularMovies(int page) {
        popularMoviesResponseDto = moviesRepository.getPopularMovies(page); // fetch genre from server
    }

    public LiveData<ApiResponseDTO> getPopularMovieList() {
        return popularMoviesResponseDto;
    }
}
