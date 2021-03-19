package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_upcoming_trip_list);

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

        //Creating a new ShoppingList in order to store all the items from the selected default
        //lists
        this.newShoppingList = new ShoppingList();

        //instantiate custom adapter
        displayDefaultLists(this.defaultKeys);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void populateUpcomingList (View view){

        Log.i("CreateUpcoming", "After Submit button is Pushed.  About to populate the upcoming List!");
        //TODO: this.defaultListChecked is an Boolean array of the positions of the default lists,
        // we just need to iterate through that and add the contents of the checked default lists to the upcoming list

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
                        //TODO: See if we can check to see if this item is already in the list and
                        // if it is don't add it - This might work, confirm once we add the edit functionality
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
}