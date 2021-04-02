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

public class CustomDefaultListNamesListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CustomMap> list;
    private Context context;
    private DataHandler data;
    private DatabaseListAccess databaseListAccess;
    private ShoppingList shoppingListDefault;
    private String listKey;
    private ArrayList<Boolean> defaultListChecked;


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
        //return list.get(pos).getId();
        return 0;
        //We aren't using Item ID's
    }

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


    CheckBox.OnCheckedChangeListener mListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            defaultListChecked.set((Integer) buttonView.getTag(), isChecked); // get the tag so we know the row and store the status
        }

    };
}