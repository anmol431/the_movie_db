package com.themovie.db.app.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class ReviewsBaseDTO implements Serializable {
    @SerializedName("id")
    public int id;

    @SerializedName("results")
    public ArrayList<ReviewsDTO> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<ReviewsDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ReviewsDTO> results) {
        this.results = results;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReviewsBaseDTO{" +
                "id=" + id +
                ", results=" + results +
                '}';
    }
}
