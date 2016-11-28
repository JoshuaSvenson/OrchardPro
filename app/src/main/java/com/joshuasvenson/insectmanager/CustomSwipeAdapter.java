package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Joshua on 11/13/2016.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] image_resources;
    private int[] apple_maggot_resources = {R.drawable.apple_maggot_damage,
            R.drawable.apple_maggot, R.drawable.apple_maggot_eggs, R.drawable.apple_maggot_trap};
    private int[] codling_moth_resources = {R.drawable.codling_moth_2,
            R.drawable.codling_moth_1, R.drawable.codling_moth_5, R.drawable.codling_moth_3, R.drawable.codling_moth_4};
    private int[] fire_blight_resources = {R.drawable.fire_blight_1,
            R.drawable.fire_blight_2, R.drawable.fire_blight_3};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx, String key, String type){
        context = ctx;

        if(type == "insect") {
            if (key == "1") {
                image_resources = codling_moth_resources;
            } else if (key == "2") {
                image_resources = apple_maggot_resources;
            }
        }
        else if (type == "disease"){
            if(key == "1"){
                image_resources = fire_blight_resources;
            }
        }
    }

    @Override
    public int getCount() {
        return image_resources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout)object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item_view = layoutInflater.inflate(R.layout.swipe_layout, container, false);

        ImageView imageView = (ImageView) item_view.findViewById(R.id.insectImageView);

        imageView.setImageResource(image_resources[position]);

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);
    }
}
