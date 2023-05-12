package android.example.todoapp;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddTodoItemFragment extends DialogFragment {
    private EditText editItemTitle;
    // Default constructor for the fragment
    public AddTodoItemFragment() {
    }
    // Static method to create a new instance of the fragment with a title parameter
    public static AddTodoItemFragment newInstance(String title) {
        AddTodoItemFragment frag = new AddTodoItemFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }
    // Inflates the fragment's layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_todo_item, container);
    }
    // Called after the view has been created
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Initialize the EditText for the item title
        editItemTitle = view.findViewById(R.id.editTextTitle);
        editItemTitle.requestFocus();
        // Set up the save button click listener
        Button saveButton = view.findViewById(R.id.button_save);
        // Set up the close button click listener
        ImageView closeBtn = view.findViewById(R.id.closeBtn);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
                dismiss();
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        // Get the dialog and configure its properties
        Dialog dialog = getDialog();
        dialog.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    // Method to save the task/item
    private void saveTask() {
        final String item = editItemTitle.getText().toString().trim();
        // Validate that the item is not empty
        if (item.isEmpty()) {
            editItemTitle.setError("Please Write ToDo Item!");
            editItemTitle.requestFocus();
            return;
        }
        // Create a new TodoItem and insert it into the ViewModel
        TodoItem todoItem = new TodoItem();
        todoItem.setItem(item);
        TodoItemViewModel todoItemViewModel = new ViewModelProvider(this).get(TodoItemViewModel.class);
        todoItemViewModel.insert(todoItem);
    }
}