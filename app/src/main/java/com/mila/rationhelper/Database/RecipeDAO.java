package com.mila.rationhelper.Database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Insert
    void insert(RecipeEntity recipeEntity);

    @Query("DELETE FROM recipe_table")
    void deleteAll();

    // getters
    @Query("SELECT * FROM recipe_table")
    LiveData<List<RecipeEntity>> getAllRecipes();

    @Query("SELECT * FROM recipe_table WHERE isBreakfast = :bool")
    LiveData<List<RecipeEntity>> getBreakfast(String bool);

    @Query("SELECT * FROM recipe_table WHERE isLuch = :bool")
    LiveData<List<RecipeEntity>> getLunch(String bool);

    @Query("SELECT * FROM recipe_table WHERE isDinner = :bool")
    LiveData<List<RecipeEntity>> getDinner(String bool);

    @Query("SELECT * FROM recipe_table WHERE category = :category")
    LiveData<List<RecipeEntity>> getByCategory(String category);

    // deletion
    @Query("DELETE FROM recipe_table WHERE id = :recipeID")
    void deleteRecipe(int recipeID);

}
