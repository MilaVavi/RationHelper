package com.mila.rationhelper.Helpers;

import com.mila.rationhelper.Database.RecipeEntity;

import java.util.HashMap;
import java.util.List;

public interface FirebaseRecipeReceiver {
    void updateRecipes(HashMap<String, List<RecipeEntity>> recipes);
}
