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
import com.example.shoppinglist.interfaces.CustomButtonListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This is the main activity for this application, and displays any upcoming shopping trip
 *
 * The user is able to select an upcoming trip to start the shopping trip, edit the upcoming trip,
 * or delete the trip.  Additionally the user can select the Default Lists menu item to go to be able
 * to manage the default lists
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class MainActivity extends AppCompatActivity {

    DataHandler data;
    DatabaseListAccess databaseListAccess;
    private String listKey;
    private ListView listView;
    private ArrayList<CustomMap> upcomingKeys;

    /**
     * OnCreate method - assigns all member vars, gets data, and populates the Views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.data = new DataHandler();

        this.databaseListAccess = new DatabaseListAccess();

        setTitle("Upcoming Trips");

        // get data
        readDataFromFirebase();

        // This will add the FAB which we are using to add a new upcomingList
        FloatingActionButton fab = findViewById(R.id.fabAddUpcomingList);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                addUpcomingList();
            }
        });

    }

    /**
     * This will read in the databaseListAccess object from the database
     * Currently we are only storing the name of the list and the key to access said list
     * We didn't want to pull all data down from Firebase if we aren't going to use all the data
     *
     */
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

    /**
     * If we have left we want fresh data, so get the data again
     */
    @Override
    protected void onResume(){
        super.onResume();
        readDataFromFirebase();

    }

    /**
     * Handles the menu item
     * We use this to go to ViewDefaultListsActivity
     * @param item
     * @return
     */
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


    /**
     * This is the button that we will click to get to ViewDefaultListsActivity
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_list, menu);
        return true;
    }

    /**
     * This is how we get to ViewDefaultListsActivity
     */
    public void clickViewDefaultLists () {
        Intent intent = new Intent(this, ViewDefaultListsActivity.class);
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivity(intent);
    }

    /**
     * When a user clicks one of the items in the listView containing all of the upcoming shopping lists
     * we will take them to the StartShoppingActivity
     * @param position
     */
    public void clickStartShopping (Integer position){
        Intent intent = new Intent(this, StartShoppingActivity.class);
        intent.putExtra("listKey", this.databaseListAccess.getUpcomingListKeys().get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivityForResult(intent, 1);
    }

    /**
     * When the user clicks the FAB this will be called - it will take the user
     * to the CreateUpcomingTripListActivity
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addUpcomingList() {
        Long instant = System.currentTimeMillis();
        String newKey = "Upcoming-" + instant.toString();

        // create a new upcomingList entry in the databaseListAccess
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
        // notifies CreateUpcomingTripListActivity where the user came from
        intent.putExtra("origin", "MainActivity");

        startActivityForResult(intent, 2);
    }

    /**
     * This will display a list of the upcoming lists with their name, an edit button, and a delete button
     * @param defaultKeys
     */
    public void displayUpcomingList(ArrayList<CustomMap> defaultKeys) {
        CustomButtonListener customListener = new CustomButtonListener() {
            @Override
            public void clickEditList(Integer position) {
                clickEditUpcomingList(position);
            }
        };

        //instantiate custom adapter
        CustomShoppingListKeysListViewAdapter adapter = new CustomShoppingListKeysListViewAdapter(defaultKeys, this, this.data, this.databaseListAccess, listKey, customListener);
        this.listView.setAdapter(adapter);

        this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            // When the item is clicked startShopping
            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                clickStartShopping(position);
            }

        });


    }

    /**
     * This is the method we call when the user clicks on one of the edit buttons next to the
     * upcoming list name in the listView
     * This will take the user to the EditUpcomingListActivity
     * @param position
     */
    public void clickEditUpcomingList(Integer position) {
        Intent intent = new Intent(this, EditUpcomingListActivity.class);
        intent.putExtra("listKey", this.databaseListAccess.getUpcomingListKeys().get(position).getKey());
        intent.putExtra("arrayPosition", position);
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivityForResult(intent, 1);

    }

    /**
     * This is when we are coming back with a result, we will be dealing with new databaseListAccess items
     * We don't need to use any kind of database call because regardless if we got the name and key of a new list
     * from the database or from another activity, the act of getting the information for that list
     * is the exact same.
     *
     * Currently we have two separate result codes that do the exact same thing.
     * We are leaving this as is because it doesn't hurt anything, and we may want to refactor in the future
     * @param requestCode
     * @param resultCode
     * @param data
     */
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
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                this.databaseListAccess = (DatabaseListAccess) data.getSerializableExtra("databaseListAccess");
                this.listKey = this.databaseListAccess.getMainKey();
                this.upcomingKeys = this.databaseListAccess.getUpcomingListKeys();
                displayUpcomingList(this.upcomingKeys);
            }
        }
    }

}