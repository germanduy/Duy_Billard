package com.example.billiardshop.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.billiardshop.model.Cue;

@Database(entities = {Cue.class}, version = 1)
public abstract class CueDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "cue.db";

    private static CueDatabase instance;

    public static synchronized CueDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), CueDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
    public abstract CueDAO cueDAO();
}
