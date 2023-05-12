package android.example.todoapp;

import androidx.room.Database;
import androidx.room.RoomDatabase;
// Define the database using the @Database annotation
@Database(entities = {TodoItem.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    // Declare an abstract method that returns the DAO (Data Access Object) for TodoItem
    public abstract DAO todoItemDAO();
}
