package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This class creates the adapter used by a ListView to display list of DefaultShoppingLists
 *
 * This creates the adapter used to display DefaultShoppingLists including a Checkbox to select the list and a Textview for the List name
 *
 * @author Team-06
 * @version 2021.03.31
 * @since 1.0
 */
public class CustomDefaultListNamesListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CustomMap> list;
    private Context context;
    private DataHandler data;
    private DatabaseListAccess databaseListAccess;
    private ShoppingList shoppingListDefault;
    private String listKey;
    private ArrayList<Boolean> defaultListChecked;

    /**
     * Constructor for the CustomDefaultListNamesListViewAdapter.
     *
     * @param list
     * @param context
     * @param data
     * @param databaseListAccess
     * @param listKey
     * @param defaultListChecked
     */
    public CustomDefaultListNamesListViewAdapter(
            ArrayList<CustomMap> list, Context context, DataHandler data,
            DatabaseListAccess databaseListAccess, String listKey,
            ArrayList<Boolean> defaultListChecked) {
        this.list = list;
        this.context = context;
        this.data = data;
        this.listKey = listKey;
        this.databaseListAccess = databaseListAccess;
        this.defaultListChecked = defaultListChecked;
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
        return 0;
        //We aren't using Item ID's
    }

    /**
     * This method generates the view to display the items of the list.  It includes the checkbox
     * to select the item and the name of the list.
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
            view = inflater.inflate(R.layout.custom_defaultlists_listview_layout, null);
        }


        CheckBox checkBox = (CheckBox) view.findViewById(R.id.selectDefaultList);
        checkBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
        defaultListChecked.add(false); // set the status as we stored it
        checkBox.setOnCheckedChangeListener(mListener); // set the listener

        //Handle TextView and name from shoppingListItem
        TextView listItemName = (TextView)view.findViewById(R.id.defaultListName);
        listItemName.setText(list.get(position).getValue());

        return view;
    }

    //Define the listener for the checkbox in order to assign the list position
    CheckBox.OnCheckedChangeListener mListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            defaultListChecked.set((Integer) buttonView.getTag(), isChecked); // get the tag so we know the row and store the status
        }

    };
}