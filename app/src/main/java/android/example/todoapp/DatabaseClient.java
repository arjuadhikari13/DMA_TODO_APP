package android.example.todoapp;

import androidx.room.Room;
import android.content.Context;

public class DatabaseClient {
    private Context context;
    private static DatabaseClient mInstance;
    private AppDatabase appDatabase;
    // Private constructor to prevent direct instantiation
    private DatabaseClient(Context context) {
        this.context = context;
        // Create the AppDatabase instance using Room
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "TodoItems")
                .fallbackToDestructiveMigration()
                .build();
    }
    // Method to get the singleton instance of DatabaseClient
    public static synchronized DatabaseClient getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(context);
        }
        return mInstance;
    }
    // Method to access the AppDatabase instance
    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
