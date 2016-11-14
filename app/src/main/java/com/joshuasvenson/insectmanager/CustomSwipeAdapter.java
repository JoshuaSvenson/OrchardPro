package com.joshuasvenson.insectmanager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Joshua on 11/13/2016.
 */

public class CustomSwipeAdapter extends PagerAdapter {

    private int[] image_resources = {R.drawable.apple_maggot_damage, R.drawable.apple_maggot, R.drawable.apple_maggot_eggs, R.drawable.apple_maggot_trap};
    private Context context;
    private LayoutInflater layoutInflater;

    public CustomSwipeAdapter(Context ctx){
        context = ctx;
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

        ImageView imageView = (ImageView) item_view.findViewById(R.id.appleMaggotImageView);

        imageView.setImageResource(image_resources[position]);

        container.addView(item_view);

        return item_view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout)object);
    }
}
