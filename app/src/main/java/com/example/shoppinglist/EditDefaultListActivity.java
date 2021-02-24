package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;

public class EditDefaultListActivity extends AppCompatActivity {

    ShoppingListDefault defaultList;
    EditText itemName;
    EditText itemQuantity;
    ListView listView;
    // data handler object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_default_list);

        // instantiate data handler object

        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);

        this.defaultList = /*this.dataHandler.*/loadList("default");
        displayDefaultList();


    }

    public ShoppingListDefault loadList(String listName) {

        Gson gson = new Gson();

        SharedPreferences sharedPreferences = getSharedPreferences("com.example.shoppinglist", MODE_PRIVATE);

        String defaultListString = sharedPreferences.getString(listName, "");
        ShoppingListDefault loadedList = gson.fromJson(defaultListString, ShoppingListDefault.class);

        if (loadedList != null) {
            return loadedList;
        }

        return new ShoppingListDefault(listName);
    }

    public void displayDefaultList() {
        ArrayList<String> listPopulator = new ArrayList<>();
        for (int i = 0, max = this.defaultList.getItems().size(); i < max; i++) {
            listPopulator.add(this.defaultList.getItems().get(i).getQuantity() + " " + this.defaultList.getItems().get(i).getName());
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listPopulator);

        this.listView.setAdapter(arrayAdapter);
    }

    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

    public void addItemToDefaultList(View view) {
        this.defaultList.addItem(new ShoppingListItem(this.itemName.getText().toString(), Integer.parseInt(this.itemQuantity.getText().toString())));
        Toast.makeText(this, this.itemName.getText().toString() + " has been added to the list" , Toast.LENGTH_LONG).show();
        clearFields();
        displayDefaultList();
        /*this.dataHandler.setDefaultLists()*/
        /*this.dataHandler.*/saveList();
    }


    public void saveList() {

        Gson gson = new Gson();

        String listStorage = gson.toJson(this.defaultList);

        // Storing data into SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.shoppinglist", MODE_PRIVATE);

        // Creating an Editor object to edit(write to the file)
        SharedPreferences.Editor myEdit = sharedPreferences.edit();

        myEdit.putString(defaultList.getName(), listStorage);

        myEdit.apply();
    }
}