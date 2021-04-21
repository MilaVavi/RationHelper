package com.mila.rationhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mila.rationhelper.Adapters.MenuRecyclerAdapter;
import com.mila.rationhelper.Database.RecipeEntity;
import com.mila.rationhelper.Database.RecipeViewModel;
import com.mila.rationhelper.Helpers.Constants;

public class MenuActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private RecyclerView breakfastRecycler, lunchRecycler, dinerRecycler;
    private RecipeViewModel recipeViewModel;
    private TextView totalCaloriesTW, requiredCaloriesTW;
    private int totalCalories, breakfastCalories, lunchCalories, dinnerCalories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        finrReferences();
    }

    @Override
    protected void onStart() {
        super.onStart();
        populateRecyclers();
    }

    private void finrReferences(){
        breakfastRecycler = findViewById(R.id.breakfastREcycler);
        lunchRecycler = findViewById(R.id.lunchRecycler);
        dinerRecycler = findViewById(R.id.dinnerRecycler);
        totalCaloriesTW = findViewById(R.id.totalCalsTW);
        requiredCaloriesTW = findViewById(R.id.requiredCalsTW);
        requiredCaloriesTW.setText(Constants.calculateCalories(this));
        totalCalories = breakfastCalories = lunchCalories = dinnerCalories = 0;

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

    }

    private void populateRecyclers(){
        MenuRecyclerAdapter breakfastAdapter = new MenuRecyclerAdapter(this);
        MenuRecyclerAdapter lunchAdapter = new MenuRecyclerAdapter(this);
        MenuRecyclerAdapter dinnerAdapter = new MenuRecyclerAdapter(this);

        breakfastRecycler.setLayoutManager(new LinearLayoutManager(this));
        lunchRecycler.setLayoutManager(new LinearLayoutManager(this));
        dinerRecycler.setLayoutManager(new LinearLayoutManager(this));

        breakfastRecycler.setAdapter(breakfastAdapter);
        lunchRecycler.setAdapter(lunchAdapter);
        dinerRecycler.setAdapter(dinnerAdapter);

        // Update the cached copy of the objects in the adapter.
        recipeViewModel.getBreakfast().observe(this, recipeEntities -> {
            breakfastAdapter.setRecipes(recipeEntities);
            Log.d(TAG, "got breakfast");

            // reset
            breakfastCalories = 0;

            // update
            for (RecipeEntity recipe : recipeEntities)
                breakfastCalories += recipe.getRecipeCalories();
            updateTotalCallories();
        });

        recipeViewModel.getLunch().observe(this, recipeEntities -> {
            lunchAdapter.setRecipes(recipeEntities);
            Log.d(TAG, "got lunch");

            lunchCalories = 0;

            // update
            for (RecipeEntity recipe : recipeEntities)
                lunchCalories += recipe.getRecipeCalories();
            updateTotalCallories();
        });

        recipeViewModel.getDinner().observe(this, recipeEntities -> {
            dinnerAdapter.setRecipes(recipeEntities);
            Log.d(TAG, "got dinner");

            dinnerCalories = 0;

            // update
            for (RecipeEntity recipe : recipeEntities)
                dinnerCalories += recipe.getRecipeCalories();
            updateTotalCallories();
        });
    }

    private synchronized void updateTotalCallories(){
        totalCalories = breakfastCalories + lunchCalories + dinnerCalories;
        // update total
        totalCaloriesTW.setText(Integer.toString(totalCalories));
    }


    public void gotToMeasurments(View view){
        Intent intent = new Intent(this, SugarLevelsActivity.class);
        startActivity(intent);
    }


    public void goToUpdateDetails(View view){
        Intent intent = new Intent(this, PersonalDetailsActivity.class);
        startActivity(intent);
    }


    public void addBreakfast(View view){
        Intent intent = new Intent(this, LibraryActivity.class);
        intent.putExtra(Constants.EXTRA_MEAL, Constants.MEALS[0]);
        startActivity(intent);
    }

    public void addLunch(View view){
        Intent intent = new Intent(this, LibraryActivity.class);
        intent.putExtra(Constants.EXTRA_MEAL, Constants.MEALS[1]);
        startActivity(intent);
    }

    public void addDinner(View view){
        Intent intent = new Intent(this, LibraryActivity.class);
        intent.putExtra(Constants.EXTRA_MEAL, Constants.MEALS[2]);
        startActivity(intent);
    }

}