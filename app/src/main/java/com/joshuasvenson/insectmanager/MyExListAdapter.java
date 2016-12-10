package com.joshuasvenson.insectmanager;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sunsh_000 on 10/20/2016.
 */

/*
Name: MyExpListAdapter
Description: This class provides the code for the custom expandable list adapter used in spray schedule to display risks
Layout File: list_parent.xml, list_child.xml
 */
public class MyExListAdapter extends BaseExpandableListAdapter
{
    private Context context;
    private HashMap<String, List<String>> Risk_Category;
    private List<String> Risk_List;

    public MyExListAdapter(Context context, HashMap<String, List<String>> Risk_Category, List<String> Risk_List) {
        this.context = context;
        this.Risk_Category = Risk_Category;
        this.Risk_List = Risk_List;
    }

    @Override
    public Object getChild(int parent, int child){
        return Risk_Category.get(Risk_List.get(parent)).get(child);
    }


    @Override
    public int getGroupCount() {
        return Risk_List.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return Risk_Category.get(Risk_List.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Risk_List.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertView, ViewGroup parentView) {
        String group_title = (String) getGroup(parent);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_parent, parentView, false);
        }

        TextView parent_textView = (TextView) convertView.findViewById(R.id.txtparent);
        parent_textView.setTypeface(null, Typeface.BOLD);
        parent_textView.setText(group_title);

        return convertView;
    }

    @Override
    public View getChildView(int parent, int child, boolean isLastChild, View convertView, ViewGroup parentView) {
        String child_title = (String) getChild(parent, child);

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_child, parentView, false);
        }

        TextView child_textView = (TextView) convertView.findViewById(R.id.txtchild);
        child_textView.setText(child_title);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
