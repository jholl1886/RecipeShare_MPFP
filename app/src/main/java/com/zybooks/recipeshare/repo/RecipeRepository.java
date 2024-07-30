package com.zybooks.recipeshare.repo;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zybooks.recipeshare.model.Recipe;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class RecipeRepository {
        private RecipeDao recipeDao;
        private LiveData<List<Recipe>> allRecipes;

        private ExecutorService executorService;

    private static final int NUMBER_OF_THREADS = 4;
    private static final ExecutorService mDatabaseExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        public RecipeRepository(Application application)
        {
            RecipeDatabase database = RecipeDatabase.getInstance(application);

            recipeDao = database.recipeDao();
            allRecipes = recipeDao.getAllRecipes();
            executorService = Executors.newFixedThreadPool(2);
            executorService.execute(() -> addStarterData());
        }

        public void insert(Recipe recipe)
        {
            executorService.execute(() -> recipeDao.insert(recipe));
        }

        public void update (Recipe recipe)
        {
            executorService.execute(() -> recipeDao.update(recipe));
        }

        public void delete(Recipe recipe)
        {
            executorService.execute(() -> recipeDao.delete(recipe));
        }

        public LiveData<List<Recipe>> getAllRecipes()
        {
            return allRecipes;
        }

        public LiveData<Recipe> getRecipeById(int id)
        {
            return recipeDao.getRecipeById(id);
        }

        public LiveData<List<Recipe>> getRecipeByName(String name)
        {
            return recipeDao.getRecipesByName(name);
        }

        public int getMaxRecipeId()
        {
            Integer maxId = recipeDao.getMaxRecipeId();
            return maxId != null ? maxId : 0;
        }

    RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            mDatabaseExecutor.execute(() -> addStarterData());
        }
    };
    private void addStarterData()
    {
        Recipe recipe1 = new Recipe(1, "Pancakes", 3, 4,
                Arrays.asList("Flour", "Eggs", "Milk"),
                Arrays.asList("Mix ingredients", "Heat pan", "Pour batter", "Flip pancake"));
        Recipe recipe2 = new Recipe(2, "Salad", 4, 3,
                Arrays.asList("Lettuce", "Tomatoes", "Cucumbers", "Dressing"),
                Arrays.asList("Chop veggies", "Mix veggies", "Add dressing"));

        insert(recipe1);
        insert(recipe2);
    }
}
