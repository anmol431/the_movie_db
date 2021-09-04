package com.themovie.db.app.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themovie.db.app.databinding.AdapterReviewsBinding;
import com.themovie.db.app.model.ReviewsDTO;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    ArrayList<ReviewsDTO> reviewsDTOS;

    public ReviewsAdapter(ArrayList<ReviewsDTO> list) {
        this.reviewsDTOS = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterReviewsBinding binding = AdapterReviewsBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setReviewsDTO(reviewsDTOS.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return reviewsDTOS.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterReviewsBinding binding;

        public ViewHolder(AdapterReviewsBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
