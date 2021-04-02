package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.CustomShoppingTripListViewAdapter;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;
import com.example.shoppinglist.ShoppingListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This Activity is where the user does the shopping.
 *
 * The user is able to click a checkbox to signify the item has been completed, delete the item,
 * add a new item or add more default lists to this trip.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class StartShoppingActivity extends AppCompatActivity {

    private ShoppingList upcomingList;
    private DataHandler data;

    private EditText itemName;
    private EditText itemQuantity;
    private ListView listView;
    private String listKey;
    private DatabaseListAccess databaseListAccess;
    private ArrayList<CustomMap> upcomingKeys;
    private CustomMap listInfo;
    private Integer arrayPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_shopping);

        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();

        // instantiate data handler object
        this.data = new DataHandler();

        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);

        //Get the listKey from the Intent Extras, in order to pull the correct data from the DB
        this.listKey = (String) getIntent().getStringExtra("listKey");
        this.arrayPosition =  (Integer) getIntent().getIntExtra("arrayPosition", 0);

        this.listInfo = this.databaseListAccess.getUpcomingListKeys().get(this.arrayPosition);
        /**
         * Once the list has been created set title with the name of the shopping list created.
         */
        setTitle(this.listInfo.getValue());


        //Read the data from Firebase
        data.readData(list -> {
            this.upcomingList = list;
            if (this.upcomingList == null) {
                this.upcomingList = new ShoppingList();
            }
            displayUpcomingList(this.upcomingList);
        }, listKey);

        FloatingActionButton fab = findViewById(R.id.upcomingListTripFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addItemToUpcomingList(view);
            }
        });

    }

    //Handles the Menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.add_default:
                addDefaultListToTrip();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Puts the menu item on the upper bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_default, menu);
        return true;
    }

    /**
     * This method will add the default trip data of the intent object
     * and pass them to the start shopping activity.
     */
    public void addDefaultListToTrip() {
        Intent intent = new Intent(this, CreateUpcomingTripListActivity.class);
        intent.putExtra("listKey", this.upcomingKeys.get(this.arrayPosition).getKey());
        intent.putExtra("arrayPosition", this.arrayPosition);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);
        intent.putExtra("upcomingListItems", this.upcomingList);
        intent.putExtra("origin", "StartShoppingActivity");

        startActivity(intent);

    }


    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("databaseListAccess", this.databaseListAccess);
        setResult(RESULT_OK,returnIntent);
    }

    /**
     * This method will display the upcoming list defined in the shopping list
     * the class CustomShoppingTripListViewAdapter creates the adapter used by the listview to display the shopping list.
     * @param list
     */
    public void displayUpcomingList(ShoppingList list) {
        //instantiate custom adapter
        CustomShoppingTripListViewAdapter adapter = new CustomShoppingTripListViewAdapter(list, this, this.data, this.listKey);
        this.listView.setAdapter(adapter);
    }

    /**
     * This method will clear the item name and quantity
     */
    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

    /**
     * This method will add item to the upcoming list
     * get the item names and quantity.
     * Create and exception to see if the shopping list has been saved, if not throw an exception that there was an error saving the shopping list.
     * @param view
     */
    public void addItemToUpcomingList(View view) {
        this.upcomingList.addItem(new ShoppingListItem(this.itemName.getText().toString(), Integer.parseInt(this.itemQuantity.getText().toString())));

        try {
            data.writeData(this.upcomingList, this.listKey);
            Toast.makeText(this, "The shopping list has been saved.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error saving the shopping list.", Toast.LENGTH_LONG).show();
            Log.i("Save Error:",e.toString());
        }

        clearFields();
        displayUpcomingList(this.upcomingList);
    }
}