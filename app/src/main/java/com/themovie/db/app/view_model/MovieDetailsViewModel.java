package com.themovie.db.app.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.themovie.db.app.model.CreditsDTO;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.model.ReviewsBaseDTO;
import com.themovie.db.app.repository.MovieDetailsRepository;

public class MovieDetailsViewModel extends AndroidViewModel {

    private final MovieDetailsRepository moviesRepository;
    private LiveData<MoviesDTO> popularMoviesResponseDto;
    private LiveData<CreditsDTO> creditsDTOLiveData;
    private LiveData<ReviewsBaseDTO> reviewsBaseDTOLiveData;

    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MovieDetailsRepository();
    }

    public void getMoviesData(int id) {
        popularMoviesResponseDto = moviesRepository.getMovieDetails(id);
        creditsDTOLiveData = moviesRepository.getCast(id);
        reviewsBaseDTOLiveData = moviesRepository.getReviews(id);
    }

    public LiveData<MoviesDTO> getMovieDetails() {
        return popularMoviesResponseDto;
    }

    public LiveData<CreditsDTO> getCreditsDetails() {
        return creditsDTOLiveData;
    }

    public LiveData<ReviewsBaseDTO> getReviewsData() {
        return reviewsBaseDTOLiveData;
    }
}
