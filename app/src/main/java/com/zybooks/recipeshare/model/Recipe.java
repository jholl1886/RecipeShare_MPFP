package com.zybooks.recipeshare.model;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.TypeConverters;
import com.zybooks.recipeshare.Converters;
import java.util.List;

@Entity(tableName = "Recipes")
@TypeConverters(Converters.class)
public class Recipe {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "recipe_id")
    private int id;

    private String name;
    private int numIngredients;
    private int numSteps;

    @ColumnInfo(name = "ingredients")
    private List<String> ingredients;

    @ColumnInfo(name = "steps")
    private List<String> steps;

    public Recipe(int id, String name, int numIngredients, int numSteps, List<String> ingredients, List<String> steps)
    {
        this.id = id;
        this.name = name;
        this.numIngredients = numIngredients;
        this.numSteps = numSteps;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    public Recipe(String nameText){
        this.name = nameText;
    }

    public Recipe()
    {

    }
    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumIngredients() {
        return numIngredients;
    }

    public void setNumIngredients(int numIngredients) {
        this.numIngredients = numIngredients;
    }

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }

}




