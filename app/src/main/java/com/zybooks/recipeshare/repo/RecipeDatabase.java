package com.zybooks.recipeshare.repo;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.zybooks.recipeshare.model.Recipe;
@Database(entities = {Recipe.class} ,version = 1)
public abstract class RecipeDatabase extends RoomDatabase {


        private static RecipeDatabase instance;

        public abstract RecipeDao recipeDao();

        public static synchronized RecipeDatabase getInstance(Context context)
        {
            if(instance == null)
            {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class,"recipe_database")
                        .addCallback(roomCallback)
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return instance;
        }


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

        }
    };

}
