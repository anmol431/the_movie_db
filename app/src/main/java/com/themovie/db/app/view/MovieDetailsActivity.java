package com.themovie.db.app.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.themovie.db.app.databinding.ActivityMainBinding;
import com.themovie.db.app.databinding.ActivityMovieDetailsBinding;
import com.themovie.db.app.view_model.AllMoviesViewModel;
import com.themovie.db.app.view_model.MovieDetailsViewModel;

public class MovieDetailsActivity extends AppCompatActivity {
    private ActivityMovieDetailsBinding binding;
    private MovieDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MovieDetailsViewModel.class);



    }
}
