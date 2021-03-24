package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.CustomShoppingListKeysListViewAdapter;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataHandler data;
    DatabaseListAccess databaseListAccess;
    private String listKey;
    private ListView listView;
    private ArrayList<CustomMap> upcomingKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.data = new DataHandler();

        this.databaseListAccess = new DatabaseListAccess();

        setTitle("Upcoming Trips");

        readDataFromFirebase();

        FloatingActionButton fab = findViewById(R.id.fabAddUpcomingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addUpcomingList();
                //test();

            }
        });

    }

    /*
    public void test() {
        Intent intent = new Intent(this, ShoppingSessionActivity.class);

        startActivity(intent);
    }*/

    public void readDataFromFirebase(){
        //Read the data from Firebase
        data.getListKeys(databaseListAccessObj -> {
                    if (databaseListAccessObj != null) {
                        if (databaseListAccessObj.getDefaultListKeys() != null) {
                            this.databaseListAccess.setDefaultListKeys(databaseListAccessObj.getDefaultListKeys());
                        }
                        if (databaseListAccessObj.getUpcomingListKeys() != null) {
                            this.databaseListAccess.setUpcomingListKeys(databaseListAccessObj.getUpcomingListKeys());
                        }
                    }

                    if (this.databaseListAccess.getDefaultListKeys() == null) {
                        this.databaseListAccess.setDefaultListKeys(new ArrayList<>());

                        this.databaseListAccess.getDefaultListKeys().add(new CustomMap("default-1", "Default List"));
                        this.data.writeListKeys(databaseListAccess, databaseListAccess.getMainKey());
                    }

                    if (this.databaseListAccess.getUpcomingListKeys() == null) {
                        this.databaseListAccess.setUpcomingListKeys(new ArrayList<>());
                    }
            this.listKey = this.databaseListAccess.getMainKey();

            this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();

            this.listView = (ListView) findViewById(R.id.upcomingListView);

            if (this.upcomingKeys != null) {
                displayUpcomingList(this.upcomingKeys);
            }

        }, databaseListAccess.getMainKey());
    }

    @Override
    protected void onResume(){
        super.onResume();
        readDataFromFirebase();

    }


    //Handles the Menu item
    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.default_lists:
                clickViewDefaultLists();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //Puts the menu item on the upper bar
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_list, menu);
        return true;
    }

    /*
    public void clickViewEditDefaultList (View view){
        Intent intent = new Intent(this, EditDefaultListActivity.class);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());

        startActivityForResult(intent, 1);
        //startActivity(intent);

    }
    */

    public void clickViewDefaultLists () {
        Intent intent = new Intent(this, ViewDefaultListsActivity.class);
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        //startActivityForResult(intent, 2);
        startActivity(intent);

    }

    public void clickStartShopping (View view){
        Intent intent = new Intent(this, StartShoppingActivity.class);
        startActivity(intent);
    }

    public void clickCreateUpcomingTripList (View view){
        Intent intent = new Intent(this, CreateUpcomingTripListActivity.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addUpcomingList() {
        Long instant = System.currentTimeMillis();
        String newKey = "Upcoming-" + instant.toString();

        this.databaseListAccess.getUpcomingListKeys().add(new CustomMap(newKey, ""));
        Integer position = this.databaseListAccess.getUpcomingListKeys().size() - 1;

        try {
            this.data.writeListKeys(databaseListAccess, databaseListAccess.getMainKey());
            Toast.makeText(this, "The Upcoming shopping list has been added.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, "There was an error adding the upcoming shopping list.", Toast.LENGTH_LONG).show();
            Log.i("Save Error:",e.toString());
        }


        Intent intent = new Intent(this, CreateUpcomingTripListActivity.class);
        intent.putExtra("listKey", this.upcomingKeys.get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivityForResult(intent, 2);
        //startActivity(intent);

    }


    public void clickViewEditUpcomingList(Integer position) {

        //TODO: This should really go to the start shopping activity for the specific list
        Intent intent = new Intent(this, EditUpcomingListActivity.class);
        intent.putExtra("listKey", this.upcomingKeys.get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("defaultKeys", this.databaseListAccess.getUpcomingListKeys());
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivity(intent);

    }

    public void displayUpcomingList(ArrayList<CustomMap> defaultKeys) {
        //instantiate custom adapter
        CustomShoppingListKeysListViewAdapter adapter = new CustomShoppingListKeysListViewAdapter(defaultKeys, this, this.data, this.databaseListAccess, listKey);
        this.listView.setAdapter(adapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                //TODO: Need to implement this Method, and then uncomment this
                clickEditUpcomingList(position);
            }

        });

    }

    public void clickEditUpcomingList (Integer position) {
        Intent intent = new Intent(this, EditUpcomingListActivity.class);
        intent.putExtra("listKey", this.databaseListAccess.getUpcomingListKeys().get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivityForResult(intent, 1);
        //startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //onActivityResult
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                this.databaseListAccess = (DatabaseListAccess) data.getSerializableExtra("databaseListAccess");
                this.listKey = this.databaseListAccess.getMainKey();
                this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();
                displayUpcomingList(this.upcomingKeys);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                this.databaseListAccess = (DatabaseListAccess) data.getSerializableExtra("databaseListAccess");
                this.listKey = this.databaseListAccess.getMainKey();
                this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();
                displayUpcomingList(this.upcomingKeys);
            }
            if (resultCode == RESULT_CANCELED) {
                //Do nothing?
            }
        }
    }
}