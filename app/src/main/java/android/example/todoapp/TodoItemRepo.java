package android.example.todoapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.Callable;

public class TodoItemRepo {
    private Context context;
    private DAO todoItemDao;
    private LiveData<List<TodoItem>> todoItems;

    public TodoItemRepo(Application application) {
        // Create an instance of the Room database and get the DAO
        AppDatabase database = DatabaseClient.getInstance(application).getAppDatabase();
        todoItemDao = database.todoItemDAO();

        // Retrieve the list of todo items as LiveData
        todoItems = todoItemDao.getAll();
    }

    public void insert(TodoItem item) {
        // Execute the insertion operation in the background using AsyncTask
        new InsertNoteAsyncTask(todoItemDao).execute(item);
    }

    public void update(TodoItem item) {
        // Execute the update operation in the background using AsyncTask
        new UpdateNoteAsyncTask(todoItemDao).execute(item);
    }

    public void delete(TodoItem item) {
        // Execute the deletion operation in the background using AsyncTask
        new DeleteNoteAsyncTask(todoItemDao).execute(item);
    }

    public LiveData<List<TodoItem>> getAllItems() {
        // Return the LiveData containing the list of todo items
        return todoItems;
    }

    // AsyncTask to insert a todo item
    private static class InsertNoteAsyncTask extends AsyncTask<TodoItem, Void, Void> {
        private DAO todoItemDao;

        private InsertNoteAsyncTask(DAO todoItemDao) {
            this.todoItemDao = todoItemDao;
        }

        @Override
        protected Void doInBackground(TodoItem... todoItems) {
            // Perform the insert operation in the background thread
            todoItemDao.insert(todoItems[0]);
            return null;
        }
    }

    // AsyncTask to update a todo item
    private static class UpdateNoteAsyncTask extends AsyncTask<TodoItem, Void, Void> {
        private DAO todoItemDao;

        private UpdateNoteAsyncTask(DAO todoItemDao) {
            this.todoItemDao = todoItemDao;
        }

        @Override
        protected Void doInBackground(TodoItem... todoItems) {
            // Perform the update operation in the background thread
            todoItemDao.update(todoItems[0]);
            return null;
        }
    }

    // AsyncTask to delete a todo item
    private static class DeleteNoteAsyncTask extends AsyncTask<TodoItem, Void, Void> {
        private DAO todoItemDao;

        private DeleteNoteAsyncTask(DAO todoItemDao) {
            this.todoItemDao = todoItemDao;
        }

        @Override
        protected Void doInBackground(TodoItem... todoItems) {
            // Perform the delete operation in the background thread
            todoItemDao.delete(todoItems[0]);
            return null;
        }
    }
}
