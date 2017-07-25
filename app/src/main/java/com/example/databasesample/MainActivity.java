package com.example.databasesample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.databasesample.model.Grocery;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddGroceryDialog();
            }
        });

        GroceryListAdapter groceryListAdapter = new GroceryListAdapter(getDummyData());

        RecyclerView recyclerGrocery = (RecyclerView) findViewById(R.id.recycler_grocery);
        recyclerGrocery.setLayoutManager(new LinearLayoutManager(this));
        recyclerGrocery.setAdapter(groceryListAdapter);
    }

    private List<Grocery> getDummyData() {
        List<Grocery> data = new ArrayList<>();
        data.add(new Grocery("Sabun", 1, "biji", false));
        data.add(new Grocery("Sampo", 3, "sachet", false));
        data.add(new Grocery("Pasta Gigi", 2, "biji", true));
        return data;
    }

    private void showAddGroceryDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddGroceryDialogFragment dialogFragment = new AddGroceryDialogFragment();
        dialogFragment.show(fragmentManager, "fragment_add_grocery");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
