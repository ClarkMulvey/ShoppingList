package com.example.shoppinglist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.shoppinglist.activities.StartShoppingActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This class creates the adapter used by a ListView to display a Shopping List
 *
 * This creates the adapter used to display Default Shopping Lists including a Textview for the
 * quantity, a Textview for the Item name, and a button to delete the item from the Shopping list
 *
 * @author Team-06
 * @version 2021.0317
 * @since 1.0
 */
public class CustomShoppingTripListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<ShoppingListItem> list;
    private Context context;
    private DataHandler data;
    private ShoppingList shoppingList;
    String listKey;

    public CustomShoppingTripListViewAdapter(ShoppingList list, Context context, DataHandler data, String listKey) {
        this.list = list.getItems();
        this.shoppingList = list;
        this.context = context;
        this.data = data;
        this.listKey = listKey;
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
            view = inflater.inflate(R.layout.custom_shopping_trip_listview_layout, null);
        }

        //Handle TextView and name from shoppingListItem
        TextView listItemName = (TextView)view.findViewById(R.id.list_item_name);
        listItemName.setText(list.get(position).getName());



        //Handle TextView and quantity from shoppingListItem
        TextView listItemQuantity = (TextView)view.findViewById(R.id.list_item_quantity);
        listItemQuantity.setText(String.valueOf(list.get(position).getQuantity()));

        //Handle the CheckBox for whether an item is complete
        CheckBox listItemComplete = (CheckBox) view.findViewById(R.id.itemComplete);

        listItemComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).isCompleted()) {
                    list.get(position).setCompleted(false);
                    listItemName.setPaintFlags(listItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                    listItemQuantity.setPaintFlags(listItemQuantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                } else {
                    list.get(position).setCompleted(true);
                    listItemName.setPaintFlags(listItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    listItemQuantity.setPaintFlags(listItemQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                data.writeData(shoppingList, listKey);
            }
        });


        if (list.get(position).isCompleted()) {
            listItemName.setPaintFlags(listItemName.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            listItemQuantity.setPaintFlags(listItemQuantity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            listItemComplete.setChecked(true);
        } else {
            listItemName.setPaintFlags(listItemName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            listItemQuantity.setPaintFlags(listItemQuantity.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            listItemComplete.setChecked(false);
        }


        //Handle buttons and add onClickListeners
        FloatingActionButton deleteBtn = (FloatingActionButton) view.findViewById(R.id.delete_btn);

        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                data.writeData(shoppingList, listKey);
                ((StartShoppingActivity)context).displayUpcomingList(shoppingList);
            }
        });

        return view;
    }
}