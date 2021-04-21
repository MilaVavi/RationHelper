package com.mila.rationhelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.mila.rationhelper.Adapters.LibraryListAdapter;
import com.mila.rationhelper.Database.RecipeEntity;
import com.mila.rationhelper.Database.Repository;
import com.mila.rationhelper.Helpers.Constants;
import com.mila.rationhelper.Helpers.FirebaseRecipeReceiver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryActivity extends AppCompatActivity implements FirebaseRecipeReceiver {
    private String TAG = getClass().getSimpleName();

    private ExpandableListView expandableListView;
    private LibraryListAdapter expandableListAdapter;
    private List<String> expandableListCategories;
    private HashMap<String, List<RecipeEntity>> expandableListRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        Intent intent = getIntent();

        populateViews();

        if (intent.hasExtra(Constants.EXTRA_MEAL)) {
            String meal = intent.getStringExtra(Constants.EXTRA_MEAL);
            Repository.getAllRecipesFirebase(meal, this);
        }
    }


    private void populateViews(){
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        expandableListAdapter = new LibraryListAdapter(this);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String category = expandableListCategories.get(groupPosition);
                RecipeEntity recipe = expandableListRecipes.get(category).get(childPosition);

                Log.d(TAG, category + "\n" + recipe);

                Intent intent = new Intent(parent.getContext(), RecipeActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE_ENTITY, recipe);
                intent.putExtra(Constants.EXTRA_RECIPE_SOURCE, Constants.RECIPE_SOURCES[1]);
                startActivity(intent);
                return false;
            }
        });
    }

    @Override
    public void updateRecipes(HashMap<String, List<RecipeEntity>> recipes) {
        Log.d(TAG, "updating recipes from callback :\n"+recipes);
        this.expandableListRecipes = recipes;
        expandableListCategories = new ArrayList<>(expandableListRecipes.keySet());
        expandableListAdapter.setContent(expandableListCategories, expandableListRecipes);
    }

}
