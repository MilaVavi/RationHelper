package com.mila.rationhelper.Database;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mila.rationhelper.Helpers.Constants;
import com.mila.rationhelper.Helpers.FirebaseRecipeReceiver;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository {
    private RecipeDAO recipeDAO;
    private SugarRecordDAO sugarRecordDAO;
    private String TAG = getClass().getSimpleName();

    private static DatabaseReference firebaseReference = FirebaseDatabase.getInstance().getReference();

    public Repository(Application application) {
        Log.d(TAG, "constructor");
        LocalDatabase db = LocalDatabase.getDatabase(application);
        recipeDAO = db.recipeDAO();
        sugarRecordDAO = db.sugarRecordDAO();

    }

    // general
    public void insertRecipe(RecipeEntity recipe) {
        LocalDatabase.databaseWriteExecutor.execute(() -> recipeDAO.insert(recipe));
    }

    public void insertRecord(SugarRecordEntity record) {
        LocalDatabase.databaseWriteExecutor.execute(() -> sugarRecordDAO.insert(record));
    }

    public void deleteAllRecipies(){
        LocalDatabase.databaseWriteExecutor.execute(() -> recipeDAO.deleteAll());
    }

    public void deleteAllRecords(){
        LocalDatabase.databaseWriteExecutor.execute(() -> sugarRecordDAO.deleteAll());
    }


    // recipe
    public LiveData<List<RecipeEntity>> getAllRecipes(){return recipeDAO.getAllRecipes();}

    public LiveData<List<RecipeEntity>> getBreakfast(){
        return recipeDAO.getBreakfast("T");
    }

    public LiveData<List<RecipeEntity>> getLunch(){
        return recipeDAO.getLunch("T");
    }

    public LiveData<List<RecipeEntity>> getDinner(){
        return recipeDAO.getDinner("T");
    }

    public LiveData<List<RecipeEntity>> getRecipesByCategory (String category){
        return recipeDAO.getByCategory(category);
    }

    void deleteRecipe(int recipeID){
        LocalDatabase.databaseWriteExecutor.execute(() -> recipeDAO.deleteRecipe(recipeID));
    }


    // record
    public LiveData<List<SugarRecordEntity>> getAllRecords(){return sugarRecordDAO.getAllRecords();}

    public LiveData<List<SugarRecordEntity>> getLastRecords(){return sugarRecordDAO.getLastRecords();}




    public static void getAllRecipesFirebase(@NotNull String meal, FirebaseRecipeReceiver receiver){
        Log.d("FirebaseReceiver","Attempt to retrieve");
        // TODO anonymous authentication needed

        List<RecipeEntity> vegesAndSalads = new ArrayList<>();
        List<RecipeEntity> mainDishes = new ArrayList<>();
        List<RecipeEntity> fruitsAndDeserts = new ArrayList<>();
        List<RecipeEntity> drinks = new ArrayList<>();
        List<RecipeEntity> other = new ArrayList<>();

        boolean lookingForBreakfast = meal.equals(Constants.MEALS[0]);
        boolean lookingForLunch = meal.equals(Constants.MEALS[1]);
        boolean lookingForDinner = meal.equals(Constants.MEALS[2]);

        firebaseReference.child("Recipes").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot recipeSnapshot : snapshot.getChildren()) {
                    RecipeEntity recipe = recipeSnapshot.getValue(RecipeEntity.class);
//                    Log.d("FirebaseReceiver", recipe.toString());

                    boolean foundBreakfast = recipe.getIsBreakfast().equals("T");
                    boolean foundLunch = recipe.getIsLunch().equals("T");
                    boolean foundDinner = recipe.getIsDinner().equals("T");

                    Log.d("FirebaseReceiver", "Testing for breakfast: "+(lookingForBreakfast && foundBreakfast));
                    Log.d("FirebaseReceiver", "Testing for lunch: "+ (lookingForLunch && foundLunch));
                    Log.d("FirebaseReceiver", "Testing for dinner: "+(lookingForDinner && foundDinner));

                    if (lookingForBreakfast && foundBreakfast){
                        recipe.setIsLunch("F");
                        recipe.setIsDinner("F");
                    }
                    if (lookingForLunch && foundLunch){
                        recipe.setIsBreakfast("F");
                        recipe.setIsDinner("F");
                    }
                    if (lookingForDinner && foundDinner){
                        recipe.setIsBreakfast("F");
                        recipe.setIsLunch("F");
                    }

                    if ((lookingForBreakfast && foundBreakfast) ||
                            (lookingForLunch && foundLunch) ||
                            (lookingForDinner && foundDinner)) {

                        Log.d("FirebaseReceiver", recipe.toString());

                        String category = recipe.getCategory();
                        if (category.equals(Constants.RECIPE_CATEGORIES[0])) {
                            vegesAndSalads.add(recipe);
                            Log.d("FirebaseReceiver", "added to veges and salads");
                        }
                        else if (category.equals(Constants.RECIPE_CATEGORIES[1])) {
                            mainDishes.add(recipe);
                            Log.d("FirebaseReceiver", "main dishes");
                        }
                        else if (category.equals(Constants.RECIPE_CATEGORIES[2])) {
                            fruitsAndDeserts.add(recipe);
                            Log.d("FirebaseReceiver", "added to fruits or deserts");
                        }
                        else if (category.equals(Constants.RECIPE_CATEGORIES[3])) {
                            drinks.add(recipe);
                            Log.d("FirebaseReceiver", "added to drinks");
                        }
                        else if (category.equals(Constants.RECIPE_CATEGORIES[4])) {
                            other.add(recipe);
                            Log.d("FirebaseReceiver", "added to added to other");
                        }
                        else {
                            other.add(recipe);
                            Log.d("FirebaseReceiver", "defaulted to others");
                        }
                    }
                }

                HashMap<String, List<RecipeEntity>> recipes = new HashMap<>();

                if(!vegesAndSalads.isEmpty())
                    recipes.put(Constants.RECIPE_CATEGORIES[0], vegesAndSalads);
                if(!mainDishes.isEmpty())
                    recipes.put(Constants.RECIPE_CATEGORIES[1], mainDishes);
                if(!fruitsAndDeserts.isEmpty())
                    recipes.put(Constants.RECIPE_CATEGORIES[2], fruitsAndDeserts);
                if(!drinks.isEmpty())
                    recipes.put(Constants.RECIPE_CATEGORIES[3], drinks);
                if(!other.isEmpty())
                    recipes.put(Constants.RECIPE_CATEGORIES[4], other);

                Log.d("FirebaseReceiver", "map to return: "+recipes);
                receiver.updateRecipes(recipes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public static void populateFirebaseDummy(){
        RecipeEntity[] sampleRecipes = Constants.getSampleRecipesFirebase();

        for (RecipeEntity recipe : sampleRecipes) {
            String key = firebaseReference.push().getKey();
            Log.d(Repository.class.getSimpleName(), key);
            firebaseReference.child("Recipes").child(key).setValue(recipe.toMap());
        }
    }
}
