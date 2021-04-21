package com.mila.rationhelper.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mila.rationhelper.Database.RecipeEntity;
import com.mila.rationhelper.Helpers.Constants;
import com.mila.rationhelper.R;
import com.mila.rationhelper.RecipeActivity;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter <RecipeAdapter.RecipeViewHolder>
{
    private String TAG = getClass().getSimpleName();

    static class RecipeViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView text, bulletPoint;

        private RecipeViewHolder(View itemView)
        {
            super(itemView);
            text = itemView.findViewById(R.id.recipeAdaptersText);
            bulletPoint = itemView.findViewById(R.id.recipeAdapterMarker);
        }
    }


    private final LayoutInflater inflater;

    private List<String> recipe; // Cached copy

    public RecipeAdapter (Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create view holder");
        View itemView = inflater.inflate(R.layout.recipe_adapters_list_item, parent, false);
        return new RecipeAdapter.RecipeViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position)
    {
        Log.d(TAG,"on bind view holder");
        Log.d(TAG, "recipe size: "+recipe.size());
        Log.d(TAG, "position: "+position);
        if (recipe.size() > 0) {
            String current = recipe.get(position);
            holder.text.setText(current);
            Log.d(TAG, "Set text: " + current);
            holder.bulletPoint.setText(Integer.toString(position+1)+": ");
        }
    }

    public void setRecipe(List<String> recipe)
    {
        Log.d(TAG, "Got repice!");
//        Log.d(TAG, recipies.get(0).getRecipeName());
        this.recipe = recipe;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount()
    {
        Log.d(TAG,"getItemCount()");
        return recipe == null ? 0 : recipe.size();
    }
}