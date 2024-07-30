package com.zybooks.recipeshare;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.zybooks.recipeshare.model.Recipe;
import com.zybooks.recipeshare.viewmodel.RecipeViewModel;

public class DeleteConfirmDialogFragment extends DialogFragment {
    private RecipeViewModel recipeViewModel;
    private Recipe recipe;


    public interface ConfirmDeleteListener {
        void onDeleteConfirmed(Recipe recipe);
    }

    private ConfirmDeleteListener Listener;

    public void SetRecipe(Recipe recipe_passed, RecipeViewModel view) {
        recipeViewModel = view;
        recipe = recipe_passed;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstance) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to delete this recipe?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (recipe != null && recipeViewModel != null) {
                        Listener.onDeleteConfirmed(recipe);
                    }
                    dismiss();
                })
                .setNegativeButton("Cancel", (dialog, which) -> dismiss());

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Listener = (ConfirmDeleteListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Listener = null;
    }
}
