package com.mila.rationhelper.Database;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.mila.rationhelper.Helpers.Constants;
import com.mila.rationhelper.Helpers.Converters;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {RecipeEntity.class, SugarRecordEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class LocalDatabase extends RoomDatabase{

    public abstract RecipeDAO recipeDAO();
    public abstract SugarRecordDAO sugarRecordDAO();

    private static volatile LocalDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static LocalDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LocalDatabase.class) {
                if (INSTANCE == null) {
                    Log.d("LocalDatabase","building database");
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            LocalDatabase.class, "objects_database")
                            // comment next line to avoid dummy data at install
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }



    /**
     * Override the onCreate method to populate the database.
     * For this sample, we clear the database every time it is created.
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        private String TAG = "DBCallback";
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.d(TAG, "on create");


        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            Log.d(TAG, "on open");

            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                RecipeDAO recipeDAO = INSTANCE.recipeDAO();
                SugarRecordDAO sugarRecordDAO = INSTANCE.sugarRecordDAO();
                recipeDAO.deleteAll();
                sugarRecordDAO.deleteAll();
                Log.d(TAG,"wiping database");

                RecipeEntity[] sampleRecipes = Constants.getSampleRecipes();
                for (RecipeEntity recipe : sampleRecipes)
                    recipeDAO.insert(recipe);

                SugarRecordEntity[] records = Constants.getSampleRecords();
                for (SugarRecordEntity record : records)
                    sugarRecordDAO.insert(record);


                Log.d(TAG, "populated local with defaults");

            });

            // uncomment to add set of dummy values to the firebase on application launch
//            Repository.populateFirebaseDummy();
//            Log.d(TAG, "populated remote with defaults");
        }
    };
}




