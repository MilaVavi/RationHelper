package com.mila.rationhelper.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "sugar_table")
public class SugarRecordEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "sugarLevel")
    private double sugarLevel;

    @NonNull
    @ColumnInfo(name = "measuredDate")
    private String measuredDate;

    public SugarRecordEntity(double sugarLevel, @NonNull String measuredDate) {
        this.sugarLevel = sugarLevel;
        this.measuredDate = measuredDate;
    }

    public double getSugarLevel() {
        return sugarLevel;
    }

    public void setSugarLevel(double sugarLevel) {
        this.sugarLevel = sugarLevel;
    }

    @NonNull
    public String getMeasuredDate() {
        return measuredDate;
    }

    public void setMeasuredDate(@NonNull String measuredDate) {
        this.measuredDate = measuredDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

/*    @Exclude //used to push object to firebase
    public Map<String, Object> toMap()
    {
        Map<String, Object> result = new HashMap<>();

        result.put("sugarLevel", sugarLevel);
        result.put("measuredDate", measuredDate);

        return result;
    }*/
}
