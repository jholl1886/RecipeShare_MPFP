package com.zybooks.recipeshare;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.zybooks.recipeshare.model.Recipe;
import com.zybooks.recipeshare.viewmodel.RecipeViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

public class RecipeViewActivity extends AppCompatActivity {

    private Button backButton;
    private RecipeViewModel recipeViewModel;
    private TextView recipeNameTextView;
    private ListView ingredientsListView;
    private ListView stepsListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_view);

        recipeNameTextView = findViewById(R.id.recipe_name);
        ingredientsListView = findViewById(R.id.ingredients_list);
        stepsListView = findViewById(R.id.steps_list);

        Intent intent = getIntent();
        int recipeId = getIntent().getIntExtra("recipeId", -1);

        // Initialize the ViewModel
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        recipeViewModel.getRecipeById(recipeId);

        recipeViewModel.getRecipeById(recipeId).observe(this, recipe -> {
            if (recipe != null) {

                recipeNameTextView.setText(recipe.getName());
                updateListView(ingredientsListView, recipe.getIngredients());
                updateListView(stepsListView, recipe.getSteps());


            } else {

                finish();
            }
        });

        backButton = findViewById(R.id.back_to_main);
        backButton.setOnClickListener(v -> backButtonClick());
    }

    public void backButtonClick() {
        finish();
    }

    private void updateListView(ListView listView, List<String> items)
    {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);
    }
}
