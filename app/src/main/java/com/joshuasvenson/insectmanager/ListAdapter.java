package com.joshuasvenson.insectmanager;

/**
 * Created by Joshua on 11/29/2016.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/*
Name: ListAdapter
Description: This class provides the code for a custom ListAdapter used in BiofixLIst
Layout File: listview.xml
 */
public class ListAdapter extends ArrayAdapter<String> {

    private final Activity Context;
    private final String[] ListItemsName;
    private final Integer[] ImageName;

    public ListAdapter(Activity context, String[] content,
                       Integer[] ImageName) {

        super(context, R.layout.listview, content);

        this.Context = context;
        this.ListItemsName = content;
        this.ImageName = ImageName;
    }
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = Context.getLayoutInflater();
        View ListViewSingle = inflater.inflate(R.layout.listview, parent, false);

        TextView ListViewItems = (TextView) ListViewSingle.findViewById(R.id.listText);
        ListViewSingle.setBackgroundResource(ImageName[position]);

        ListViewItems.setText(ListItemsName[position]);
        return ListViewSingle;

    };

}
