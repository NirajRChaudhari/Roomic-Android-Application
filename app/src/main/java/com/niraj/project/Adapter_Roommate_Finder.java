package com.niraj.project;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Roommate_Finder extends RecyclerView.Adapter<Adapter_Roommate_Finder.Roommate_ViewHolder> {

Context context;
List<Rentee>rentees_list;
private static Roommate_ClickListener listener;

Adapter_Roommate_Finder(Context context,List<Rentee> temp)
    {
        this.context=context;
        this.rentees_list=temp;
    }//constructor

    @NonNull
    @Override
    public Roommate_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.box_roommate,null);
        return new Roommate_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Roommate_ViewHolder roommate_viewHolder, int i) {
   Rentee temp_rentee=rentees_list.get(i);
        roommate_viewHolder.tvname.setText(temp_rentee.r_name);
        roommate_viewHolder.tvaddress.setText(temp_rentee.r_peraddress);
        roommate_viewHolder.tvstatus.setText(temp_rentee.r_status);
        roommate_viewHolder.tvquali.setText(temp_rentee.r_phone);
        Picasso.get().load(temp_rentee.r_photo).fit().into(roommate_viewHolder.tvimage);

    }

    @Override
    public int getItemCount() {
        return rentees_list.size();
    }

    ///Click Listener
    public interface Roommate_ClickListener{
    void onItemClick(int position,View view);
    }

    public void setOnItemClickListener(Roommate_ClickListener clickListener)
    {
        Adapter_Roommate_Finder.listener =clickListener;
    }


    /// View Holder
    class Roommate_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    TextView tvname,tvaddress,tvstatus,tvquali;
    ImageView tvimage;

        @Override //Click Listener
        public void onClick(View v) {
            if(listener !=null)
                listener.onItemClick(getAdapterPosition(),v);
        }


        public Roommate_ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvname = (TextView)itemView.findViewById(R.id.box_roommate_name);
             tvaddress = (TextView)itemView.findViewById(R.id.box_roommate_address);
             tvstatus = (TextView)itemView.findViewById(R.id.box_roommate_status);
             tvquali = (TextView)itemView.findViewById(R.id.box_roommate_quali);
             tvimage=(ImageView)itemView.findViewById(R.id.imageView2);

             itemView.setOnClickListener(this);
        }

    }

}//class
