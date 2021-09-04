package com.themovie.db.app.view;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.themovie.db.app.R;
import com.themovie.db.app.databinding.ActivityMainBinding;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.view.adapter.MoviesAdapter;
import com.themovie.db.app.view_model.AllMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickListener {
    private final ArrayList<MoviesDTO> moviesDTOS = new ArrayList<>();
    boolean doubleBackToExit = false;
    private ActivityMainBinding binding;
    private AllMoviesViewModel viewModel;
    private MoviesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AllMoviesViewModel.class);

        binding.srlGenre.setOnRefreshListener(() -> {
            viewModel.getPopularMovies(1);
            binding.srlGenre.setRefreshing(false);
        });

        setUpSpinner();
        setUpAdapter();

      //  new Handler().postDelayed(this::setObserver, 100);
    }

    private void setUpSpinner() {
        List<String> categories = new ArrayList<>();
        categories.add(getString(R.string.popular_text));
        categories.add(getString(R.string.now_playing_text));
        categories.add(getString(R.string.up_coming_text));
        categories.add(getString(R.string.top_rated_text));

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spHeader.setAdapter(dataAdapter);

        binding.spHeader.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(18);

                String category = parent.getItemAtPosition(position).toString();
                getMovieList(category);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getMovieList(String category) {
        moviesDTOS.clear();
        binding.srlGenre.setRefreshing(true);
        viewModel.getPopularMovies(1);

        setObserver();
    }

    private void setUpAdapter() { // Top story adapter
        adapter = new MoviesAdapter(moviesDTOS, this);
        binding.rcvGenre.setAdapter(adapter);
    }

    @Override
    public void onMovieClick(int id) {

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setObserver() {
        viewModel.getPopularMovieList().observe(this, response -> {
            if (response != null) {
                moviesDTOS.addAll(response.getResults());
                adapter.notifyDataSetChanged();
                binding.srlGenre.setRefreshing(false);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExit) {
            finishAndRemoveTask();
            finishAffinity();
            return;
        }
        doubleBackToExit = true;
        Toast.makeText(this, getString(R.string.exit_text), Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExit = false, 5000);
    }
}
