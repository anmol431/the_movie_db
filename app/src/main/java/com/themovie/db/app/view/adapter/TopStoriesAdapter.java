package com.themovie.db.app.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themovie.db.app.databinding.AdapterTopStoriesBinding;
import com.themovie.db.app.model.StoryDTO;

import java.util.ArrayList;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesAdapter.ViewHolder> {
    ArrayList<StoryDTO> storyArrayList;
    OnItemClickListener listener;
    int row_index = 0;

    public TopStoriesAdapter(ArrayList<StoryDTO> list, OnItemClickListener clickListener) {
        this.storyArrayList = list;
        this.listener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterTopStoriesBinding binding = AdapterTopStoriesBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setStoryDTO(storyArrayList.get(position));

        holder.binding.clRootView.setOnClickListener(v -> {
            row_index = holder.getBindingAdapterPosition();
            notifyDataSetChanged();
            listener.onStoryClick(storyArrayList.get(position), position);
        });

        holder.binding.setIsSelected(row_index == position); // set background color of selected index

        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return storyArrayList.size();
    }

    public interface OnItemClickListener {
        void onStoryClick(StoryDTO storyDTO, int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterTopStoriesBinding binding;

        public ViewHolder(AdapterTopStoriesBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
