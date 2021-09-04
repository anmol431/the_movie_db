package com.themovie.db.app.model;

import static com.themovie.db.app.constants.Constants.IMAGE_URL;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReviewsDTO implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("author")
    private String author;

    @SerializedName("author_details")
    private AuthorDTO author_details;

    @SerializedName("created_at")
    private Timestamp created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public AuthorDTO getAuthor_details() {
        return author_details;
    }

    public void setAuthor_details(AuthorDTO author_details) {
        this.author_details = author_details;
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
                ", author='" + author + '\'' +
                ", author_details=" + author_details +
                ", created_at=" + created_at +
                '}';
    }

    public String convertServerDateToUi(Timestamp requestedDate) {
        String UI_FORMAT = "MMM dd, yyyy";
        if (requestedDate == null) {
            return "-";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(UI_FORMAT, Locale.getDefault());
        return simpleDateFormat.format(requestedDate);
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
}
