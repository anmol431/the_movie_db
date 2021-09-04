package com.themovie.db.app.view;

import static android.nfc.tech.MifareUltralight.PAGE_SIZE;
import static com.themovie.db.app.constants.Constants.MOVIE_DETAILS;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.themovie.db.app.R;
import com.themovie.db.app.databinding.ActivityMainBinding;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.room.MovieDB;
import com.themovie.db.app.view.adapter.MoviesAdapter;
import com.themovie.db.app.view_model.AllMoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.OnItemClickListener {
    private final ArrayList<MoviesDTO> moviesDTOS = new ArrayList<>();
    boolean doubleBackToExit = false;
    private ActivityMainBinding binding;
    private AllMoviesViewModel viewModel;
    private int currentPage = 1;
    private int totalPages;
    private String category;
    private boolean mIsLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        binding.setLifecycleOwner(this);
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AllMoviesViewModel.class);

        binding.srlMovies.setOnRefreshListener(() -> {
            moviesDTOS.clear();
            binding.rcvMovies.setAdapter(null);
            currentPage = 1;
            getMovieList();
        });

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        binding.rcvMovies.setLayoutManager(layoutManager);
        binding.rcvMovies.addOnScrollListener(onScrollLoadList(layoutManager));

        setUpSpinner();
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

                moviesDTOS.clear();
                binding.rcvMovies.setAdapter(null);
                category = parent.getItemAtPosition(position).toString();
                currentPage = 1;
                getMovieList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getMovieList() {
        if (isNetworkAvailable()) {
            binding.srlMovies.setRefreshing(true);

            if (category.equals(getString(R.string.popular_text))) {
                viewModel.getPopularMovies(currentPage);
            } else if (category.equals(getString(R.string.now_playing_text))) {
                viewModel.getNowPlayingMovies(currentPage);
            } else if (category.equals(getString(R.string.up_coming_text))) {
                viewModel.getUpcomingMovies(currentPage);
            } else if (category.equals(getString(R.string.top_rated_text))) {
                viewModel.getTopRatedMovies(currentPage);
            }
            setObserver();
        } else {
            moviesDTOS.clear();
            moviesDTOS.addAll(MovieDB.getInstance(this).getMovieList(category));
            if (moviesDTOS.size() > 0) {
                setUpAdapter(moviesDTOS.size());
            }
            binding.srlMovies.setRefreshing(false);
            Toast.makeText(this, getString(R.string.no_network_text), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMovieClick(MoviesDTO moviesDTO) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_DETAILS, moviesDTO);
        startActivity(intent);
    }

    private void setObserver() {
        viewModel.getPopularMovieList().observe(this, response -> {
            if (response != null) {
                totalPages = response.getTotal_pages();
                moviesDTOS.addAll(response.getResults());
                for (MoviesDTO moviesDTO : response.getResults()) {
                    moviesDTO.setCategory(category);
                    MovieDB.getInstance(this).insert(moviesDTO);
                }
                setUpAdapter(response.getResults().size());
            }
            binding.srlMovies.setRefreshing(false);
        });
    }

    private void setUpAdapter(int size) {
        MoviesAdapter adapter = new MoviesAdapter(moviesDTOS, this);
        binding.rcvMovies.setAdapter(adapter);
        binding.rcvMovies.scrollToPosition(moviesDTOS.size() - size);
        mIsLoading = false;
    }

    private RecyclerView.OnScrollListener onScrollLoadList(final LinearLayoutManager mLayoutManager) {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 20) {
                    int visibleItemCount = mLayoutManager.getChildCount();
                    int totalItemCount = mLayoutManager.getItemCount();
                    int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

                    if (!mIsLoading) {
                        if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                                && firstVisibleItemPosition >= 0
                                && totalItemCount > PAGE_SIZE) {
                            if (isNetworkAvailable()) {
                                if (totalPages > currentPage) {
                                    currentPage++;
                                    mIsLoading = true;
                                    getMovieList();
                                }
                            }
                        }
                    }
                }
            }
        };
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
