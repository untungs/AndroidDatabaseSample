package com.example.databasesample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.databasesample.model.Grocery;

import java.util.List;


public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private List<Grocery> groceries;

    public GroceryListAdapter(List<Grocery> groceries) {
        this.groceries = groceries;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grocery, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(groceries.get(position));
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkboxBought;
        private TextView textItem;
        private ImageButton buttonDelete;

        ViewHolder(View itemView) {
            super(itemView);
            checkboxBought = itemView.findViewById(R.id.checkbox_bought);
            textItem = itemView.findViewById(R.id.text_item);
            buttonDelete = itemView.findViewById(R.id.button_delete);
        }

        void bindItem(Grocery grocery) {
            checkboxBought.setChecked(grocery.bought);
            textItem.setText(grocery.name + " - " + grocery.amount + " " + grocery.unit);
        }
    }
}
