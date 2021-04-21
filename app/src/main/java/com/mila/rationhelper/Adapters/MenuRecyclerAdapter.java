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

public class MenuRecyclerAdapter extends RecyclerView.Adapter <MenuRecyclerAdapter.RecipeViewHolder>
{
    private String TAG = getClass().getSimpleName();

    static class RecipeViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView recipeName, recipeCallories, recipeID;

        private RecipeViewHolder(View itemView)
        {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeRecyclerName);
            recipeCallories = itemView.findViewById(R.id.recipeRecyclerCallories);
            recipeID = itemView.findViewById(R.id.recipeID);
        }
    }


    private final LayoutInflater inflater;

    private List<RecipeEntity> recipies; // Cached copy

    public MenuRecyclerAdapter (Context context)
    {
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create view holder");

        View itemView = inflater.inflate(R.layout.menu_list_item, parent, false);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                TextView taskName = v.findViewById(R.id.taskNameLisItem);
//                Intent it = new Intent(v.getContext(), Task.class);
//                it.putExtra(Constants.EXTRA_TASK_NAME, taskName.getText());
//                v.getContext().startActivity(it);

                TextView recipeIDView = v.findViewById(R.id.recipeID);
                String recipeID = (String) recipeIDView.getText();
                RecipeEntity recipeToPass = null;

                for (RecipeEntity  recipe : recipies)
                    if (Integer.toString(recipe.getId()).equals(recipeID))
                        recipeToPass = recipe;

                Log.d(TAG, "attaching recepie: "+recipeToPass);
                Intent intent = new Intent(v.getContext(), RecipeActivity.class);
                intent.putExtra(Constants.EXTRA_RECIPE_ENTITY, recipeToPass);
                intent.putExtra(Constants.EXTRA_RECIPE_SOURCE, Constants.RECIPE_SOURCES[0]);
                v.getContext().startActivity(intent);
            }
        });

        return new RecipeViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position)
    {
        Log.d(TAG,"on bind view holder");
        Log.d(TAG, "recipe size: "+recipies.size());
        Log.d(TAG, "position: "+position);
        if (recipies.size() > 0) {
            RecipeEntity current = recipies.get(position);
            holder.recipeName.setText(current.getRecipeName());
            Log.d(TAG, "Set name: "+current.getRecipeName());
            holder.recipeCallories.setText(Integer.toString(current.getRecipeCalories()));
            Log.d(TAG,"Set callories: "+current.getRecipeCalories());
            holder.recipeID.setText(Integer.toString(current.getId()));
        }
        else
            holder.recipeName.setText("No recipes so far");
    }

    public void setRecipes(List<RecipeEntity> recipies)
    {
        Log.d(TAG, "Got repices!");
//        Log.d(TAG, recipies.get(0).getRecipeName());
        this.recipies = recipies;
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount()
    {
        Log.d(TAG,"getItemCount()");
        return recipies == null ? 0 : recipies.size();
    }


}