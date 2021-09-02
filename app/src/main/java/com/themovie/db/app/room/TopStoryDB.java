package com.themovie.db.app.room;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.themovie.db.app.constants.Constants;
import com.themovie.db.app.model.StoryDTO;

import java.util.ArrayList;
import java.util.List;

public class TopStoryDB {
    public static final String TABLE_NAME = Constants.STORY_TABLE;
    public static final String TITLE = "title";
    public static final String DESCENDANTS = "descendants";
    public static final String SCORE = "score";
    public static final String TYPE = "type";
    public static final String id = "id";
    public static final String TEXT = "text";
    public static final String TIME = "time";
    public static final String read = "read";
    public static final String URL = "url";
    public static final String AUTHOR = "by";

    public static final String CREATE_STORY_TABLE = "CREATE TABLE " + TABLE_NAME
            + "(id INTEGER, title TEXT, descendants TEXT, score INTEGER, type TEXT, read TEXT, text TEXT," +
            "time INTEGER, url TEXT, by TEXT)";

    static TopStoryDB instance;
    static GenericController dbController;

    private TopStoryDB() {

    }

    public static TopStoryDB getInstance(Context context) {
        if (instance == null) {
            instance = new TopStoryDB();
        }
        dbController = GenericController.getInstance(context);

        return instance;
    }

    public void insert(StoryDTO storyDTO) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(TITLE, storyDTO.getTitle());
        contentValues.put(DESCENDANTS, storyDTO.getDescendants());
        contentValues.put(SCORE, storyDTO.getScore());
        contentValues.put(TYPE, storyDTO.getType());
        contentValues.put(id, storyDTO.getId());
        contentValues.put(TEXT, storyDTO.getText());
        contentValues.put(TIME, storyDTO.getTime());
        contentValues.put(URL, storyDTO.getUrl());
        contentValues.put(AUTHOR, storyDTO.getBy());
        contentValues.put(read, storyDTO.getRead());

        dbController.getWritableDatabase().insertWithOnConflict(TABLE_NAME,
                null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public List<StoryDTO> getTopStoryList() {
        List<StoryDTO> details = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME + " ORDER BY SCORE DESC";

        @SuppressLint("Recycle")
        Cursor cursor = dbController.getReadableDatabase().rawQuery(selectQuery, null);

        cursor.getCount();

        if (cursor.moveToFirst()) {
            do {
                StoryDTO storyDTO = new StoryDTO();

                storyDTO.setBy(cursor.getString(cursor.getColumnIndex(AUTHOR)));
                storyDTO.setTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                storyDTO.setDescendants(cursor.getInt(cursor.getColumnIndex(DESCENDANTS)));
                storyDTO.setType(cursor.getString(cursor.getColumnIndex(TYPE)));
                storyDTO.setScore(cursor.getInt(cursor.getColumnIndex(SCORE)));
                storyDTO.setId(cursor.getInt(cursor.getColumnIndex(id)));
                storyDTO.setRead(cursor.getString(cursor.getColumnIndex(read)));
                storyDTO.setUrl(cursor.getString(cursor.getColumnIndex(URL)));
                storyDTO.setText(cursor.getString(cursor.getColumnIndex(TEXT)));
                storyDTO.setTime(cursor.getLong(cursor.getColumnIndex(TIME)));

                details.add(storyDTO);

            } while (cursor.moveToNext());
        }
        return details;
    }

    public void clear() {
        String deleteQuery = "DELETE FROM " + TABLE_NAME;
        dbController.getWritableDatabase().execSQL(deleteQuery);
    }

    public void update(long id) {
        String updateQuery = "UPDATE " + TABLE_NAME + " SET " + "read='true' WHERE id=" + id;
        dbController.getWritableDatabase().execSQL(updateQuery);
    }
}