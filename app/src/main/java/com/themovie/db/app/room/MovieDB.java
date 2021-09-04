package com.themovie.db.app.room;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.themovie.db.app.constants.Constants;
import com.themovie.db.app.model.AuthorDTO;
import com.themovie.db.app.model.CastDTO;
import com.themovie.db.app.model.MoviesDTO;
import com.themovie.db.app.model.ReviewsDTO;

import java.util.ArrayList;
import java.util.List;

public class MovieDB {
    public static final String TABLE_NAME = Constants.MOVIE_TABLE;
    public static final String CAST_TABLE_TABLE_NAME = Constants.CAST_TABLE;
    public static final String REVIEW_TABLE_TABLE_NAME = Constants.REVIEW_TABLE;
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String BACK_DROP_PATH = "backdrop_path";
    public static final String VOTE_AVERAGE = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final String id = "id";
    public static final String STATUS = "status";
    public static final String BUDGET = "budget";
    public static final String REVENUE = "revenue";
    public static final String GENRE = "genre";
    public static final String OVERVIEW = "overview";
    public static final String POSTER_PATH = "poster_path";

    public static final String CAST_NAME = "name";
    public static final String CAST_ROLE = "role";
    public static final String CAST_ID = "id";
    public static final String CAST_MOVIE_ID = "movie_id";
    public static final String CAST_PROFILE = "profile";

    public static final String REVIEW_CREATED = "created_at";
    public static final String REVIEW_MOVIE_ID = "movie_id";
    public static final String REVIEW_ID = "id";
    public static final String REVIEW_NAME = "username";
    public static final String REVIEW_PROFILE = "avatar_path";
    public static final String REVIEW_RATING = "rating";

    public static final String CREATE_MOVIE_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, title TEXT, backdrop_path TEXT, category TEXT, vote_average REAL, release_date TEXT," +
            "overview TEXT, status TEXT, genre TEXT, budget REAL, revenue REAL, poster_path TEXT)";

    public static final String CREATE_CAST_TABLE = "CREATE TABLE " + CAST_TABLE_TABLE_NAME
            + "(id INTEGER, movie_id INTEGER, name TEXT, role TEXT, profile TEXT)";

    public static final String CREATE_REVIEW_TABLE = "CREATE TABLE " + REVIEW_TABLE_TABLE_NAME
            + "(id INTEGER, movie_id INTEGER, created_at TEXT, username TEXT, avatar_path TEXT, rating REAL)";


    static MovieDB instance;
    static GenericController dbController;

    private MovieDB() {

    }

    public static MovieDB getInstance(Context context) {
        if (instance == null) {
            instance = new MovieDB();
        }
        dbController = GenericController.getInstance(context);

        return instance;
    }

    public void insert(MoviesDTO moviesDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, moviesDTO.getTitle());
        contentValues.put(CATEGORY, moviesDTO.getCategory());
        contentValues.put(BACK_DROP_PATH, moviesDTO.getBackdrop_path());
        contentValues.put(VOTE_AVERAGE, moviesDTO.getVote_average());
        contentValues.put(RELEASE_DATE, moviesDTO.getRelease_date());
        contentValues.put(id, moviesDTO.getId());
        contentValues.put(GENRE, moviesDTO.getM_genres());
        contentValues.put(OVERVIEW, moviesDTO.getOverview());
        contentValues.put(STATUS, moviesDTO.getStatus());
        contentValues.put(BUDGET, moviesDTO.getBudget());
        contentValues.put(REVENUE, moviesDTO.getRevenue());
        contentValues.put(POSTER_PATH, moviesDTO.getPoster_path());

        dbController.getWritableDatabase().insertWithOnConflict(TABLE_NAME,
                null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void insertReview(ReviewsDTO reviewsDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(REVIEW_ID, reviewsDTO.getId());
        contentValues.put(REVIEW_MOVIE_ID, reviewsDTO.getMovie_id());
        contentValues.put(REVIEW_NAME, reviewsDTO.getAuthor_details().getUsername());
        contentValues.put(REVIEW_CREATED, reviewsDTO.getReview_date());
        contentValues.put(REVIEW_PROFILE, reviewsDTO.getAuthor_details().getAvatar_path());
        contentValues.put(REVIEW_RATING, reviewsDTO.getAuthor_details().getRating());

        dbController.getWritableDatabase().insertWithOnConflict(REVIEW_TABLE_TABLE_NAME,
                null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<ReviewsDTO> getReviewList(int movieId) {
        List<ReviewsDTO> details = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + REVIEW_TABLE_TABLE_NAME + " WHERE " + REVIEW_MOVIE_ID + " = '" + movieId + "'";

        @SuppressLint("Recycle")
        Cursor cursor = dbController.getReadableDatabase().rawQuery(selectQuery, null);

        cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                ReviewsDTO reviewsDTO = new ReviewsDTO();
                AuthorDTO authorDTO = new AuthorDTO();
                authorDTO.setAvatar_path(cursor.getString(cursor.getColumnIndex(REVIEW_PROFILE)));
                authorDTO.setUsername(cursor.getString(cursor.getColumnIndex(REVIEW_NAME)));
                authorDTO.setRating(cursor.getFloat(cursor.getColumnIndex(REVIEW_RATING)));

                reviewsDTO.setId(cursor.getString(cursor.getColumnIndex(REVIEW_ID)));
                reviewsDTO.setMovie_id(cursor.getInt(cursor.getColumnIndex(REVIEW_MOVIE_ID)));
                reviewsDTO.setReview_date(cursor.getString(cursor.getColumnIndex(REVIEW_CREATED)));
                reviewsDTO.setAuthor_details(authorDTO);

                details.add(reviewsDTO);

            } while (cursor.moveToNext());
        }
        return details;
    }

    public void insertCast(CastDTO castDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CAST_NAME, castDTO.getName());
        contentValues.put(CAST_ID, castDTO.getId());
        contentValues.put(CAST_MOVIE_ID, castDTO.getMovieId());
        contentValues.put(CAST_ROLE, castDTO.getCharacter());
        contentValues.put(CAST_PROFILE, castDTO.getProfile_path());

        dbController.getWritableDatabase().insertWithOnConflict(CAST_TABLE_TABLE_NAME,
                null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<CastDTO> getCastList(int movieId) {
        List<CastDTO> details = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + CAST_TABLE_TABLE_NAME + " WHERE " + CAST_MOVIE_ID + " = '" + movieId + "'";

        @SuppressLint("Recycle")
        Cursor cursor = dbController.getReadableDatabase().rawQuery(selectQuery, null);

        cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                CastDTO castDTO = new CastDTO();

                castDTO.setId(cursor.getInt(cursor.getColumnIndex(CAST_ID)));
                castDTO.setMovieId(cursor.getInt(cursor.getColumnIndex(CAST_MOVIE_ID)));
                castDTO.setCharacter(cursor.getString(cursor.getColumnIndex(CAST_ROLE)));
                castDTO.setName(cursor.getString(cursor.getColumnIndex(CAST_NAME)));
                castDTO.setProfile_path(cursor.getString(cursor.getColumnIndex(CAST_PROFILE)));

                details.add(castDTO);

            } while (cursor.moveToNext());
        }
        return details;
    }

    public List<MoviesDTO> getMovieList(String category) {
        List<MoviesDTO> details = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + CATEGORY + " = '" + category + "'";

        @SuppressLint("Recycle")
        Cursor cursor = dbController.getReadableDatabase().rawQuery(selectQuery, null);

        cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                MoviesDTO moviesDTO = new MoviesDTO();

                moviesDTO.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                moviesDTO.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));
                moviesDTO.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACK_DROP_PATH)));
                moviesDTO.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
                moviesDTO.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
                moviesDTO.setId(cursor.getInt(cursor.getColumnIndex(id)));
                moviesDTO.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
                moviesDTO.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
                moviesDTO.setM_genres(cursor.getString(cursor.getColumnIndex(GENRE)));
                moviesDTO.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                moviesDTO.setBudget(cursor.getLong(cursor.getColumnIndex(BUDGET)));
                moviesDTO.setRevenue(cursor.getLong(cursor.getColumnIndex(REVENUE)));

                details.add(moviesDTO);

            } while (cursor.moveToNext());
        }
        return details;
    }

    public MoviesDTO getMovie(int movieId) {
        MoviesDTO moviesDTO = new MoviesDTO();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + id + " = '" + movieId + "'";

        @SuppressLint("Recycle")
        Cursor cursor = dbController.getReadableDatabase().rawQuery(selectQuery, null);

        cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                moviesDTO.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                moviesDTO.setCategory(cursor.getString(cursor.getColumnIndex(CATEGORY)));
                moviesDTO.setBackdrop_path(cursor.getString(cursor.getColumnIndex(BACK_DROP_PATH)));
                moviesDTO.setVote_average(cursor.getFloat(cursor.getColumnIndex(VOTE_AVERAGE)));
                moviesDTO.setRelease_date(cursor.getString(cursor.getColumnIndex(RELEASE_DATE)));
                moviesDTO.setId(cursor.getInt(cursor.getColumnIndex(id)));
                moviesDTO.setOverview(cursor.getString(cursor.getColumnIndex(OVERVIEW)));
                moviesDTO.setPoster_path(cursor.getString(cursor.getColumnIndex(POSTER_PATH)));
                moviesDTO.setM_genres(cursor.getString(cursor.getColumnIndex(GENRE)));
                moviesDTO.setStatus(cursor.getString(cursor.getColumnIndex(STATUS)));
                moviesDTO.setBudget(cursor.getLong(cursor.getColumnIndex(BUDGET)));
                moviesDTO.setRevenue(cursor.getLong(cursor.getColumnIndex(REVENUE)));

            } while (cursor.moveToNext());
        }
        return moviesDTO;
    }
}