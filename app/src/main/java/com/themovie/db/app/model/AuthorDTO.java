package com.themovie.db.app.model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AuthorDTO implements Serializable {
    @SerializedName("username")
    private String username;

    @SerializedName("avatar_path")
    private String avatar_path;

    @SerializedName("rating")
    private float rating;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_path() {
        return avatar_path;
    }

    public void setAvatar_path(String avatar_path) {
        this.avatar_path = avatar_path;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthorDTO{" +
                "username='" + username + '\'' +
                ", avatar_path='" + avatar_path + '\'' +
                ", rating=" + rating +
                '}';
    }
}
