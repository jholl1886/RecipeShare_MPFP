package com.zybooks.recipeshare;

import android.content.Context;
import android.content.res.Resources;
import com.zybooks.recipeshare.TempRecipe;

import java.util.ArrayList;
import java.util.List;

public class TempRepo {
    private List<TempRecipe> RecipeList;
    private static TempRepo instance;

    public static TempRepo getInstance(Context context){
        if(instance == null){
            instance = new TempRepo(context);
        }
        return instance;
    }

    private TempRepo(Context context){
        RecipeList = new ArrayList<>();
        Resources res = context.getResources();
        String[] recipes = res.getStringArray(R.array.recipes);
        for(int i = 0; i < recipes.length; i++){
            RecipeList.add(new TempRecipe(i+1, recipes[i]));
        }
    }

    public List<TempRecipe> getRecipeList(){ return RecipeList; }

    public TempRecipe getRecipe(int recipeId){
        for(TempRecipe recipe: RecipeList){
            if(recipe.getId() == recipeId) {
                return recipe;
            }
        }
        return null;
    }


}
