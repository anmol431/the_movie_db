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
import com.themovie.db.app.model.GenresDetailsDTO;
import com.themovie.db.app.view.adapter.GenreAdapter;
import com.themovie.db.app.view_model.GenreViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GenreAdapter.OnItemClickListener {
    private final ArrayList<GenresDetailsDTO> genresDetailsDTOS = new ArrayList<>();
    boolean doubleBackToExit = false;
    private ActivityMainBinding binding;
    private GenreViewModel viewModel;
    private GenreAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(GenreViewModel.class);

        binding.srlGenre.setOnRefreshListener(() -> {
            viewModel.getPopularMovies(1);
            binding.srlGenre.setRefreshing(false);
        });

        setUpSpinner();
        setUpAdapter();
        setObserver();
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
        genresDetailsDTOS.clear();
        binding.srlGenre.setRefreshing(true);
        viewModel.getPopularMovies(1);
    }

    private void setUpAdapter() { // Top story adapter
        adapter = new GenreAdapter(genresDetailsDTOS, this);
        binding.rcvGenre.setAdapter(adapter);
    }

    @Override
    public void onGenreClick(GenresDetailsDTO genresDetailsDTO) {

    }

    @SuppressLint("NotifyDataSetChanged")
    private void setObserver() {
       /* viewModel.getGenreList().observe(this, genreDTO -> {
            if (genreDTO != null) {
                // TopStoryDB.getInstance(this).clear();
                genresDetailsDTOS.addAll(genreDTO.getGenres());
                adapter.notifyDataSetChanged();
                binding.srlGenre.setRefreshing(false);
            }
        });*/
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
