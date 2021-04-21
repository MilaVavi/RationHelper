package com.mila.rationhelper.Database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {
    private Repository repository;

    public RecipeViewModel (Application application) {
        super(application);
        repository = new Repository(application);
    }

    // general
    public void insertRecipe(RecipeEntity recipe) {
        LocalDatabase.databaseWriteExecutor.execute(() -> repository.insertRecipe(recipe));
    }
    public void deleteAllRecipies(){
        LocalDatabase.databaseWriteExecutor.execute(() -> repository.deleteAllRecipies());
    }

    // getters
    public LiveData<List<RecipeEntity>> getAllRecipes(){return repository.getAllRecipes();}

    public LiveData<List<RecipeEntity>> getBreakfast(){
        return repository.getBreakfast();
    }

    public LiveData<List<RecipeEntity>> getLunch(){
        return repository.getLunch();
    }

    public LiveData<List<RecipeEntity>> getDinner(){
        return repository.getDinner();
    }

    public LiveData<List<RecipeEntity>> getRecipesByCategory (String category){
        return repository.getRecipesByCategory(category);
    }

    public void deleteRecipe(int recipeID){
        LocalDatabase.databaseWriteExecutor.execute(() -> repository.deleteRecipe(recipeID));
    }

}
