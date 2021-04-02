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

/**
 * This Activity Allows the suer to edit a default list
 *
 * The User is able to change the name of the default list as well as add/remove items from the list
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_default_list);

        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.defaultKeys = this.databaseListAccess.getDefaultListKeys();

        // instantiate data handler object
        this.data = new DataHandler();

        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);
        this.listName = (EditText) findViewById(R.id.upcomingListName);

        //Get the listKey from the Intent Extras, in order to pull the correct data from the DB
        this.listKey = (String) getIntent().getStringExtra("listKey");
        this.arrayPosition =  (Integer) getIntent().getIntExtra("arrayPosition", 0);


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

        FloatingActionButton fab = findViewById(R.id.addItemsListFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addItemToDefaultList(view);
                //test();

            }
        });

    }

    //Handles the Menu item
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

    public void displayDefaultList(ShoppingList list) {
        //instantiate custom adapter
        CustomListViewAdapter adapter = new CustomListViewAdapter(list, this, this.data, this.listKey);
        this.listView.setAdapter(adapter);
    }

    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

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