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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_upcoming_trip_list);
        setTitle("Upcoming trip");

        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        actionBar.setDisplayHomeAsUpEnabled(true);

        this.databaseListAccess = (DatabaseListAccess) getIntent().getSerializableExtra("databaseListAccess");
        this.defaultKeys = this.databaseListAccess.getDefaultListKeys();

        this.defaultListChecked = new ArrayList<>();


        // instantiate data handler object
        this.data = new DataHandler();

        this.listView = (ListView) findViewById(R.id.defaultListsView);
        this.listName = (EditText) findViewById(R.id.upcomingListName);

        this.listKey = (String) getIntent().getStringExtra("listKey");
        this.arrayPosition = (Integer) getIntent().getIntExtra("arrayPosition", 0);


        this.listInfo = this.databaseListAccess.getUpcomingListKeys().get(this.arrayPosition);

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void populateUpcomingList (View view){

        Log.i("CreateUpcoming", "After Submit button is Pushed.  About to populate the upcoming List!");

        ArrayList<CustomMap> defaultLists = this.databaseListAccess.getDefaultListKeys();
        this.position = 0;

        //Iterate through the array of checked items to get the position of the Default List
        this.defaultListChecked.forEach(list -> {
            String defaultListKey = new String();
            if (list.booleanValue() == true) {
                defaultListKey = this.databaseListAccess.getDefaultListKeys().get(this.position).getKey();
                //Read the data from Firebase
                data.readData(defaultListItem -> {
                    defaultList = defaultListItem;
                    Log.i("reading Firebase", "Trying to read in a lamda");
                    defaultList.getItems().forEach(item -> {

                        //TODO: This doesn't work to check if the item already exists.  It is failing
                        // since it is looking for the object memory reference, which wouldn't be the
                        // same, we would need to be checking the values.  There are some posts
                        // about using stream for this

                        if (!this.newShoppingList.getItems().contains(item)) {
                            this.newShoppingList.addItem(item);
                        }
                        this.data.writeData(this.newShoppingList,this.listKey);
                    });
                }, defaultListKey);

            }
            this.position += 1;
        });

        Intent intent = new Intent(this, EditUpcomingListActivity.class);
        // Save the Upcoming List Name
        this.databaseListAccess.getUpcomingListKeys().get(this.arrayPosition).setValue(this.listName.getText().toString());
        this.data.writeListKeys(this.databaseListAccess, this.databaseListAccess.getMainKey());

        intent.putExtra("listKey", this.listKey);
        intent.putExtra("arrayPosition", arrayPosition);
        intent.putExtra("databaseListAccess", this.databaseListAccess);

        startActivity(intent);

    }

    public void displayDefaultLists(ArrayList<CustomMap> defaultKeys) {
        //instantiate custom adapter
        CustomDefaultListNamesListViewAdapter adapter = new CustomDefaultListNamesListViewAdapter(defaultKeys, this, this.data, this.databaseListAccess, listKey, this.defaultListChecked);
        this.listView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.deleteNewlyCreatedListAndGoBack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void deleteNewlyCreatedListAndGoBack() {
        this.databaseListAccess.getUpcomingListKeys().remove(this.databaseListAccess.getUpcomingListKeys().size() - 1);
        this.data.writeListKeys(this.databaseListAccess, this.databaseListAccess.getMainKey());


        Intent returnIntent = new Intent();
        returnIntent.putExtra("databaseListAccess", this.databaseListAccess);
        setResult(RESULT_OK,returnIntent);
        this.finish();

    }
}