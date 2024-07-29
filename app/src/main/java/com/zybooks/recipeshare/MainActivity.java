package com.zybooks.recipeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.recipeshare.model.Recipe;
import com.zybooks.recipeshare.viewmodel.RecipeViewModel;


import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private RecipeViewModel recipeViewModel; //do this if you need the viewmodel anywhere else
    private RecyclerView RecipeListView; // used to view the recipes
    private RecipeAdapter recipeAdapter;
    private Boolean LoadRecipeList = true; // used for updating list of recipes

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       addButton = findViewById(R.id.AddButton);

       // initialize the recycler view
       RecipeListView = findViewById(R.id.recipeListView);
       RecyclerView.LayoutManager linearLayoutManager =
               new LinearLayoutManager(getApplicationContext());
       RecipeListView.setLayoutManager(linearLayoutManager);
       addButton.setOnClickListener(v -> addClick());

        // Initialize the ViewModel
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        // Observe the LiveData list of recipes
        recipeViewModel.getAllRecipes().observe(this, allRecipes -> {
                if(LoadRecipeList){
                    updateUI(allRecipes);
                }
        } );
    }

    private void updateUI(List<Recipe> recipeList) {
       recipeAdapter = new RecipeAdapter(recipeList);
       RecipeListView.setAdapter(recipeAdapter);
    }


    public void addClick() {
        Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
        startActivity(intent);
    }


    // Adapter class for RecyclerView
    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder>{

        private final List<Recipe> recipeList;

        public RecipeAdapter(List<Recipe> recipes) { recipeList = recipes; }
        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
            return new RecipeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(RecipeHolder holder, int position){
            holder.bind(recipeList.get(position), position);
        }

        @Override
        public int getItemCount() {return recipeList.size(); }

        public void addRecipe(Recipe recipe){
            recipeList.add(0, recipe);
            notifyItemInserted(0);

        }

    }

    // Holder class for RecyclerView
    private class RecipeHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private Recipe recipe;
        private final TextView recipeTextView;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            itemView.setOnClickListener(this);
            recipeTextView = itemView.findViewById(R.id.recipe_name);
        }

        public void bind(Recipe recipeBind, int position){
            recipe = recipeBind;
            recipeTextView.setText(recipe.getName());
        }
        public void onClick(View view){
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        }
    }

}