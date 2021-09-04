package com.themovie.db.app.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class CreditsDTO implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("cast")
    private ArrayList<CastDTO> cast;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CastDTO> getCast() {
        return cast;
    }

    public void setCast(ArrayList<CastDTO> cast) {
        this.cast = cast;
    }

    @NonNull
    @Override
    public String toString() {
        return "CreditsDTO{" +
                "id=" + id +
                ", cast=" + cast +
                '}';
    }
}
