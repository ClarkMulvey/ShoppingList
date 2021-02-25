package com.example.shoppinglist;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DataHandler {
     private DefaultShoppingLists defaultLists;
     private UpcomingShoppingLists upcomingLists;
     private FirebaseDatabase mFirebaseDatabase;
     //private String filename;    //This isn't needed when using Firebase


    /*
    Default constructor which creates the DB Instance from Firebase
     */
    public DataHandler() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public DefaultShoppingLists getDefaultLists() {
        return defaultLists;
    }

    public void setDefaultLists(DefaultShoppingLists defaultLists) {
        this.defaultLists = defaultLists;
    }

    public UpcomingShoppingLists getUpcomingLists() {
        return upcomingLists;
    }

    public void setUpcomingLists(UpcomingShoppingLists upcomingLists) {
        this.upcomingLists = upcomingLists;
    }

    public void readData(FirebaseCallback myCallback) {
    //TODO: Need to write this method to read from the local Disk

    //TODO: Need to add to this method to also read from the cloud, although we need to determin
    // when the right time is to read from the cloud.

        DatabaseReference mDatabaseReference = this.mFirebaseDatabase.getReference("defaultList");
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ShoppingListDefault list = dataSnapshot.getValue(ShoppingListDefault.class);
                myCallback.onCallback(list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void writeData(ShoppingListDefault list){

        //TODO: Need to write this method to write to the local Disk

        //TODO: Need to add to this method to also write to the cloud, although we need to determine
        // when the right time is to write to the cloud.

        DatabaseReference mDatabaseReference = this.mFirebaseDatabase.getReference("defaultList");
        mDatabaseReference.setValue(list);
    }

}
