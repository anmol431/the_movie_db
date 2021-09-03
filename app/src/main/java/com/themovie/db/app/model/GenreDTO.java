package com.themovie.db.app.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class GenreDTO implements Serializable {
    @SerializedName("genres")
    private ArrayList<GenresDetailsDTO> genres;

    public ArrayList<GenresDetailsDTO> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<GenresDetailsDTO> genres) {
        this.genres = genres;
    }

    @NonNull
    @Override
    public String toString() {
        return "GenreDTO{" +
                "genres=" + genres +
                '}';
    }
}
