package com.zybooks.recipeshare;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.zybooks.recipeshare.viewmodel.RecipeViewModel;
import com.zybooks.recipeshare.model.Recipe;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Button addButton;
    private RecipeViewModel recipeViewModel; //do this if you need the viewmodel anywhere else


    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       addButton = findViewById(R.id.AddButton);

       addButton.setOnClickListener(v -> addClick());

        // Initialize the ViewModel
        recipeViewModel = new ViewModelProvider(this).get(RecipeViewModel.class);

        // Observe the LiveData list of recipes


    }



    public void addClick() {
        Intent intent = new Intent(MainActivity.this, AddRecipeActivity.class);
        startActivity(intent);
    }
}