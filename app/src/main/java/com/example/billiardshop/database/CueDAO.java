package com.example.billiardshop.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.billiardshop.model.Cue;

import java.util.List;

@Dao
public interface CueDAO {
    @Insert
    void insertCue(Cue cue);

    @Query("SELECT * FROM cue")
    List<Cue> getListCueCart();

    @Query("SELECT * FROM cue WHERE id=:id")
    List<Cue> checkCueInCart(int id);

    @Delete
    void deleteCue(Cue cue);

    @Update
    void updateCue(Cue cue);

    @Query("DELETE from cue")
    void deleteAllCue();
}
