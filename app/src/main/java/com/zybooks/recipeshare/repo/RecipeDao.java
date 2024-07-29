package com.zybooks.recipeshare.repo;
import androidx.lifecycle.LiveData;
import androidx.room.*;
import java.util.List;
import com.zybooks.recipeshare.model.Recipe;
@Dao
public interface RecipeDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM Recipes")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM Recipes WHERE recipe_id = :id")
    LiveData<Recipe> getRecipeById(int id);

    @Query("SELECT * FROM Recipes WHERE name LIKE :name")
    LiveData<List<Recipe>> getRecipesByName(String name);


}
