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

    ArrayList<ShoppingListItem> defaultList;
    EditText itemName;
    EditText itemQuantity;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_default_list);

        this.itemName = (EditText) findViewById(R.id.ItemName);
        this.itemQuantity = (EditText) findViewById(R.id.ItemQuantity);
        this.listView = (ListView) findViewById(R.id.listView);

        defaultList = loadList();
        if (defaultList == null) {
            defaultList = new ArrayList<>();
        }
    }

    public ArrayList<ShoppingListItem> loadList() {
        return null;
    }

    public void displayDefaultList() {
        ArrayList<String> listPopulator = new ArrayList<>();

        for (int i = 0, max = this.defaultList.size(); i < max; i++) {
            listPopulator.add(this.defaultList.get(i).getQuantity() + " " + this.defaultList.get(i).getName());
        }

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listPopulator);

        this.listView.setAdapter(arrayAdapter);
    }

    public void clearFields() {
        this.itemName.setText("");
        this.itemQuantity.setText("");
    }

    public void addItemToDefaultList(View view) {
        this.defaultList.add(new ShoppingListItem(this.itemName.getText().toString(), Integer.parseInt(this.itemQuantity.getText().toString())));
        Toast.makeText(this, this.itemName.getText().toString() + " has been added to the list" , Toast.LENGTH_LONG).show();
        clearFields();
        displayDefaultList();
    }
}