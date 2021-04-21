package com.mila.rationhelper.Helpers;

import android.util.Log;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class Converters {
    @TypeConverter
    public String list2String(ArrayList<String> list){
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }

    @TypeConverter
    public ArrayList<String> string2List(String value){
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public String map2String (Map<String, String> map){
        Gson gson = new Gson();
        Log.d(getClass().getSimpleName(), "to string: \n"+map.toString());
        return gson.toJson(map);
    }

    @TypeConverter
    public Map<String, String> string2Map (String value){
        Type mapType = new TypeToken<Map<Integer, String>>() {
        }.getType();
        Map<Integer, String> map = new Gson().fromJson(value, mapType);
        Log.d(getClass().getSimpleName(),"from string: \n"+ map.toString());
        return new Gson().fromJson(value, mapType);
    }
}
