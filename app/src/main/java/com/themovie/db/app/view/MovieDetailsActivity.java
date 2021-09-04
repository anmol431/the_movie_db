package com.themovie.db.app.view;

import static com.themovie.db.app.constants.Constants.MOVIE_DETAILS;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.themovie.db.app.databinding.ActivityMovieDetailsBinding;
import com.themovie.db.app.model.CastDTO;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.model.ReviewsDTO;
import com.themovie.db.app.view.adapter.CastingAdapter;
import com.themovie.db.app.view.adapter.ReviewsAdapter;
import com.themovie.db.app.view_model.MovieDetailsViewModel;

import java.util.ArrayList;

public class MovieDetailsActivity extends AppCompatActivity {
    private final ArrayList<CastDTO> castDTOS = new ArrayList<>();
    private final ArrayList<ReviewsDTO> reviewsDTO = new ArrayList<>();
    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;
    private MoviesDTO moviesDTO = new MoviesDTO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);

        moviesDTO = (MoviesDTO) getIntent().getSerializableExtra(MOVIE_DETAILS);
        binding.setMoviesDTO(moviesDTO);

        setUpView();
        setUpListener();
        setObserver();
    }

    private void setUpListener() {
        binding.tvBack.setOnClickListener(v -> onBackPressed());
    }

    private void setUpView() {
        viewModel.getMoviesData(moviesDTO.getId());
    }

    private void setObserver() {
        viewModel.getMovieDetails().observe(this, response -> {
            if (response != null) {
                binding.setMoviesDTO(response);
            }
        });

        viewModel.getCreditsDetails().observe(this, credits -> {
            if (credits != null) {
                castDTOS.clear();
                castDTOS.addAll(credits.getCast());
                setCastAdapter();
            }
        });

        viewModel.getReviewsData().observe(this, reviewsResponse -> {
            if (reviewsResponse != null) {
                reviewsDTO.clear();
                reviewsDTO.addAll(reviewsResponse.getResults());
                binding.setSize(reviewsDTO.size());
                setReviewAdapter();
            }
        });
    }

    private void setCastAdapter() {
        CastingAdapter castingAdapter = new CastingAdapter(castDTOS);
        binding.rcvCast.setAdapter(castingAdapter);
    }

    private void setReviewAdapter() {
        ReviewsAdapter reviewsAdapter = new ReviewsAdapter(reviewsDTO);
        binding.rcvReviews.setAdapter(reviewsAdapter);
    }
}
