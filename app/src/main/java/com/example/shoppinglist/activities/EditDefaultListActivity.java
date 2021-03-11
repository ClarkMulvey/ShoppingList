package com.example.shoppinglist.activities;

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
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomListViewAdapter;
import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;
import com.example.shoppinglist.ShoppingListItem;

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
        this.listName = (EditText) findViewById(R.id.listName);

        //Get the listKet from the Intent Extras, in order to pull the correct data from the DB
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


    }

    //Handles the Menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.save_list:
                try {
                    data.writeData(this.defaultList, this.listKey);
                    Toast.makeText(this, "The shopping list has been saved.", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Toast.makeText(this, "There was an error saving the shopping list.", Toast.LENGTH_LONG).show();
                    Log.e("EditDefaultListAct",e.toString());
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

    @Override
    protected void onPause() {
        // call the superclass method first
        super.onPause();
        this.listInfo.setValue(this.listName.getText().toString());
        this.data.writeListKeys(this.databaseListAccess, this.databaseListAccess.getMainKey());
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