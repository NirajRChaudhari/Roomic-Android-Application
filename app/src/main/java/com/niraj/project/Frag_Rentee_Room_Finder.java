package com.niraj.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Frag_Rentee_Room_Finder extends Fragment {
    List<Room_class> arraylist = new ArrayList<Room_class>();
    RecyclerView recyclerView;
    View view;
    ListenerRegistration lr;
    ProgressDialog progressDialog;
    EditText search_room;
    Adapter_Rentee_Room_Finder adapter_Rentee_room_finder;
    NestedScrollView nestedScrollView;
    Button load_more;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.frag_rentee_room_finder, container, false);
        search_room=(EditText) view.findViewById(R.id.search_room);

        nestedScrollView=(NestedScrollView)view.findViewById(R.id.scroll_rentee_room);
        recyclerView = (RecyclerView)view.findViewById(R.id.room_list_xml);
        load_more=(Button)view.findViewById(R.id.room_load_more_button);

        adapter_Rentee_room_finder = new Adapter_Rentee_Room_Finder(getContext(), arraylist);

        recyclerView.setAdapter(adapter_Rentee_room_finder);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter_Rentee_room_finder.setOnItemClickListener(new Adapter_Rentee_Room_Finder.ClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                if(adapter_Rentee_room_finder.room_list.size()>=position) {  // Prevent Array Index Out of Bound Exception
                    Intent i = new Intent(getContext(), Details_Room.class);
                    i.putExtra("room", arraylist.get(position));
                    startActivity(i);
                }
            }
        });

        Room_class r;

////////////////////////////////////
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
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait.... ");
        progressDialog.setCancelable(false);
        progressDialog.show();

        CollectionReference collectionReference = FirebaseFirestore.getInstance().collection("room");

        lr=collectionReference.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot querySnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    return;
                } else {
                    arraylist.clear();
                    Log.i("nosia", "Fetching");
                    for (QueryDocumentSnapshot queryDocumentSnapshot : querySnapshots) {
                        Room_class room_class = queryDocumentSnapshot.toObject(Room_class.class);
                        arraylist.add(room_class);
                    }

                    progressDialog.dismiss();

                    if(arraylist.isEmpty())
                    {
                        nestedScrollView.setBackgroundResource(R.drawable.no_result_found);
                        load_more.setVisibility(View.INVISIBLE);
                    }
                    else {
                        nestedScrollView.setBackgroundResource(R.drawable.back5);
                        load_more.setVisibility(View.VISIBLE);
                    }
                    adapter_Rentee_room_finder.room_list = arraylist;
                    recyclerView.setAdapter(adapter_Rentee_room_finder);
                }//else
            }
        });
    }//onCreate

    @Override
    public void onDestroy() {
        super.onDestroy();
        lr.remove();
    }

    void search(String str)
    {
            List<Room_class>temp=new ArrayList<>();
            for (int i=0;i<arraylist.size();i++)
            {
                Room_class room_class=arraylist.get(i);
                if(room_class.address.toLowerCase().contains(str.toLowerCase()))
                {
                    temp.add(room_class);
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
        adapter_Rentee_room_finder.room_list = temp;
        recyclerView.setAdapter(adapter_Rentee_room_finder);

    }//search

}//class

 /*    databaseReference = FirebaseDatabase.getInstance().getReference().child("room");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arraylist.clear();
                Log.i("noca","Fetching");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Room_class room_class = ds.getValue(Room_class.class);
                    arraylist.add(room_class);

                }
                progressDialog.dismiss();
                //Adapter_Owner_Room_Finder adapter_Owner_room_finder = new Adapter_Owner_Room_Finder(getContext(), arraylist);
                adapter_Owner_room_finder.room_list=arraylist;
                recyclerView.setAdapter(adapter_Owner_room_finder);

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

