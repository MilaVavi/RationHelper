package com.mila.rationhelper.Database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity(tableName = "recipe_table")
public class RecipeEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "recipeName")
    private String recipeName;

    @ColumnInfo(name = "calories")
    private int recipeCalories;

    @ColumnInfo(name = "recipe")
    private ArrayList<String> recipe;

    @ColumnInfo(name = "ingredients")
    private ArrayList<String> ingredients;

    @ColumnInfo(name = "isBreakfast")
    private String IsBreakfast;

    @ColumnInfo(name = "isLuch")
    private String IsLunch;

    @ColumnInfo(name = "isDinner")
    private String IsDinner;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "imageURL")
    private String ImageUrl;

    public RecipeEntity(@NonNull String recipeName, int recipeCalories, ArrayList<String> recipe, ArrayList<String> ingredients, String isBreakfast, String isLunch, String isDinner, String category, String imageUrl) {
        this.recipeName = recipeName;
        this.recipeCalories = recipeCalories;
        this.recipe = recipe;
        this.ingredients = ingredients;
        this.IsBreakfast = isBreakfast;
        this.IsLunch = isLunch;
        this.IsDinner = isDinner;
        this.category = category;
        this.ImageUrl = imageUrl;
    }

    public RecipeEntity() {
    }

    @NonNull
    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(@NonNull String recipeName) {
        this.recipeName = recipeName;
    }

    public int getRecipeCalories() {
        return recipeCalories;
    }

    public void setRecipeCalories(int recipeCalories) {
        this.recipeCalories = recipeCalories;
    }

    public ArrayList<String> getRecipe() {
        return recipe;
    }

    public void setRecipe(ArrayList<String> recipe) {
        this.recipe = recipe;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public String getIsBreakfast() {
        return IsBreakfast;
    }

    public void setIsBreakfast(String isBreakfast) {
        IsBreakfast = isBreakfast;
    }

    public String getIsLunch() {
        return IsLunch;
    }

    public void setIsLunch(String isLunch) {
        IsLunch = isLunch;
    }

    public String getIsDinner() {
        return IsDinner;
    }

    public void setIsDinner(String isDinner) {
        IsDinner = isDinner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return obj instanceof RecipeEntity && this.id == ((RecipeEntity) obj).id;
    }

    @Exclude //used to push object to firebase
    public Map<String, Object> toMap()
    {
        Map<String, Object> result = new HashMap<>();

        result.put("recipeName", recipeName);
        result.put("recipeCalories", recipeCalories);

        List<String> recipeList = recipe;
        result.put("recipe", recipeList);

        List<String> ingredientsList = ingredients;
        result.put("ingredients", ingredientsList);

        result.put("isBreakfast", IsBreakfast);
        result.put("isLunch", IsLunch);
        result.put("isDinner", IsDinner);
        result.put("category", category);
        result.put("imageUrl", ImageUrl);

        return result;
    }



    @NonNull
    @Override
    public String toString() {
        return "Recipe id: "+id+"\n"+
                "Recipe Name: "+recipeName+"\n"+
                "Recipe calories: "+recipeCalories+"\n"+
                "Recipe: "+Arrays.toString(recipe.toArray()) +"\n"+
                "Recipe ingredients: "+ingredients+"\n"+
                "is breakfast: "+IsBreakfast+"\n"+
                "is lunch: "+IsLunch+"\n"+
                "is dinner: "+IsDinner+"\n"+
                "Recipe category: "+category+"\n"+
                "Image URL: "+ ImageUrl +"\n";
    }
}
