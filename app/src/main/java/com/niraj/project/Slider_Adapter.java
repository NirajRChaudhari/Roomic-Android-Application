package com.niraj.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Slider_Adapter extends PagerAdapter {
    public static final String DISCOVER_ROOMS = "DISCOVER ROOMS";
    public static final String FLAT_REGISTER = "FLAT REGISTER";
    public static final String ROOMMATE_FINDER = "ROOMMATE FINDER";
    Context context;
    LayoutInflater layoutInflater;

    Button bt;
    public Slider_Adapter(Context context) {
        this.context = context;
    }

    public String[] heading = {DISCOVER_ROOMS, FLAT_REGISTER, ROOMMATE_FINDER};

    public String[] text = {"You  are  just  One  Step  AWAY  from  your  Desirable  ROOM !!!!","We  are  24*7  available  to  help  you  in  finding  Occupants  for  you  Property ","Find  PERFECT  MATCH  for  Roommates  as  per  your  AREA, STREAM  and  many  more  FILTERS"};

    public int[] image={R.drawable.room,R.drawable.owner,R.drawable.roomate};//R.drawable.back5,

    @Override
    public int getCount() {
        return heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == (RelativeLayout) o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideimage = (ImageView) view.findViewById(R.id.imageView);
        TextView slideheading = (TextView) view.findViewById(R.id.hpg1);
        TextView slidetext = (TextView) view.findViewById(R.id.tpg1);

        slideheading.setText(heading[position]);
        slidetext.setText(text[position]);
        slideimage.setImageResource(image[position]);

        container.addView(view);

        return view;
    }
 @Override
      public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)  throws UnsupportedOperationException{

      //  super.destroyItem(container,position,object);
        container.removeView((View) object);
    }

}