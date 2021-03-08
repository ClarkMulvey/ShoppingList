package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.CustomShoppingListKeysListViewAdapter;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.R;

import java.util.ArrayList;

public class ViewDefaultListsActivity extends AppCompatActivity {

    private ArrayList<CustomMap> defaultKeys;
    private DataHandler data = new DataHandler();
    private String listKey;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_default_lists);

        // instantiate the defaultKeys list
        this.defaultKeys = (ArrayList<CustomMap>) getIntent().getSerializableExtra("defaultKeys");
        this.listKey = (String) getIntent().getStringExtra("listKey");

        this.listView = (ListView) findViewById(R.id.defaultListView);

        displayDefaultList(this.defaultKeys);

    }

    public void clickViewEditDefaultList(Integer position) {
        Intent intent = new Intent(this, EditDefaultListActivity.class);
        intent.putExtra("listKey", this.defaultKeys.get(position).getKey());

        startActivity(intent);

    }



    public void displayDefaultList(ArrayList<CustomMap> defaultKeys) {
        //instantiate custom adapter
        CustomShoppingListKeysListViewAdapter adapter = new CustomShoppingListKeysListViewAdapter(defaultKeys, this, this.data, listKey);
        this.listView.setAdapter(adapter);

        //TODO: Need to figure out why this isn't working.
        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                clickViewEditDefaultList(position);
            }

        });

    }

}
