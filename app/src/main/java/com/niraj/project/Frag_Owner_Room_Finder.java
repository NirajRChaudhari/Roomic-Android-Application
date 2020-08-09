package com.niraj.project;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Frag_Owner_Room_Finder extends Fragment {

    View view;
    Adapter_Owner_Room_Finder adapter_Owner_room_finder;
    List<Room_class> arraylist;
    ProgressDialog progressDialog;
    EditText search_room;
    RecyclerView recyclerView;
    Button load_more;
    NestedScrollView nestedScrollView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.frag__owner__room__finder,container,false);

        nestedScrollView =(NestedScrollView) view.findViewById(R.id.scroll_owner_room);
        recyclerView=(RecyclerView)view.findViewById(R.id.ow_list_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter_Owner_room_finder =new Adapter_Owner_Room_Finder(getContext(),arraylist);
        recyclerView.setAdapter(adapter_Owner_room_finder);

        load_more=view.findViewById(R.id.load_more_button);

        adapter_Owner_room_finder.setOnItemClickListener(new Adapter_Owner_Room_Finder.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if(arraylist.size()>=position) {
                    Intent i = new Intent(getContext(), Details_Room.class);
                    i.putExtra("room", arraylist.get(position));
                    startActivity(i);
                }
            }
        });

        search_room=(EditText) view.findViewById(R.id.owner_search_room);
        search_room.addTextChangedListener(new TextWatcher() {
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
    }//onCreate


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog=new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.logo);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();

        arraylist=new ArrayList<Room_class>();
        arraylist.clear();
        CollectionReference collectionReference=FirebaseFirestore.getInstance().collection("room");
        collectionReference.whereEqualTo("owner_id",FirebaseAuth.getInstance().getUid()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {
                for(QueryDocumentSnapshot queryDocumentSnapshot:querySnapshot)
                {
                    Room_class room_class=queryDocumentSnapshot.toObject(Room_class.class);
                    arraylist.add(room_class);
                }//for
                progressDialog.dismiss();
                if(arraylist.isEmpty())
                {
                    nestedScrollView.setBackgroundResource(R.drawable.register_arrow);
                    load_more.setVisibility(View.INVISIBLE);
                    new AlertDialog.Builder(getContext()).setIcon(R.mipmap.logo).setTitle("NO ROOOMS REGISTERED") .setPositiveButton("DONE",null).setMessage("Register Rooms and We will find Perfect Rentee for you....").setCancelable(false).show();

                }
                else {

                    nestedScrollView.setBackgroundResource(R.drawable.back5);
                    load_more.setVisibility(View.VISIBLE);
                }
                adapter_Owner_room_finder.room_list=arraylist;
                recyclerView.setAdapter(adapter_Owner_room_finder);

            }//onSuccess
        });
    }//onCreate

    void search(String str)
    {
        List<Room_class>temp=new ArrayList<>();
        for (int i=0;i<arraylist.size();i++) {
            Room_class room_class = arraylist.get(i);
            if (room_class.address.toLowerCase().contains(str.toLowerCase())) {
                temp.add(room_class);
                Log.i("helow","Found");
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
            adapter_Owner_room_finder.room_list=temp;
            recyclerView.setAdapter(adapter_Owner_room_finder);
        }

    }//search

// Written only for delete operation refreshing
/*    public void update_Recycler_view(List<Room_class> arraylist)
    {
        RecyclerView recyclerView1;
        recyclerView1=(RecyclerView)view.findViewById(R.id.ow_list_view);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));

        Adapter_Owner_Room_Finder adapter_Owner_room_finder1 =new Adapter_Owner_Room_Finder(getContext(),arraylist);
        adapter_Owner_room_finder1.room_list=arraylist;
        recyclerView1.setAdapter(adapter_Owner_room_finder1);
    }
*/
}//class




        /*databaseReference=FirebaseDatabase.getInstance().getReference().child("room");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arraylist.clear();
                for (DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Room_class room_class=ds.getValue(Room_class.class);
                    if(room_class.owner_id.equals(FirebaseAuth.getInstance().getUid()))
                    {
                        arraylist.add(room_class);
                    }
                }
                progressDialog.dismiss();
                if(arraylist.isEmpty())
                {
                    new AlertDialog.Builder(getContext()).setIcon(R.mipmap.logo).setTitle("NO ROOOMS REGISTERED").setPositiveButton("DONE",null).setMessage("Register Rooms and We will find Perfect Rentee for you....").setCancelable(false).show();
                }
                else {
                    //Adapter_Owner_Room_Finder adapter = new Adapter_Owner_Room_Finder(getContext(), arraylist);
                    adapter_Owner_room_finder.room_list=arraylist;
                    recyclerView.setAdapter(adapter_Owner_room_finder);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/