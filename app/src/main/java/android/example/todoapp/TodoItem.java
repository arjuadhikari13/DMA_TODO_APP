package android.example.todoapp;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.io.Serializable;

@Entity
public class TodoItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "item")
    private String item;

    @ColumnInfo(name = "isSelected", defaultValue = "false")
    @NonNull
    private boolean isSelected;

    // Getter and Setter methods for the id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter and Setter methods for the item text
    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    // Getter and Setter methods for the selected state
    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean selected) {
        this.isSelected = selected;
    }
}