package com.example.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
