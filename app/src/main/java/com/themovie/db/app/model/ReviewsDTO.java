package com.themovie.db.app.model;

import static com.themovie.db.app.constants.Constants.IMAGE_URL;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ReviewsDTO implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("movie_id")
    private int movie_id;

    @SerializedName("author_details")
    private AuthorDTO author_details;

    @SerializedName("created_at")
    private Timestamp created_at;

    @SerializedName("review_date")
    private String review_date;

    public String getReview_date() {
        return review_date;
    }

    public void setReview_date(String review_date) {
        this.review_date = review_date;
    }

    @BindingAdapter({"avatar"})
    public static void loadAvatar(ImageView view, String imageUrl) {
        String url = "";
        if (imageUrl != null) {
            if (imageUrl.startsWith("/https")) {
                url = imageUrl.replaceFirst("/", "");
            } else {
                url = IMAGE_URL + imageUrl;
            }
            Glide.with(view.getContext()).load(url).circleCrop().into(view);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AuthorDTO getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(AuthorDTO author_details) {
        this.author_details = author_details;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    @NonNull
    @Override
    public String toString() {
        return "ReviewsDTO{" +
                "id='" + id + '\'' +
                ", movie_id='" + movie_id + '\'' +
                ", author_details=" + author_details +
                ", review_date=" + review_date +
                ", created_at=" + created_at +
                '}';
    }

    public String convertServerDateToUi(String requestedDate) {
        System.out.println("created : "+getCreated_at());
        String UI_FORMAT = "MMM dd, yyyy";
        if (getCreated_at() == null) {
            return requestedDate;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UI_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(getCreated_at());
    }
}
