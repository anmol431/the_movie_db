package com.themovie.db.app.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themovie.db.app.databinding.AdapterMoviesBinding;
import com.themovie.db.app.model.MoviesDTO;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    ArrayList<MoviesDTO> moviesDTOS;
    OnItemClickListener listener;

    public MoviesAdapter(ArrayList<MoviesDTO> list, OnItemClickListener clickListener) {
        this.moviesDTOS = list;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterMoviesBinding binding = AdapterMoviesBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setMoviesDTO(moviesDTOS.get(position));
        holder.binding.setListener(listener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return moviesDTOS.size();
    }

    public interface OnItemClickListener {
        void onMovieClick(MoviesDTO moviesDTO);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterMoviesBinding binding;

        public ViewHolder(AdapterMoviesBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
