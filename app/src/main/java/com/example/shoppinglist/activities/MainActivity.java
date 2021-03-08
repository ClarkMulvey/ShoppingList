package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.CustomMap;
import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataHandler data;
    DatabaseListAccess databaseListAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.data = new DataHandler();

        this.databaseListAccess = new DatabaseListAccess();
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
            };

        }, databaseListAccess.getMainKey());

    }

    //Handles the Menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.default_lists:
                clickViewDefaultLists();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    //Puts the menu item on the upper bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.default_list, menu);
        return true;
    }

    public void clickViewEditDefaultList(View view) {
        Intent intent = new Intent(this, EditDefaultListActivity.class);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());

        startActivity(intent);

    }

    public void clickViewDefaultLists() {
        Intent intent = new Intent(this, ViewDefaultListsActivity.class);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());
        intent.putExtra("listKey", this.databaseListAccess.getMainKey());

        startActivity(intent);

    }

    public void clickStartShopping(View view) {
        Intent intent = new Intent(this, StartShoppingActivity.class);
        startActivity(intent);
    }

    public void clickViewUpcomingList(View view) {
        Intent intent = new Intent(this, ViewUpcomingListsActivity.class);
        startActivity(intent);
    }

    public void clickCreateUpcomingTripList(View view) {
        Intent intent = new Intent(this, CreateUpcomingTripListActivity.class);
        startActivity(intent);
    }
}
