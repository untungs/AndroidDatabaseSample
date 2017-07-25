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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.databasesample.model.Grocery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;
import nl.qbusict.cupboard.DatabaseCompartment;
import nl.qbusict.cupboard.QueryResultIterable;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

public class MainActivity extends AppCompatActivity implements
        AddGroceryDialogFragment.OnGroceryAddedListener,
        GroceryListAdapter.OnItemUpdatedListener {

    private DatabaseCompartment database;
    private GroceryListAdapter groceryListAdapter;
    private ImageView imageEmptyList;
    private TextView textEmptyList;

    private RecyclerView.AdapterDataObserver dataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            refreshEmptyView();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            refreshEmptyView();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            refreshEmptyView();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageEmptyList = (ImageView) findViewById(R.id.image_empty_list);
        textEmptyList = (TextView) findViewById(R.id.text_empty_list);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddGroceryDialog();
            }
        });

        groceryListAdapter = new GroceryListAdapter(Collections.<Grocery>emptyList(), this);
        groceryListAdapter.registerAdapterDataObserver(dataObserver);

        RecyclerView recyclerGrocery = (RecyclerView) findViewById(R.id.recycler_grocery);
        recyclerGrocery.setLayoutManager(new LinearLayoutManager(this));
        recyclerGrocery.setAdapter(groceryListAdapter);
        recyclerGrocery.setItemAnimator(new OvershootInRightAnimator());

        SQLiteDatabase db = ((MyApp) getApplication()).getDb();
        database = cupboard().withDatabase(db);

        showAllGroceries();
    }

    @Override
    public void onGroceryAdded(long id) {
        showNewGrocery(id);
    }

    private void showAllGroceries() {
        Cursor groceries = database.query(Grocery.class).getCursor();
        List<Grocery> data = new ArrayList<>();
        try {
            QueryResultIterable<Grocery> itr = cupboard().withCursor(groceries).iterate(Grocery.class);
            data.addAll(itr.list());
        } finally {
            groceries.close();
        }
        groceryListAdapter.setGroceries(data);
    }

    private void showNewGrocery(long id) {
        Grocery grocery = database.get(Grocery.class, id);
        groceryListAdapter.addGrocery(grocery);
    }

    private void showAddGroceryDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AddGroceryDialogFragment dialogFragment = new AddGroceryDialogFragment();
        dialogFragment.show(fragmentManager, "fragment_add_grocery");
    }

    @Override
    public void onItemUpdated(Grocery grocery) {
        database.put(grocery);
    }

    @Override
    public void onItemDeleted(Grocery grocery) {
        database.delete(grocery);
        groceryListAdapter.deleteGrocery(grocery);
    }

    private void refreshEmptyView() {
        int visibility = groceryListAdapter.getItemCount() > 0 ? View.GONE : View.VISIBLE;
        imageEmptyList.setVisibility(visibility);
        textEmptyList.setVisibility(visibility);
    }

    @Override
    protected void onDestroy() {
        groceryListAdapter.unregisterAdapterDataObserver(dataObserver);
        super.onDestroy();
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
