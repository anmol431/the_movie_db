package com.themovie.db.app.view;

import static com.themovie.db.app.constants.Constants.MOVIE_DETAILS;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.themovie.db.app.R;
import com.themovie.db.app.databinding.ActivityMovieDetailsBinding;
import com.themovie.db.app.model.CastDTO;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.model.ReviewsDTO;
import com.themovie.db.app.room.MovieDB;
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
    }

    private void setUpListener() {
        binding.tvBack.setOnClickListener(v -> onBackPressed());
    }

    private void setUpView() {
        if (isNetworkAvailable()) {
            viewModel.getMoviesData(moviesDTO.getId());
            setObserver();
        } else {
            moviesDTO = MovieDB.getInstance(this).getMovie(moviesDTO.getId());
            if (moviesDTO != null) {
                binding.setMoviesDTO(moviesDTO);
            }
            castDTOS.clear();
            castDTOS.addAll(MovieDB.getInstance(this).getCastList(moviesDTO.getId()));
            setCastAdapter();

            reviewsDTO.clear();
            reviewsDTO.addAll(MovieDB.getInstance(this).getReviewList(moviesDTO.getId()));
            setReviewAdapter();
            Toast.makeText(this, getString(R.string.no_network_text), Toast.LENGTH_SHORT).show();
        }
    }

    private void setObserver() {
        viewModel.getMovieDetails().observe(this, response -> {
            if (response != null) {
                response.setM_genres(response.getMovieGenres(response.getM_genres()));
                MovieDB.getInstance(this).insert(response);
                binding.setMoviesDTO(response);
            }
        });

        viewModel.getCreditsDetails().observe(this, credits -> {
            if (credits != null) {
                castDTOS.clear();
                castDTOS.addAll(credits.getCast());
                if (castDTOS.size() > 0) {
                    for (CastDTO castDTO : castDTOS) {
                        castDTO.setMovieId(moviesDTO.getId());
                        MovieDB.getInstance(this).insertCast(castDTO);
                    }
                }
                setCastAdapter();
            }
        });

        viewModel.getReviewsData().observe(this, reviewsResponse -> {
            if (reviewsResponse != null) {
                reviewsDTO.clear();
                reviewsDTO.addAll(reviewsResponse.getResults());
                if (reviewsDTO.size() > 0) {
                    for (ReviewsDTO reviewsDTO : reviewsDTO) {
                        reviewsDTO.setMovie_id(moviesDTO.getId());
                        reviewsDTO.setReview_date(reviewsDTO.convertServerDateToUi(reviewsDTO.getReview_date()));
                        MovieDB.getInstance(this).insertReview(reviewsDTO);
                    }
                }
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

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
