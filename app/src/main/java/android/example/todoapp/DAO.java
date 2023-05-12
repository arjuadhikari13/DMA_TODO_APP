package android.example.todoapp;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
// The @Dao annotation indicates that this interface is a DAO
@Dao
public interface DAO {
    // Define methods for performing database operations

//    @Query("SELECT * FROM TodoItem")
    @Query("SELECT * FROM TodoItem")
        // The @Query annotation specifies a SQL query to retrieve all TodoItems from the database

    LiveData<List<TodoItem>> getAll();
    // The @Insert annotation specifies a method to insert a TodoItem into the database
    @Insert
    void insert(TodoItem todoItem);
    // The @Delete annotation specifies a method to delete a TodoItem from the database
    @Delete
    void delete(TodoItem todoItem);
    // The @Update annotation specifies a method to update a TodoItem in the database
    @Update
    void update(TodoItem todoItem);
}
