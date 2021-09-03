package com.themovie.db.app.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themovie.db.app.databinding.AdapterGenreBinding;
import com.themovie.db.app.model.GenresDetailsDTO;

import java.util.ArrayList;

public class GenreAdapter extends RecyclerView.Adapter<GenreAdapter.ViewHolder> {
    ArrayList<GenresDetailsDTO> genresDetailsDTOS;
    OnItemClickListener listener;

    public GenreAdapter(ArrayList<GenresDetailsDTO> list, OnItemClickListener clickListener) {
        this.genresDetailsDTOS = list;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterGenreBinding binding = AdapterGenreBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setGenresDto(genresDetailsDTOS.get(position));
        holder.binding.setListener(listener);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return genresDetailsDTOS.size();
    }

    public interface OnItemClickListener {
        void onGenreClick(GenresDetailsDTO genresDetailsDTO);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterGenreBinding binding;

        public ViewHolder(AdapterGenreBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
