package com.themovie.db.app.model;

import static com.themovie.db.app.constants.Constants.IMAGE_URL;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MoviesDTO implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("adult")
    private boolean adult;

    @SerializedName("backdrop_path")
    private String backdrop_path;

    @SerializedName("original_language")
    private String original_language;

    @SerializedName("original_title")
    private String original_title;

    @SerializedName("overview")
    private String overview;

    @SerializedName("popularity")
    private double popularity;

    @SerializedName("poster_path")
    private String poster_path;

    @SerializedName("release_date")
    private String release_date;

    @SerializedName("title")
    private String title;

    @SerializedName("video")
    private boolean video;

    @SerializedName("vote_average")
    private float vote_average;

    @SerializedName("vote_count")
    private int vote_count;

    @SerializedName("genre_ids")
    private ArrayList<Integer> genre_ids;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public float getVote_average() {
        return vote_average;
    }

    public void setVote_average(float vote_average) {
        this.vote_average = vote_average;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public ArrayList<Integer> getGenre_ids() {
        return genre_ids;
    }

    public void setGenre_ids(ArrayList<Integer> genre_ids) {
        this.genre_ids = genre_ids;
    }

    @NonNull
    @Override
    public String toString() {
        return "MoviesDTO{" +
                "id=" + id +
                ", adult=" + adult +
                ", backdrop_path='" + backdrop_path + '\'' +
                ", original_language='" + original_language + '\'' +
                ", original_title='" + original_title + '\'' +
                ", overview='" + overview + '\'' +
                ", popularity=" + popularity +
                ", poster_path='" + poster_path + '\'' +
                ", release_date='" + release_date + '\'' +
                ", title='" + title + '\'' +
                ", video=" + video +
                ", vote_average=" + vote_average +
                ", vote_count=" + vote_count +
                ", genre_ids=" + genre_ids +
                '}';
    }

    public String convertServerDateToUi(String requestedDate) {
        String SERVER_FORMAT = "yyyy-MM-dd";
        String UI_FORMAT = "MMM dd, yyyy";
        if (requestedDate == null) {
            return "-";
        }
        SimpleDateFormat sourceFormat = new SimpleDateFormat(SERVER_FORMAT, Locale.getDefault());
        SimpleDateFormat destFormat = new SimpleDateFormat(UI_FORMAT, Locale.getDefault());
        Date date = null;
        try {
            date = sourceFormat.parse(requestedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date != null) {
            return destFormat.format(date);
        }
        return requestedDate;
    }

    public int getVotes(float vote) {
        return (int) (vote * 10);
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        String url = IMAGE_URL + imageUrl;
        Glide.with(view.getContext()).load(url).into(view);
    }
}
