package com.mila.rationhelper.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.mila.rationhelper.Database.RecipeEntity;
import com.mila.rationhelper.R;

import java.util.HashMap;
import java.util.List;

public class LibraryListAdapter extends BaseExpandableListAdapter {
    private String TAG = getClass().getSimpleName();

    private Context context;
    private List<String> expandableListCategory;
    private HashMap<String, List<RecipeEntity>> expandableListRecipes;

    public LibraryListAdapter(Context context) {
        this.context = context;

        Log.d(TAG, "Received recipes: "+expandableListRecipes);
    }

    @Override
    public RecipeEntity getChild(int listPosition, int expandedListPosition) {
        return this.expandableListRecipes.get(this.expandableListCategory.get(listPosition))
                .get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final RecipeEntity recipe = getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.menu_list_item, null);
        }
        TextView recipeName = (TextView) convertView.findViewById(R.id.recipeRecyclerName);
        recipeName.setText(recipe.getRecipeName());

        TextView recipeCalories = (TextView) convertView.findViewById(R.id.recipeRecyclerCallories);
        recipeCalories.setText(Integer.toString(recipe.getRecipeCalories()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.expandableListRecipes.get(this.expandableListCategory.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.expandableListCategory.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.expandableListCategory == null ? 0 : expandableListCategory.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String categoryName = (String) getGroup(listPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.library_category, null);
        }

        TextView libraryCategory = (TextView) convertView.findViewById(R.id.libraryCategory);
        libraryCategory.setTypeface(null, Typeface.BOLD);
        libraryCategory.setText(categoryName);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }

    public void setContent(List<String> expandableListCategory, HashMap<String, List<RecipeEntity>> expandableListRecipes){
        Log.d(TAG, "Updating recipes");
        this.expandableListCategory = expandableListCategory;
        this.expandableListRecipes = expandableListRecipes;
        notifyDataSetChanged();
    }
}