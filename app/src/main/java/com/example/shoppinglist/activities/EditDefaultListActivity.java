package com.example.shoppinglist.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.shoppinglist.CustomListViewAdapter;
import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;
import com.example.shoppinglist.ShoppingListItem;

import java.util.ArrayList;


public class EditDefaultListActivity extends AppCompatActivity {

    private ShoppingList defaultList;
    private DataHandler data;
    private ArrayList<CustomMap> defaultKeys;

    private EditText itemName;
    private EditText itemQuantity;
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_default_list);


        // instantiate the defaultKeys list
        this.defaultKeys = (ArrayList<CustomMap>) getIntent().getSerializableExtra("defaultKeys");

        // instantiate data handler object
        this.data = new DataHandler();

        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);

        String listKey = "defaultList";

        //Read the data from Firebase
        data.readData(list -> {
            this.defaultList = list;
            if (this.defaultList == null) {
                this.defaultList = new ShoppingList();
            }
            displayDefaultList(this.defaultList);
        }, listKey);


    }

    //Handles the Menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.save_list:
                try {
                    data.writeData(this.defaultList, "defaultList");
                    Toast.makeText(this, "The shopping list has been saved.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "There was an error saving the shopping list.", Toast.LENGTH_LONG).show();
                    Log.i("Save Error:",e.toString());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //Puts the menu item on the upper bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    public void displayDefaultList(ShoppingList list) {
        //instantiate custom adapter
        CustomListViewAdapter adapter = new CustomListViewAdapter(list, this, this.data);
        this.listView.setAdapter(adapter);
    }

    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

    public void addItemToDefaultList(View view) {
        this.defaultList.addItem(new ShoppingListItem(this.itemName.getText().toString(), Integer.parseInt(this.itemQuantity.getText().toString())));

        //TODO: Clark should we be saving the list everytime they add an item?
        try {
            data.writeData(this.defaultList, "defaultList");
            Toast.makeText(this, "The shopping list has been saved.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error saving the shopping list.", Toast.LENGTH_LONG).show();
            Log.i("Save Error:",e.toString());
        }
        //Toast.makeText(this, this.itemName.getText().toString() + " has been added to the list" , Toast.LENGTH_LONG).show();
        clearFields();
        displayDefaultList(this.defaultList);
    }

    // TODO: This is no longer used, we should be able to delete it
    public void saveListActivity(View view) {
        try {
            data.writeData(this.defaultList, "defaultList");
            Toast.makeText(this, "The shopping list has been saved.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error saving the shopping list.", Toast.LENGTH_LONG).show();
            Log.i("Save Error:",e.toString());
        }
    }
}