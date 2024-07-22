package com.zybooks.recipeshare;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class AddRecipeActivity extends AppCompatActivity {

    private Button backButton;
    private Button doneButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_detail);

        backButton = findViewById(R.id.backButton);
        doneButton = findViewById(R.id.doneButton);

        backButton.setOnClickListener(v -> backButtonClick());
        doneButton.setOnClickListener(v -> doneButtonClick());

    }

    public void backButtonClick() {
        finish();
    }

    public void doneButtonClick() {
        finish();
    }


}
