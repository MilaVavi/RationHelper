package com.mila.rationhelper.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mila.rationhelper.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IngredientsAdapter extends RecyclerView.Adapter <IngredientsAdapter.RecipeViewHolder>
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

    private ArrayList<String> ingredients; // Cached copy

    public IngredientsAdapter (Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public IngredientsAdapter.RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create view holder");
        View itemView = inflater.inflate(R.layout.recipe_adapters_list_item, parent, false);
        return new IngredientsAdapter.RecipeViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(IngredientsAdapter.RecipeViewHolder holder, int position)
    {
        Log.d(TAG,"on bind view holder");
        Log.d(TAG, "recipe size: "+ingredients.size());
        Log.d(TAG, "position: "+position);
        if (ingredients.size() > 0) {
            Log.d(TAG, "ingredients: \n"+ingredients.toString());
            String current = ingredients.get(position);
            holder.text.setText(current);
            holder.bulletPoint.setText("• ");
        }
    }

    public void setIngredients(ArrayList<String> ingredients)
    {
        Log.d(TAG, "Got ingredients!");
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount()
    {
        Log.d(TAG,"getItemCount()");
        return ingredients == null ? 0 : ingredients.size();
    }
}