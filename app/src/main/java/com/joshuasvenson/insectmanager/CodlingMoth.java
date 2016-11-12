package com.joshuasvenson.insectmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anaso_000 on 9/27/2016.
 */
public class CodlingMoth extends AppCompatActivity {

    HashMap<String, List<String>> Risk_category;
    List<String> Risk_list;
    ExpandableListView Exp_list;
    MyExListAdapter RiskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.codling_moth);

        Exp_list = (ExpandableListView) findViewById(R.id.exp_list);
        Risk_category = ExpListDataProviderInsects.getInfo("1");
        Risk_list = new ArrayList<String>(Risk_category.keySet());
        RiskAdapter = new MyExListAdapter(this, Risk_category, Risk_list);
        Exp_list.setAdapter(RiskAdapter);

    }
}
