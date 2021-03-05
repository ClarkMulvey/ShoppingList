package com.example.shoppinglist.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppinglist.DataHandler;
import com.example.shoppinglist.DatabaseListAccess;
import com.example.shoppinglist.R;

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

            /*
            if (this.databaseListAccess.getDefaultListKeys() == null) {
                this.databaseListAccess.setDefaultListKeys(new ArrayList<>());

                this.databaseListAccess.getDefaultListKeys().add(new CustomMap("1", "defaultList"));
                //this.data.writeListKeys(databaseListAccess, databaseListAccess.getMainKey());
            };

             */


            /*
            if (this.upcomingListKeys == null) {
                //this.upcomingListKeys = new HashMap<>();
                //this.upcomingListKeys.put("Clark", "upcomingList");
                //this.data.writeListKeys(this, mainKey);
            }

             */

        }, databaseListAccess.getMainKey());

    }

    public void clickViewEditDefaultList(View view) {
        Intent intent = new Intent(this, EditDefaultListActivity.class);
        intent.putExtra("defaultKeys", this.databaseListAccess.getDefaultListKeys());

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
