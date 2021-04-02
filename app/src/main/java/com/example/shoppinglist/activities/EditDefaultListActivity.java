package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomListViewAdapter;
import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;
import com.example.shoppinglist.ShoppingListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class EditDefaultListActivity extends AppCompatActivity {

    private ShoppingList defaultList;
    private DataHandler data;

    private EditText itemName;
    private EditText itemQuantity;
    private ListView listView;
    private String listKey;
    private EditText listName;
    private DatabaseListAccess databaseListAccess;
    private ArrayList<CustomMap> defaultKeys;
    private CustomMap listInfo;
    private Integer arrayPosition;


    /**
     * OnCreate method - assigns all member vars, gets data, and populates the Views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_default_list);

        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.defaultKeys = this.databaseListAccess.getDefaultListKeys();

        // instantiate data handler object
        this.data = new DataHandler();

        // get the Views on the page
        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listName = (EditText) findViewById(R.id.upcomingListName);

        //Get the listKey from the Intent Extras, in order to pull the correct data from the DB
        this.listKey = (String) getIntent().getStringExtra("listKey");
        this.arrayPosition =  (Integer) getIntent().getIntExtra("arrayPosition", 0);

        // This is for convenience in working with the list in question
        this.listInfo = this.databaseListAccess.getDefaultListKeys().get(this.arrayPosition);

        this.listName.setText(this.listInfo.getValue());

        //Read the data from Firebase
        data.readData(list -> {
            this.defaultList = list;
            if (this.defaultList == null) {
                this.defaultList = new ShoppingList();
            }
            displayDefaultList(this.defaultList);
        }, listKey);

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // This will add the FAB which we are using to add a new item to the DefaultList
        FloatingActionButton fab = findViewById(R.id.addItemsListFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addItemToDefaultList(view);
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
            case android.R.id.home:
                this.onPause();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
        this.finish();

    }

    /**
     * This method will use a custom list view adapter CustomListViewAdapter
     * in order to populate the listview on this page, this listview will have the list of all the
     * shopping list Items within this default list and a delete button which will delete
     * the item from the default shopping list
     * @param list
     */
    public void displayDefaultList(ShoppingList list) {
        //instantiate custom adapter
        CustomListViewAdapter adapter = new CustomListViewAdapter(list, this, this.data, this.listKey);
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
     * This will add an item to our default list, show a toast, clear the input fields, and re display
     * @param view
     */
    public void addItemToDefaultList(View view) {
        this.defaultList.addItem(new ShoppingListItem(this.itemName.getText().toString(), Integer.parseInt(this.itemQuantity.getText().toString())));

        try {
            data.writeData(this.defaultList, this.listKey);
            Toast.makeText(this, "The shopping list has been saved.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error saving the shopping list.", Toast.LENGTH_LONG).show();
            Log.i("Save Error:",e.toString());
        }

        clearFields();
        displayDefaultList(this.defaultList);
    }
}