package com.zybooks.recipeshare.viewmodel;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.zybooks.recipeshare.model.Recipe;
import com.zybooks.recipeshare.repo.RecipeRepository;
import java.util.List;
public class RecipeViewModel extends AndroidViewModel
{
    private RecipeRepository recipeRepository;
    private LiveData<List<Recipe>> allRecipes;

    public RecipeViewModel(Application application)
    {
        super(application);
        recipeRepository = new RecipeRepository(application);
        allRecipes = recipeRepository.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes()
    {
        return allRecipes;
    }

    public void insert(Recipe recipe)
    {
        recipeRepository.insert(recipe);
    }

    public void update(Recipe recipe)
    {
        recipeRepository.update(recipe);
    }

    public void delete(Recipe recipe)
    {
        recipeRepository.delete(recipe);
    }

    public LiveData<Recipe> getRecipeById(int id)
    {
        return recipeRepository.getRecipeById(id);
    }

    public LiveData<List<Recipe>> getRecipesByName(String name)
    {
        return recipeRepository.getRecipeByName(name);
    }

    public  int nextId()
    {
        // Assuming your RecipeRepository can access a method to get the max ID
        int maxId = recipeRepository.getMaxRecipeId();
        return maxId + 1;
    }

}
