package com.niraj.project;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Rentee_Room_Finder extends RecyclerView.Adapter<Adapter_Rentee_Room_Finder.Room_ViewHolder>{
    Context context;
    List<Room_class> room_list;
    private static Adapter_Rentee_Room_Finder.ClickListener clickListener;


    public Adapter_Rentee_Room_Finder(Context context, List<Room_class> room_list) {
        this.context = context;
        this.room_list = room_list;
    }//constructor


    @NonNull
    @Override
    public Adapter_Rentee_Room_Finder.Room_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.box_rentee_room,null);

        return new Adapter_Rentee_Room_Finder.Room_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter_Rentee_Room_Finder.Room_ViewHolder room_viewHolder, int i) {
        Room_class room_class=room_list.get(i);

        room_viewHolder.maddress.setText(room_class.address);
        room_viewHolder.mdesc.setText(room_class.desc);
        room_viewHolder.mRent.setText(Html.fromHtml("&#8377;")+" "+room_class.rent);

        Picasso.get().load(room_class.photoname1).fit().into(room_viewHolder.mprofile);
    }

    @Override
    public int getItemCount() {

        return room_list.size();
    }


    /////////////// Click Listener
    public interface ClickListener{
        void onItemClick(int position,View view);
    }

    public void setOnItemClickListener(Adapter_Rentee_Room_Finder.ClickListener clickListener)
    {
        Adapter_Rentee_Room_Finder.clickListener=clickListener;
    }

    //View Holder
    static class Room_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView maddress,mdesc,mRent;
        ImageView mprofile;

        @Override// Click Listener
        public void onClick(View v) {
            if(clickListener!=null)
                clickListener.onItemClick(getAdapterPosition(),v);
        }

        public Room_ViewHolder(@NonNull View itemView) {

            super(itemView);
            maddress = (TextView) itemView.findViewById(R.id.tloc);
            mdesc= (TextView) itemView.findViewById(R.id.tdesc);
            mRent=(TextView) itemView.findViewById(R.id.trent);

            mprofile=(ImageView)itemView.findViewById(R.id.timage);

            itemView.setOnClickListener(this);

        }
}
}
