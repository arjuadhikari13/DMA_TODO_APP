package android.example.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private FloatingActionButton addTodoItemBtn;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    // Declare variables and views
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.todo_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ImageView openDrawerBtn = (ImageView) findViewById(R.id.drawerOpenBtn);
        openDrawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer = findViewById(R.id.drawer_layout);
                drawer.open();
                // Set the content view to the activity_main layout

                // Initialize views and set listeners

                // Get an instance of TodoItemViewModel and observe the LiveData
                // Update the RecyclerView adapter when data changes

                // Add ItemTouchHelper to handle swipe gestures on RecyclerView items
            }
            // Show the add dialog when the add button is clicked

            // Handle navigation item selection

        });
//This code initializes the NavigationView and sets its listener to the current activity.
// It then selects the first item in the navigation menu and checks it.
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().getItem(0).setChecked(true);
//This code initializes the "Add" button (addTodoItemBtn) and sets an OnClickListener for it.
// When the button is clicked, it calls the showAddDialog() method.
        addTodoItemBtn = findViewById(R.id.add_btn);
        addTodoItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });
//This code creates an instance of TodoItemViewModel using the ViewModelProvider and retrieves the list of TodoItem objects using the getTodoItems() method.
        // It observes changes in the todoItemList using a lambda expression and updates the RecyclerView with a new instance of RecyclerAdapter.

        TodoItemViewModel model = new ViewModelProvider(this).get(TodoItemViewModel.class);
        LiveData<List<TodoItem>> todoItemList = model.getTodoItems(getApplicationContext());
        todoItemList.observe(this, todoItems -> {
            recyclerAdapter = new RecyclerAdapter(MainActivity.this, todoItems, model);
            recyclerView.setAdapter(recyclerAdapter);
        });
        //Enables swipe-to-delete functionality for RecyclerView items.
        //Deletes the corresponding TodoItem from the database using TodoItemViewModel.
        //Shows a snack-bar to indicate the deletion.
         new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                TodoItem item = todoItemList.getValue().get(viewHolder.getAdapterPosition());
                TodoItemViewModel todoItemViewModel = new ViewModelProvider(MainActivity.this).get(TodoItemViewModel.class);
                todoItemViewModel.delete(item);
                Snackbar.make(recyclerView, item.getItem() + " removed!", Snackbar.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(recyclerView);

    }

    private void showAddDialog() {
        // Get the FragmentManager to manage the fragments
        FragmentManager fm = getSupportFragmentManager();
        // Create an instance of the AddTodoItemFragment with the title "Add Todo Item"
        AddTodoItemFragment addTodoItemFragment = AddTodoItemFragment.newInstance("Add Todo Item");
        // Show the fragment by adding it to the FragmentManager and assigning it a tag
        addTodoItemFragment.show(fm, "add_todo_item");
    }
    //The onNavigationItemSelected() method is an overridden method from the NavigationView.OnNavigationItemSelectedListener interface.
    //It handles the selection of items in the navigation drawer.
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Get the ID of the selected item
        int id = item.getItemId();

        // Check if the selected item is "nav_share"
        if (id == R.id.nav_share) {
            // Create an intent for sharing the app
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT,
                    "Hey check out my app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
        // Check if the selected item is "nav_contact"
        else if (id == R.id.nav_contact) {
            // Check if the app has the CALL_PHONE permission
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                // Request the CALL_PHONE permission if it's not granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 101);
                return false;
            }

            // Create an intent for making a phone call
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel: 9821911389"));
            startActivity(callIntent);
        }

        // Set the first item in the navigation menu as checked
        navigationView.getMenu().getItem(0).setChecked(true);

        // Close the navigation drawer
        drawer.closeDrawer(GravityCompat.START);

        // Return true to indicate that the item selection has been handled
        return true;
    }

}