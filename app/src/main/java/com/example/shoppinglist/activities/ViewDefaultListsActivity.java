package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.CustomShoppingListKeysListViewAdapter;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ViewDefaultListsActivity extends AppCompatActivity {

    private ArrayList<CustomMap> defaultKeys;
    private DataHandler data = new DataHandler();
    private String listKey;
    private ListView listView;
    private DatabaseListAccess databaseListAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_default_lists);

        // instantiate the defaultKeys list
        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");

        this.listKey = this.databaseListAccess.getMainKey();

        this.defaultKeys = this.databaseListAccess.getDefaultListKeys();

        this.listView = (ListView) findViewById(R.id.defaultListView);

        displayDefaultList(this.defaultKeys);

        FloatingActionButton fab = findViewById(R.id.fabAddDefaultList);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addDefaultList();
            }
        });

    }

    public void clickViewEditDefaultList(Integer position) {
        Intent intent = new Intent(this, EditDefaultListActivity.class);
        intent.putExtra("listKey", this.defaultKeys.get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivity(intent);

    }

    public void displayDefaultList(ArrayList<CustomMap> defaultKeys) {
        //instantiate custom adapter
        CustomShoppingListKeysListViewAdapter adapter = new CustomShoppingListKeysListViewAdapter(defaultKeys, this, this.data, this.databaseListAccess, listKey);
        this.listView.setAdapter(adapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                clickViewEditDefaultList(position);
            }

        });

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addDefaultList() {
        Long instant = System.currentTimeMillis();
        String newKey = "default-" + instant.toString();

        this.databaseListAccess.getDefaultListKeys().add(new CustomMap(newKey, ""));
        Integer position = this.databaseListAccess.getDefaultListKeys().size() - 1;

        try {
            this.data.writeListKeys(databaseListAccess, databaseListAccess.getMainKey());
            Toast.makeText(this, "The default shopping list has been added.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error adding the default shopping list.", Toast.LENGTH_LONG).show();
            Log.i("Save Error:",e.toString());
        }
        //Toast.makeText(this, this.itemName.getText().toString() + " has been added to the list" , Toast.LENGTH_LONG).show();
        //displayDefaultList(this.defaultKeys);
        Intent intent = new Intent(this, EditDefaultListActivity.class);
        intent.putExtra("listKey", this.defaultKeys.get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivity(intent);
    }


}
