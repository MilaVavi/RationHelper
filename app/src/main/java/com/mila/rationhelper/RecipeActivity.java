package com.mila.rationhelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mila.rationhelper.Adapters.IngredientsAdapter;
import com.mila.rationhelper.Adapters.RecipeAdapter;
import com.mila.rationhelper.Database.RecipeEntity;
import com.mila.rationhelper.Database.RecipeViewModel;
import com.mila.rationhelper.Helpers.Constants;
import com.mila.rationhelper.Helpers.DownloadImage;

public class RecipeActivity extends AppCompatActivity {
    private String TAG = getClass().getSimpleName();
    private TextView recipeName, recipeCallories;
    private RecipeEntity recipeEntity;
    private RecipeViewModel recipeViewModel;
    private RecyclerView recipe, ingredients;
    private Button actionButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        findReferences();
        Intent intent = getIntent();

        if (intent.hasExtra(Constants.EXTRA_RECIPE_ENTITY)) {
            Log.d(TAG, "Extra recipe found");
            recipeEntity = (RecipeEntity) intent.getSerializableExtra(Constants.EXTRA_RECIPE_ENTITY);
            fillViews();
        }

        if (intent.hasExtra(Constants.EXTRA_RECIPE_SOURCE)){
            String recipeSource = intent.getStringExtra(Constants.EXTRA_RECIPE_SOURCE);

            if(recipeSource.equals(Constants.RECIPE_SOURCES[0]))
                setButtonLocal();
            else if (recipeSource.equals(Constants.RECIPE_SOURCES[1]))
                setButtonRemote();
        }
    }

    private void findReferences(){
        recipeName = findViewById(R.id.recipeDetailsRecipeName);
        recipeCallories = findViewById(R.id.recipeDetailsCallories);
        recipe = findViewById(R.id.recipe);
        ingredients = findViewById(R.id.recipeIngredients);
        actionButton = findViewById(R.id.recipeDetailsButton);
        imageView = findViewById(R.id.recipePhotoIW);

        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);
    }

    private void fillViews(){
        Log.d(TAG, "recipe entity: "+recipeEntity);
        if (recipeEntity != null) {
            recipeName.setText(recipeEntity.getRecipeName());
            recipeCallories.setText(Integer.toString(recipeEntity.getRecipeCalories()));

            new DownloadImage(imageView).execute(recipeEntity.getImageUrl());

            RecipeAdapter recipeAdapter = new RecipeAdapter(this);
            IngredientsAdapter ingredientsAdapter = new IngredientsAdapter(this);

            recipe.setLayoutManager(new LinearLayoutManager(this));
            recipe.setAdapter(recipeAdapter);
            recipeAdapter.setRecipe(recipeEntity.getRecipe());

            ingredients.setLayoutManager(new LinearLayoutManager(this));
            ingredients.setAdapter(ingredientsAdapter);
            ingredientsAdapter.setIngredients(recipeEntity.getIngredients());
        }
        else {
            recipeName.setText("Something went wrong!");
        }
    }

    public void buttonListenerLocal(View view){
        recipeViewModel.deleteRecipe(recipeEntity.getId());
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    public void buttonListenerRemote(View view){
        recipeViewModel.insertRecipe(recipeEntity);
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }

    private void setButtonLocal(){
        actionButton.setOnClickListener(this::buttonListenerLocal);
        actionButton.setText(Constants.LOCAL_BUTTON_TEXT);
    }

    private void setButtonRemote(){
        actionButton.setOnClickListener(this::buttonListenerRemote);
        actionButton.setText(Constants.REMOTE_BUTTON_TEXT);
    }
}