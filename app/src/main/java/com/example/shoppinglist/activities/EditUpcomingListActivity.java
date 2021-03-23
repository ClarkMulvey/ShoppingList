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

import com.example.shoppinglist.CustomListViewAdapter;
import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;
import com.example.shoppinglist.ShoppingListItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_upcoming_trip);
        setTitle("Edit list");
        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();

        // instantiate data handler object
        this.data = new DataHandler();

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

        FloatingActionButton fab = findViewById(R.id.upcomingListTripFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addItemToUpcomingList(view);
                //test();

            }
        });
    }

    //TODO: Need to edit the code below to put the appropriate menu Item on this Activity

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

    public void addDefaultListToTrip() {
        Intent intent = new Intent(this, CreateUpcomingTripListActivity.class);
        intent.putExtra("listKey", this.upcomingKeys.get(this.arrayPosition).getKey());
        intent.putExtra("arrayPosition", this.arrayPosition);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);
        intent.putExtra("upcomingListItems", this.upcomingList);

        startActivity(intent);
        //startActivityForResult(intent, 2);
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
    }

    public void displayUpcomingList(ShoppingList list) {
        //instantiate custom adapter
        CustomListViewAdapter adapter = new CustomListViewAdapter(list, this, this.data, this.listKey);
        this.listView.setAdapter(adapter);
    }

    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

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