package com.example.shoppinglist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomShoppingListKeysListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<CustomMap> list;
    private Context context;
    private DataHandler data;
    private ShoppingList shoppingListDefault;
    private String listKey;


    public CustomShoppingListKeysListViewAdapter(ArrayList<CustomMap> list, Context context, DataHandler data, String listKey) {
        this.list = list;
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
            view = inflater.inflate(R.layout.custom_shoppinglist_keys_listview_layout, null);
        }


        //Handle TextView and name from shoppingListItem
        TextView listItemName = (TextView)view.findViewById(R.id.list_name);
        listItemName.setText(list.get(position).getValue());

        //Handle buttons and add onClickListeners
        Button deleteBtn = (Button)view.findViewById(R.id.delete_btn);

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