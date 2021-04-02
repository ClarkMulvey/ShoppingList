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
import com.example.shoppinglist.CustomUpcomingListViewAdapter;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;
import com.example.shoppinglist.ShoppingListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This Activity Allows the user to edit an upcoming shopping trip list
 *
 * The User is able to change the name of the upcoming shopping list as well as add/remove items
 * from the list.  Additionally the user is able select the menu item to be able to add additional
 * default lists to this shopping list
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class EditUpcomingListActivity extends AppCompatActivity {

    private ShoppingList upcomingList;
    private DataHandler data;

    private EditText itemName;
    private EditText itemQuantity;
    private ListView listView;
    private String listKey;
    private EditText listName;
    private DatabaseListAccess databaseListAccess;
    private ArrayList<CustomMap> upcomingKeys;
    private CustomMap listInfo;
    private Integer arrayPosition;

    /**
     * OnCreate method - assigns all member vars, gets data, and populates the Views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_upcoming_trip);
        setTitle("Edit Upcoming Trip");
        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();

        // instantiate data handler object
        this.data = new DataHandler();

        // grab the page Views
        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listName = (EditText) findViewById(R.id.upcomingListName);

        //Get the listKey from the Intent Extras, in order to pull the correct data from the DB
        this.listKey = (String) getIntent().getStringExtra("listKey");
        this.arrayPosition =  (Integer) getIntent().getIntExtra("arrayPosition", 0);

        this.listInfo = this.databaseListAccess.getUpcomingListKeys().get(this.arrayPosition);

        this.listName.setText(this.listInfo.getValue());


        //Read the data from Firebase
        data.readData(list -> {
            this.upcomingList = list;
            if (this.upcomingList == null) {
                this.upcomingList = new ShoppingList();
            }
            displayUpcomingList(this.upcomingList);
        }, listKey);

        // This will add the FAB which we are using to add a new item to the UpcomingList
        FloatingActionButton fab = findViewById(R.id.upcomingListTripFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addItemToUpcomingList(view);
            }
        });
    }

    /**
     * Handles the menu item
     * We use this to go back to the previous activity, utilizing the backstack of activities
     * @param item
     * @return
     */
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

    /**
     * This will add our button in the up right corner which allows the user
     * To view the CreateUpcomingTripListActivity, which will allow the user
     * to add additional defaultLists to this current upcomingList
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_default, menu);
        return true;
    }

    /**
     * This is the method that creates the intent and starts the CreateUpcomingTripListActivity
     */
    public void addDefaultListToTrip() {
        Intent intent = new Intent(this, CreateUpcomingTripListActivity.class);
        intent.putExtra("listKey", this.upcomingKeys.get(this.arrayPosition).getKey());
        intent.putExtra("arrayPosition", this.arrayPosition);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);
        intent.putExtra("upcomingListItems", this.upcomingList);
        // This line tells the createUpcomingTripListActivity where we came from
        intent.putExtra("origin", "EditUpcomingListActivity");

        startActivity(intent);
    }

    /**
     * When we are done with the activity we will save all information to the database
     * We return with an intent saying the result was okay, we also send back the newly updated
     * databaseListAccess information
     */
    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();
        this.listInfo.setValue(this.listName.getText().toString());
        this.data.writeListKeys(this.databaseListAccess, this.databaseListAccess.getMainKey());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("databaseListAccess", this.databaseListAccess);
        setResult(RESULT_OK,returnIntent);
    }

    /**
     * This will display the list of shopping list items and a delete button next to it so that
     * the user can delete the item
     * @param list
     */
    public void displayUpcomingList(ShoppingList list) {
        //instantiate custom adapter
        CustomUpcomingListViewAdapter adapter = new CustomUpcomingListViewAdapter(list, this, this.data, this.listKey);
        this.listView.setAdapter(adapter);
    }

    /**
     * When we have added an item to our list we want to delete the information from these text fields
     */
    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

    /**
     * This will add an item to our upcoming list, show a toast, clear the input fields, and re display
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