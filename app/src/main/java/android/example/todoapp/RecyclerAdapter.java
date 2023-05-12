package android.example.todoapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    Context context;
    List<TodoItem> todoItems;
    TodoItemViewModel viewModel;

    // Constructor to initialize the adapter
    public RecyclerAdapter(Context context, List<TodoItem> todoItems, TodoItemViewModel model) {
        this.context = context;
        this.todoItems = todoItems;
        this.viewModel = model;
    }

    // Creating and inflating the view holder
    @Override
    public RecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_row, parent, false);
        return new MyViewHolder(view);
    }

    // Binding data to the view holder
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.MyViewHolder holder, int position) {
        // Set the text and checked state of the CheckBox based on the todo item
        holder.todoItemCheckBox.setText(todoItems.get(position).getItem().toString());
        holder.todoItemCheckBox.setChecked(todoItems.get(position).getIsSelected());

        // Apply strikethrough text if the item is selected
        if (todoItems.get(position).getIsSelected())
            holder.todoItemCheckBox.setPaintFlags(holder.todoItemCheckBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        else
            holder.todoItemCheckBox.setPaintFlags(holder.todoItemCheckBox.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
    }

    // Getting the item count
    @Override
    public int getItemCount() {
        if (todoItems != null) {
            return todoItems.size();
        }
        return 0;
    }

    // View holder class
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        CheckBox todoItemCheckBox;

        // Constructor to initialize the view holder
        public MyViewHolder(View itemView) {
            super(itemView);
            // Get references to the views in the card layout
            todoItemCheckBox = (CheckBox) itemView.findViewById(R.id.title);
            todoItemCheckBox.setClickable(false);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        // Click listener for toggling the selected state of an item
        @Override
        public void onClick(View view) {
            // Get the clicked item
            TodoItem item = todoItems.get(getAdapterPosition());

            // Toggle the selected state of the item
            if (!item.getIsSelected())
                item.setIsSelected(true);
            else
                item.setIsSelected(false);

            // Update the item in the view model
            viewModel.update(item);
        }

        // Long click listener for updating an item
        @Override
        public boolean onLongClick(View view) {
            // Get the long-clicked item
            TodoItem item = todoItems.get(getAdapterPosition());

            // Show the update dialog fragment using the fragment manager
            FragmentManager fm = ((FragmentActivity) context).getSupportFragmentManager();
            UpdateTodoItemFragment updateTodoItemFragment = UpdateTodoItemFragment.newInstance("Update Todo Item", item);
            updateTodoItemFragment.show(fm, "update_todo_item");

            // Return true to indicate that the long click event is consumed
            return true;
        }
    }


}
