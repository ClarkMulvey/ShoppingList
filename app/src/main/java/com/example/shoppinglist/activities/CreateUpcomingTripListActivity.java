package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomDefaultListNamesListViewAdapter;
import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;
import com.example.shoppinglist.ShoppingList;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This Activity allows for the creation of an Upcoming Shopping Trip
 *
 * The user is able create a new upcoming shopping list and put in the name of the list.
 *  Additionally the user can choose default lists to be able to add to this new shopping list.
 *  We can access this activity from 3 separate activities MainActivity, EditUpcomingListActivity, and StartShoppingActivity
 *  We have code to handle which activity to return to/go to based on which activity we came from.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class CreateUpcomingTripListActivity extends AppCompatActivity {


    private DatabaseListAccess databaseListAccess;
    private ArrayList<CustomMap> defaultKeys;
    private DataHandler data;
    private ListView listView;
    private EditText listName;
    private String listKey;
    private Integer arrayPosition;
    private CustomMap listInfo;
    ArrayList<Boolean> defaultListChecked = new ArrayList<>();
    private Integer position;
    private ShoppingList defaultList;
    private ShoppingList newShoppingList;
    private ShoppingList upcomingList;
    private String originActivity;

    /**
     * OnCreate method - assigns all member vars, gets data, and populates the Views
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_upcoming_trip_list);
        setTitle("Upcoming trip list");

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        // set all member variables
        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.originActivity = (String) getIntent().getStringExtra("origin");
        this.defaultKeys = this.databaseListAccess.getDefaultListKeys();
        this.defaultListChecked = new ArrayList<>();
        // instantiate data handler object
        this.data = new DataHandler();

        // Grab elements on the page
        this.listView = (ListView) findViewById(R.id.defaultListsView);
        this.listName = (EditText) findViewById(R.id.upcomingListName);
        this.listKey = (String) getIntent().getStringExtra("listKey");
        this.arrayPosition = (Integer) getIntent().getIntExtra("arrayPosition", 0);

        // This is for easy access for which list we will be pointing to
        this.listInfo = this.databaseListAccess.getUpcomingListKeys().get(this.arrayPosition);

        // show the list name to the user
        this.listName.setText(this.listInfo.getValue());

        //Check to see if the ShoppingList has any items
        this.newShoppingList = (ShoppingList) getIntent().getSerializableExtra("upcomingListItems");
        if (this.newShoppingList == null) {
            //Creating a new ShoppingList in order to store all the items from the selected default
            //lists
            this.newShoppingList = new ShoppingList();
        }

        //instantiate custom adapter
        displayDefaultLists(this.defaultKeys);

    }

    /**************************************************************************
     * populateUpcomingList
     * This method will populate the upcoming list when the next button is clicked
     * The default lists that are checked will contain the data which will be added
     * To the upcoming list. We would like to note that the database is saved after each default
     * list is added to the upcoming list.
     * This is where we handle the logic of where we came from, and decide where to go
     * after we have populated the upcoming list
     *
     * @param view
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void populateUpcomingList (View view){
        ArrayList<CustomMap> defaultLists = this.databaseListAccess.getDefaultListKeys();
        this.position = 0;

        //Iterate through the array of checked items to get the position of the Default List
        this.defaultListChecked.forEach(list -> {
            String defaultListKey = new String();
            // If the checkbox is checked
            if (list.booleanValue() == true) {
                defaultListKey = this.databaseListAccess.getDefaultListKeys().get(this.position).getKey();
                //Read the data from Firebase
                data.readData(defaultListItem -> {
                    defaultList = defaultListItem;

                    // Make sure the default list exists
                    if (defaultList != null) {
                        defaultList.getItems().forEach(item -> {

                            //This block of code will check to see if the item is already added to the upcoming list, if it is
                            // It will not add it a second time. We don't want to have the user accidentally add more than one
                            // Copy of the default list.
                            AtomicReference<Boolean> found = new AtomicReference<>(false);
                            this.newShoppingList.getItems().forEach(checkItem -> {
                                // If the item is found, set found var to true
                                if (item.getName().equals(checkItem.getName()) && item.getQuantity().equals(checkItem.getQuantity())) {
                                    found.set(true);
                                }
                            });
                            // Only if not found will we add to upcoming list
                            if (!found.get()) {
                                this.newShoppingList.addItem(item);
                            }
                        });
                    }
                    this.data.writeData(this.newShoppingList, this.listKey);

                    // This block of code will handle which activity to go to next.
                    // If we came from main activity or editupcomingListActivity we want to return
                    // to editUpcomingListActivity
                    if (this.originActivity.equals("EditUpcomingListActivity") || this.originActivity.equals("MainActivity") ) {
                        Intent intent = new Intent(this, EditUpcomingListActivity.class);
                        intent.putExtra("listKey", this.listKey);
                        intent.putExtra("arrayPosition", arrayPosition);
                        intent.putExtra("databaseListAccess", this.databaseListAccess);

                        startActivity(intent);

                    }
                    // If we didn't come from either of those two then we know we came from startShoppingSessionActivity -
                    // And we want to return there when finished with this activity.
                    else {
                        Intent intent = new Intent(this, StartShoppingActivity.class);
                        intent.putExtra("listKey", this.listKey);
                        intent.putExtra("arrayPosition", arrayPosition);
                        intent.putExtra("databaseListAccess", this.databaseListAccess);

                        startActivity(intent);
                    }
                }, defaultListKey);

            }
            this.position += 1;
        });


        // Save the Upcoming List Name
        this.databaseListAccess.getUpcomingListKeys().get(this.arrayPosition).setValue(this.listName.getText().toString());
        this.data.writeListKeys(this.databaseListAccess, this.databaseListAccess.getMainKey());
    }

    /***
     * This method will use a custom list view adapter CustomDefaultListNamesListViewAdapter
     * in order to populate the listview on this page, this listview will have the list of all the
     * default lists and a checkbox next to them for the user to select which will indicate to the program
     * which default lists to add to the upcoming shopping list
     * @param defaultKeys
     */
    public void displayDefaultLists(ArrayList<CustomMap> defaultKeys) {
        //instantiate custom adapter
        CustomDefaultListNamesListViewAdapter adapter = new CustomDefaultListNamesListViewAdapter(defaultKeys, this, this.data, this.databaseListAccess, listKey, this.defaultListChecked);
        this.listView.setAdapter(adapter);
    }

    /**
     * Here we handle if we have entered the activity from MainActivity - if we have
     * Then we will delete the newly created upcoming list which was created when we entered this activity
     * This is because in order to create an upcoming shopping list it's a two step process - we did
     * not want the user to have to go through two full steps and then go back just to delete a mistakenly created list
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (this.originActivity.equals("MainActivity")) {
                    this.deleteNewlyCreatedListAndGoBack();
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("databaseListAccess", this.databaseListAccess);
                    setResult(RESULT_OK,returnIntent);
                    this.finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This is the method that deletes the newly created upcoming list and saves it to the database
     */
    protected void deleteNewlyCreatedListAndGoBack() {
        this.databaseListAccess.getUpcomingListKeys().remove(this.databaseListAccess.getUpcomingListKeys().size() - 1);
        this.data.writeListKeys(this.databaseListAccess, this.databaseListAccess.getMainKey());


        Intent returnIntent = new Intent();
        returnIntent.putExtra("databaseListAccess", this.databaseListAccess);
        setResult(RESULT_OK,returnIntent);
        this.finish();

    }
}