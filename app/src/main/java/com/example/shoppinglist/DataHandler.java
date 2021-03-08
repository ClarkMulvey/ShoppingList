package com.example.shoppinglist;


import com.example.shoppinglist.interfaces.FirebaseCallback;
import com.example.shoppinglist.interfaces.FirebaseCallbackListKeys;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


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
