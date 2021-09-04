package com.themovie.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.themovie.db.app.model.ApiResponseDTO;
import com.themovie.db.app.repository.MoviesRepository;

public class AllMoviesViewModel extends AndroidViewModel {

    private final MoviesRepository moviesRepository;
    private LiveData<ApiResponseDTO> popularMoviesResponseDto;

    public AllMoviesViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository();
    }

    public void getPopularMovies(int page) {
        popularMoviesResponseDto = moviesRepository.getPopularMovies(page);
    }

    public void getNowPlayingMovies(int page) {
        popularMoviesResponseDto = moviesRepository.getNowPlayingMovies(page);
    }

    public void getUpcomingMovies(int page) {
        popularMoviesResponseDto = moviesRepository.getUpComingMovies(page);
    }

    public void getTopRatedMovies(int page) {
        popularMoviesResponseDto = moviesRepository.getTopRatedMovies(page);
    }

    public LiveData<ApiResponseDTO> getPopularMovieList() {
        return popularMoviesResponseDto;
    }
}
