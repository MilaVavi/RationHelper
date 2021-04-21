package com.mila.rationhelper.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SugarRecordDAO {

    @Insert
    void insert(SugarRecordEntity sugarRecordEntity);

    @Query("DELETE FROM sugar_table")
    void deleteAll();

    // getters
    @Query("SELECT * FROM sugar_table ORDER BY date(measuredDate) DESC")
    LiveData<List<SugarRecordEntity>> getAllRecords();

    @Query("SELECT * FROM sugar_table ORDER BY date(measuredDate) DESC LIMIT 10")
    LiveData<List<SugarRecordEntity>> getLastRecords();
    }
