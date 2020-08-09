package com.niraj.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Frag_Roomate_Finder extends Fragment {

    List<Rentee>arraylist=new ArrayList<>();
    View view;
    Adapter_Roommate_Finder adapter_roommate_finder;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    EditText search_roomate;
    NestedScrollView nestedScrollView;
    Button load_more;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.frag__roomate__finder,container,false);

        recyclerView= view.findViewById(R.id.roommate_list_xml);
        nestedScrollView=(NestedScrollView)view.findViewById(R.id.scroll_roommate_view);
        load_more=(Button)view.findViewById(R.id.roommate_load_more_button);
        search_roomate=(EditText) view.findViewById(R.id.search_roommate);

        adapter_roommate_finder=new Adapter_Roommate_Finder(getContext(),arraylist);
        recyclerView.setAdapter(adapter_roommate_finder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter_roommate_finder.setOnItemClickListener(new Adapter_Roommate_Finder.Roommate_ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if(adapter_roommate_finder.rentees_list.size() >=position)
                {
                    List<Rentee> temp_list=adapter_roommate_finder.rentees_list;
                    Intent i=new Intent(getContext(),Details_Roommate.class);
                    i.putExtra("roommate",temp_list.get(position));
                    startActivity(i);

                }
            }
        });

        search_roomate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                search(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseReference =FirebaseDatabase.getInstance().getReference().child("rentee");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arraylist.clear();
                for(DataSnapshot ds :dataSnapshot.getChildren()) {
                    Rentee rentee = ds.getValue(Rentee.class);
                    if(rentee.rm_register==true) {
                        arraylist.add(rentee);
                    }
                }

                if(arraylist.isEmpty())
                {
                 nestedScrollView.setBackgroundResource(R.drawable.no_result_found);
                 load_more.setVisibility(View.INVISIBLE);
                }
                else
                {
                    nestedScrollView.setBackgroundResource(R.drawable.back5);
                    load_more.setVisibility(View.VISIBLE);
                }
                    adapter_roommate_finder.rentees_list=arraylist;
                    recyclerView.setAdapter(adapter_roommate_finder);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(getContext(),"Fail to Sync",Toast.LENGTH_SHORT).show();
            }
        });

    }//onCreate

    void search(String str)
    {
        List<Rentee>temp=new ArrayList<>();
        for (int i=0;i<arraylist.size();i++)
        {
            Rentee rentee=arraylist.get(i);
            if(rentee.r_peraddress.toLowerCase().contains(str.toLowerCase()))
            {
                temp.add(rentee);
            }
        }

        if(temp.isEmpty())
        {
            nestedScrollView.setBackgroundResource(R.drawable.no_result_found);
            load_more.setVisibility(View.INVISIBLE);
        }
        else {
            nestedScrollView.setBackgroundResource(R.drawable.back5);
            load_more.setVisibility(View.VISIBLE);
        }
        adapter_roommate_finder.rentees_list = temp;
        recyclerView.setAdapter(adapter_roommate_finder);
    }
}


      /*arraylist.clear();

         databaseReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                   Rentee rentee = new Rentee();

                   rentee = dataSnapshot.getValue(Rentee.class);
                   arraylist.add(rentee);

               Adapter_Roommate_Finder adapter_roommate_finder=new Adapter_Roommate_Finder(getContext(),arraylist);
               alist.setAdapter(adapter_roommate_finder);


           }

           @Override
           public void onChildChanged(DataSnapshot dataSnapshot, String s) {

               Toast.makeText(getContext(),"Changed :"+dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onChildRemoved(DataSnapshot dataSnapshot) {
               Toast.makeText(getContext(),"Removed :"+dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onChildMoved(DataSnapshot dataSnapshot, String s) {
               Toast.makeText(getContext(),"Moved :"+dataSnapshot.getKey(),Toast.LENGTH_SHORT).show();
           }

           @Override
           public void onCancelled(DatabaseError databaseError) {
           }

       });*/


  /*  arraylist.add(new Roommate_class(1,"NIRAJ CHAUDHARI","Sukamal Niwas,Taloda at Nandurbar","Single with NO Room"));
        arraylist.add(new Roommate_class(1,"OMKAR KULKARNI","Mor Galli,Rahimatpur at Satara","Group of People with Room"));
        arraylist.add(new Roommate_class(1,"SIDDHANT PATIL","Samrat Chowk, Solapur","Single with Room"));
*/