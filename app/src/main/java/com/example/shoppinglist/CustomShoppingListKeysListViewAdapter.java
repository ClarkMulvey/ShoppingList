package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.shoppinglist.interfaces.CustomButtonListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This class creates the adapter used by a ListView to display list of ShoppingLists
 *
 * This creates the adapter used to display ShoppingLists including a Textview for the List name,
 * a button to edit a ShoppingList, and a button to delete the Shoppinglist
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class CustomShoppingListKeysListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CustomMap> list;
    private Context context;
    private DataHandler data;
    private DatabaseListAccess databaseListAccess;
    private ShoppingList shoppingListDefault;
    private String listKey;
    private CustomButtonListener customListener;


    public CustomShoppingListKeysListViewAdapter(ArrayList<CustomMap> list, Context context, DataHandler data, DatabaseListAccess databaseListAccess, String listKey, CustomButtonListener customListener) {
        this.list = list;
        this.context = context;
        this.data = data;
        this.listKey = listKey;
        this.databaseListAccess = databaseListAccess;
        this.customListener = customListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        //return list.get(pos).getId();
        return 0;
        //We aren't using Item ID's
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_shoppinglist_keys_listview_layout, null);
        }


        //Handle TextView and name from shoppingListItem
        TextView listItemName = (TextView)view.findViewById(R.id.list_name);
        listItemName.setText(list.get(position).getValue());


        //Handle buttons and add onClickListeners
        FloatingActionButton deleteBtn = (FloatingActionButton) view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String shoppingListKey = list.get(position).getKey();
                list.remove(position);
                notifyDataSetChanged();
                data.writeListKeys(databaseListAccess, listKey);
                ShoppingList deleteShoppingList = new ShoppingList();
                data.writeData(deleteShoppingList, shoppingListKey);
            }
        });

        //Handle buttons and add onClickListeners
        FloatingActionButton editBtn = (FloatingActionButton) view.findViewById(R.id.edit_button);

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (customListener != null) {
                    customListener.clickEditList(position);
                }
            }
        });

        return view;
    }


}