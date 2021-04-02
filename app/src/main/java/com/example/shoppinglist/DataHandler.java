package com.example.shoppinglist;


import com.example.shoppinglist.interfaces.FirebaseCallback;
import com.example.shoppinglist.interfaces.FirebaseCallbackListKeys;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * This Class is the class to read and write to the firebase database.
 *
 * This will instantiate an instance of the Firebase, and provides four methods: readData,
 * getListKeys, writeData, and writeListKeys in order to read and write to the Database.
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class DataHandler {
     private FirebaseDatabase mFirebaseDatabase;

    /*
    Default constructor which creates the DB Instance from Firebase
     */
    public DataHandler() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
    }

    public void readData(FirebaseCallback myCallback, String listKey) {

        DatabaseReference mDatabaseReference = this.mFirebaseDatabase.getReference(listKey);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ShoppingList list = dataSnapshot.getValue(ShoppingList.class);
                myCallback.onCallback(list);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void getListKeys(FirebaseCallbackListKeys myCallback, String listKey) {


        DatabaseReference mDatabaseReference = this.mFirebaseDatabase.getReference(listKey);
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseListAccess accessKeys = dataSnapshot.getValue(DatabaseListAccess.class);
                myCallback.onCallback(accessKeys);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    public void writeData(ShoppingList list, String listKey){

        DatabaseReference mDatabaseReference = this.mFirebaseDatabase.getReference(listKey);
        mDatabaseReference.setValue(list);
    }

    public void writeListKeys(DatabaseListAccess accessKeys, String listKey){

        DatabaseReference mDatabaseReference = this.mFirebaseDatabase.getReference(listKey);
        mDatabaseReference.setValue(accessKeys);
    }

}
