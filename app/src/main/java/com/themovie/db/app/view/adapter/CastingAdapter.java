package com.themovie.db.app.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.themovie.db.app.databinding.AdapterCastingBinding;
import com.themovie.db.app.model.CastDTO;

import java.util.ArrayList;

public class CastingAdapter extends RecyclerView.Adapter<CastingAdapter.ViewHolder> {
    ArrayList<CastDTO> castDTOS;

    public CastingAdapter(ArrayList<CastDTO> list) {
        this.castDTOS = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        AdapterCastingBinding binding = AdapterCastingBinding
                .inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.setCastDTO(castDTOS.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return castDTOS.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public AdapterCastingBinding binding;

        public ViewHolder(AdapterCastingBinding itemRowBinding) {
            super(itemRowBinding.getRoot());
            this.binding = itemRowBinding;
        }
    }
}
