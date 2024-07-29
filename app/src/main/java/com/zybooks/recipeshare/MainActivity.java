package com.zybooks.recipeshare;

import static androidx.core.content.ContentProviderCompat.requireContext;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private RecyclerView RecipeListView;
    private TempRepo recipeRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       addButton = findViewById(R.id.AddButton);
       RecipeListView = findViewById(R.id.recipeListView);

       recipeRepo = TempRepo.getInstance(this);
       List<TempRecipe> recipes = recipeRepo.getRecipeList();
       RecipeListView.setLayoutManager(new LinearLayoutManager(this));
       RecipeListView.setAdapter(new RecipeAdapter(recipes));

       addButton.setOnClickListener(v -> addClick());

    }
    public void addClick() {
        Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
        startActivity(intent);
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder> {
        private final List<TempRecipe> Recipes;
        public RecipeAdapter(List<TempRecipe> recipes){
            Recipes = recipes;
        }
        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new RecipeHolder(inflater, parent);
        }
        @Override
        public void onBindViewHolder(@NonNull RecipeHolder holder, int positon){
            TempRecipe recipe = Recipes.get(positon);
            holder.bind(recipe);
        }

        @Override
        public int getItemCount() {
            return Recipes.size();
        }

    }

    private static class RecipeHolder extends RecyclerView.ViewHolder {
        private final TextView NameTextView;

        public RecipeHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            NameTextView = itemView.findViewById(R.id.recipe_name);
        }

        public void bind(TempRecipe recipe){ NameTextView.setText(recipe.getName()); }
    }




}