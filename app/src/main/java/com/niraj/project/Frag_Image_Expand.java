package com.niraj.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


public class Frag_Image_Expand extends Fragment {

    int current_index;

    ImageView image;
    FloatingActionButton bt_left,bt_right;
    Room_class room_class=new Room_class();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag__image__expand, container, false);

        image = (ImageView) view.findViewById(R.id.image_expand);
        bt_left = (FloatingActionButton) view.findViewById(R.id.floating_left);
        bt_right = (FloatingActionButton) view.findViewById(R.id.floating_right);
        Room_class room_class=null;

        if (getArguments() != null) {

            room_class = (Room_class) getArguments().getSerializable("room");

            current_index = getArguments().getInt("pic_no");
            final String id = getArguments().getString("owner_id");
            final String unique_pic1 = getArguments().getString("unique_pic1");


            switch (current_index) {
                case 1:
                    Picasso.get().load(room_class.photoname1).fit().into(image);
                    break;

                case 2:
                    Picasso.get().load(room_class.photoname2).fit().into(image);
                    break;

                case 3:
                    Picasso.get().load(room_class.photoname3).fit().into(image);
                    break;
            }


        }//bundle null checking


        final Room_class final_room_class=room_class;


        bt_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Frag_Image_Expand fr=new Frag_Image_Expand();
                Bundle args=new Bundle();

                switch (current_index)
                {
                    case 1:args.putInt("pic_no",3);
                        args.putSerializable("room",final_room_class);
                        fr.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
                        break;

                    case 2:args.putInt("pic_no",1);
                        args.putSerializable("room",final_room_class);
                        fr.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
                        break;

                    case 3:args.putInt("pic_no",2);
                        args.putSerializable("room",final_room_class);
                        fr.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
                        break;

                }
            }
        });


        bt_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Frag_Image_Expand fr=new Frag_Image_Expand();
                Bundle args=new Bundle();

                switch (current_index)
                {
                    case 1:args.putInt("pic_no",2);
                        args.putSerializable("room",final_room_class);
                        fr.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
                        break;

                    case 2:args.putInt("pic_no",3);
                        args.putSerializable("room",final_room_class);
                        fr.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
                        break;

                    case 3:args.putInt("pic_no",1);
                        args.putSerializable("room",final_room_class);
                        fr.setArguments(args);
                        getFragmentManager().beginTransaction().replace(R.id.image_exp_fragment,fr).commit();
                        break;

                }
            }
        });
        return view;
    }

  }
