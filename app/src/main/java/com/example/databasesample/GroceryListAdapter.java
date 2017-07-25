package com.example.databasesample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.databasesample.model.Grocery;

import java.util.List;


public class GroceryListAdapter extends RecyclerView.Adapter<GroceryListAdapter.ViewHolder> {
    private List<Grocery> groceries;
    private OnItemUpdatedListener itemUpdatedListener;

    public GroceryListAdapter(List<Grocery> groceries, OnItemUpdatedListener itemUpdatedListener) {
        this.groceries = groceries;
        this.itemUpdatedListener = itemUpdatedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grocery, parent, false);
        return new ViewHolder(view, itemUpdatedListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindItem(groceries.get(position));
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public void setGroceries(List<Grocery> groceries) {
        this.groceries = groceries;
        notifyDataSetChanged();
    }

    public void addGrocery(Grocery grocery) {
        this.groceries.add(grocery);
        notifyItemInserted(groceries.size() - 1);
    }

    public void deleteGrocery(Grocery grocery) {
        int position = this.groceries.indexOf(grocery);
        this.groceries.remove(grocery);
        notifyItemRemoved(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkboxBought;
        private TextView textItem;
        private ImageButton buttonDelete;

        private Grocery grocery = null;

        ViewHolder(View itemView, final OnItemUpdatedListener itemUpdatedListener) {
            super(itemView);
            checkboxBought = itemView.findViewById(R.id.checkbox_bought);
            textItem = itemView.findViewById(R.id.text_item);
            buttonDelete = itemView.findViewById(R.id.button_delete);

            checkboxBought.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (grocery != null) {
                        grocery.bought = isChecked;
                        itemUpdatedListener.onItemUpdated(grocery);
                    }
                }
            });

            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (grocery != null) {
                        itemUpdatedListener.onItemDeleted(grocery);
                    }
                }
            });
        }

        void bindItem(Grocery grocery) {
            this.grocery = grocery;
            checkboxBought.setChecked(grocery.bought);
            textItem.setText(grocery.name + " - " + grocery.amount + " " + grocery.unit);
        }
    }

    public interface OnItemUpdatedListener {
        void onItemUpdated(Grocery grocery);

        void onItemDeleted(Grocery grocery);
    }
}
