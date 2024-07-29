package com.zybooks.recipeshare;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.zybooks.recipeshare.model.Recipe;
import com.zybooks.recipeshare.viewmodel.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddRecipeActivity extends AppCompatActivity {

    private Button backButton;
    private Button doneButton;
    private EditText numIngredientsEditText;
    private EditText numStepsEditText;
    private LinearLayout ingredientsContainer;
    private LinearLayout stepsContainer;

    private RecipeViewModel recipeViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.recipe_detail);

        backButton = findViewById(R.id.backButton);
        doneButton = findViewById(R.id.doneButton);

        numIngredientsEditText = findViewById(R.id.numIngredientsEditText);
        numStepsEditText = findViewById(R.id.numStepsEditText);
        ingredientsContainer = findViewById(R.id.ingredientsContainer);
        stepsContainer = findViewById(R.id.stepsContainer);

        backButton.setOnClickListener(v -> backButtonClick());
        doneButton.setOnClickListener(v -> doneButtonClick());
        numIngredientsEditText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addDynamicFields(ingredientsContainer, "Ingredient", s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
        numStepsEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                addDynamicFields(stepsContainer, "Step", s);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });





    }

    public void backButtonClick() {
        finish();
    }

    public void doneButtonClick()
    {
        String recipeName = ((EditText) findViewById(R.id.NameEditText)).getText().toString();
        int newId = recipeViewModel.nextId();
        int numIngredients;
        int numSteps;
        try
        {
            numIngredients = Integer.parseInt(((EditText) findViewById(R.id.numIngredientsEditText)).getText().toString());
            numSteps = Integer.parseInt(((EditText) findViewById(R.id.numStepsEditText)).getText().toString());
        }
        catch (NumberFormatException e)
        {
            //only allow numbers anyway but wont let them not fill it out
            Toast.makeText(this, "Please enter valid numbers for ingredients and steps.", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> ingredients = new ArrayList<>();
        for (int i = newId; i < numIngredients; i++) {
            EditText ingredientEditText = (EditText) ingredientsContainer.getChildAt(i);
            ingredients.add(ingredientEditText.getText().toString());
        }
        List<String> steps = new ArrayList<>();
        for (int i = 0; i < numSteps; i++) {
            EditText stepEditText = (EditText) stepsContainer.getChildAt(i);
            steps.add(stepEditText.getText().toString());
        }

        Recipe recipe = new Recipe(newId, recipeName, numIngredients, numSteps, ingredients, steps);
        recipeViewModel.insert(recipe);
        Toast.makeText(this, "Recipe saved to DataBase!", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void addDynamicFields(LinearLayout container, String prefix, CharSequence count) {
        container.removeAllViews();
        int num = 0;
        try {
            num = Integer.parseInt(count.toString());
        } catch (NumberFormatException e) {
            // Handle if the input is not a valid number
        }

        for (int i = 0; i < num; i++) {
            EditText editText = new EditText(this);
            editText.setHint(prefix + " " + (i + 1));
            container.addView(editText);
        }
    }




}
