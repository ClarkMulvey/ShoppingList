package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DataHandler data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.data = new DataHandler();

        DatabaseListAccess databaseListAccess = new DatabaseListAccess();
        //Read the data from Firebase
        data.getListKeys(databaseListAccessObj -> {
            if (databaseListAccessObj != null) {
                if (databaseListAccessObj.getDefaultListKeys() != null) {
                    databaseListAccess.setDefaultListKeys(databaseListAccessObj.getDefaultListKeys());
                }
                if (databaseListAccessObj.getUpcomingListKeys() != null) {
                    databaseListAccess.setUpcomingListKeys(databaseListAccessObj.getUpcomingListKeys());
                }
            }

            if (databaseListAccess.getDefaultListKeys() == null) {
                databaseListAccess.setDefaultListKeys(new ArrayList<>());

                databaseListAccess.getDefaultListKeys().add(new CustomMap("1", "defaultList"));
                this.data.writeListKeys(databaseListAccess, databaseListAccess.getMainKey());
            };
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
