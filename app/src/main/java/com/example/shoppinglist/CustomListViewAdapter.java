package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * This class creates the adapter used by a ListView to display a Shopping List
 *
 * This creates the adapter used to display Default Shopping Lists including a Textview for the
 * quantity, a Textview for the Item name, and a button to delete the item from the Shopping list
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class CustomListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<ShoppingListItem> list;
    private Context context;
    private DataHandler data;
    private ShoppingList shoppingListDefault;
    String listKey;

    /**
     * Constructor for the CustomListViewAdapter.
     *
     * @param list
     * @param context
     * @param data
     * @param listKey
     */
    public CustomListViewAdapter(ShoppingList list, Context context, DataHandler data, String listKey) {
        this.list = list.getItems();
        this.shoppingListDefault = list;
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

    /**
     * This method generates the view to display the items of the list.  It includes the item name,
     * item quantity, and a button to delete the item.
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_listview_layout, null);
        }

        //Handle TextView and name from shoppingListItem
        TextView listItemName = (TextView)view.findViewById(R.id.list_item_name);
        listItemName.setText(list.get(position).getName());



        //Handle TextView and quantity from shoppingListItem
        TextView listItemQuantity = (TextView)view.findViewById(R.id.list_item_quantity);
        listItemQuantity.setText(String.valueOf(list.get(position).getQuantity()));


        //Handle buttons and add onClickListeners
        FloatingActionButton deleteBtn = (FloatingActionButton) view.findViewById(R.id.delete_btn);

        //custom listener to remove the item if they click the delete button
        deleteBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(position);
                notifyDataSetChanged();
                data.writeData(shoppingListDefault, listKey);
            }
        });

        return view;
    }
}