package com.example.databasesample;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import java.util.Collections;
import java.util.List;

import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity implements AddGroceryDialogFragment.OnDialogDismissListener {

    private GroceryListAdapter groceryListAdapter;

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

        groceryListAdapter = new GroceryListAdapter(Collections.<Grocery>emptyList());

        RecyclerView recyclerGrocery = (RecyclerView) findViewById(R.id.recycler_grocery);
        recyclerGrocery.setLayoutManager(new LinearLayoutManager(this));
        recyclerGrocery.setAdapter(groceryListAdapter);

        showData();
    }

    @Override
    public void onDialogDismiss() {
        showData();
    }

    private void showData() {
        SQLiteDatabase db = ((MyApp) getApplication()).getDb();
        Cursor groceries = cupboard().withDatabase(db).query(Grocery.class).getCursor();
        List<Grocery> data = new ArrayList<>();
        try {
            QueryResultIterable<Grocery> itr = cupboard().withCursor(groceries).iterate(Grocery.class);
            data.addAll(itr.list());
        } finally {
            groceries.close();
        }
        groceryListAdapter.setGroceries(data);
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
