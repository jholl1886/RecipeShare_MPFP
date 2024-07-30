package com.zybooks.recipeshare;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity implements DeleteConfirmDialogFragment.ConfirmDeleteListener {
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
        recipeAdapter = new RecipeAdapter(this, recipeList);
        RecipeListView.setAdapter(recipeAdapter);
    }


    public void addClick() {
        Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
        startActivity(intent);
    }


    // Adapter class for RecyclerView
    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder>{

        private final List<Recipe> recipeList;
        private final AppCompatActivity activityContext; //added to pass into Recipe Adapter so we can directly use the activty context without itemView.getContext()

        public RecipeAdapter(AppCompatActivity context, List<Recipe> recipes) { //needed to change recipe adapter
            activityContext = context;
            recipeList = recipes;
        }
        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            LayoutInflater layoutInflater = LayoutInflater.from(activityContext);
            return new RecipeHolder(layoutInflater, parent, activityContext);
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
            implements View.OnClickListener,View.OnLongClickListener {
        private Recipe recipe;
        private final TextView recipeTextView;
        private Button DeleteButton;
        private final AppCompatActivity activityContext; //same deal here
        public RecipeHolder(LayoutInflater inflater, ViewGroup parent,AppCompatActivity context){ // with AppCompatActivity as a parameter ensures context is always the activity context we need
            super(inflater.inflate(R.layout.list_item_recipe, parent, false));
            activityContext = context;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            recipeTextView = itemView.findViewById(R.id.recipe_name);
            DeleteButton = itemView.findViewById(R.id.deleteButton);

            DeleteButton.setOnClickListener(v -> {
                DeleteConfirmDialogFragment dialog = new DeleteConfirmDialogFragment();
                dialog.SetRecipe(recipe, recipeViewModel);
                dialog.show(activityContext.getSupportFragmentManager(), "ConfirmDeletion");
            });
        }

        public void bind(Recipe recipeBind, int position){
            recipe = recipeBind;
            recipeTextView.setText(recipe.getName());
        }
        public void onClick(View view){
            Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
            startActivity(intent);
        }
        @Override
        public boolean onLongClick(View view) {
            Intent intent = new Intent(activityContext, RecipeViewActivity.class);
            intent.putExtra("recipeId", recipe.getId());
            activityContext.startActivity(intent);
            return true;
        }

    }

    @Override
    public void onDeleteConfirmed(Recipe recipe){
        Log.e("Delete called", "Delete was called");
        recipeViewModel.delete(recipe);
    }
}