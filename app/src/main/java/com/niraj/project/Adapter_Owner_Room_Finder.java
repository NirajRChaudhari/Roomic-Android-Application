package com.niraj.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SyncAdapterType;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter_Owner_Room_Finder extends RecyclerView.Adapter<Adapter_Owner_Room_Finder.Room_ViewHolder> {

    Context context;
    List<Room_class> room_list;
    private static ClickListener clickListener;


    public Adapter_Owner_Room_Finder(Context context, List<Room_class> room_list) {
        this.context = context;
        this.room_list = room_list;
    }//constructor


    @NonNull
    @Override
    public Room_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.box_owner_room,null);

        return new Room_ViewHolder(view,context,room_list);
    }

    @Override
    public void onBindViewHolder(@NonNull Room_ViewHolder room_viewHolder, int i) {
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

    public void setOnItemClickListener(ClickListener clickListener)
    {
        Adapter_Owner_Room_Finder.clickListener=clickListener;
    }

    //View Holder
    static class Room_ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView maddress,mdesc,mRent;
        ImageView mprofile,mdelete;

        @Override// Click Listener
        public void onClick(View v) {
            if(clickListener!=null)
            clickListener.onItemClick(getAdapterPosition(),v);
        }

        public Room_ViewHolder(@NonNull View itemView,final Context context,final List<Room_class>room_list) {

            super(itemView);
            maddress = (TextView) itemView.findViewById(R.id.tloc);
            mdesc= (TextView) itemView.findViewById(R.id.tdesc);
            mRent=(TextView) itemView.findViewById(R.id.trent);

            mprofile=(ImageView)itemView.findViewById(R.id.timage);
            mdelete=(ImageView)itemView.findViewById(R.id.delete_oroom);

            //Delete Room
            mdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //To reflect changes in list
                    new AlertDialog.Builder(context).setTitle("DELETE ROOM").setMessage("Are you sure you want to delete this ROOM ? All Room's Data will be Lost ?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            FirebaseFirestore.getInstance().collection("room").document(room_list.get(getAdapterPosition()).firestore_id).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(context, " ROOM DELETED SUCCESSFULLY .... ", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(context, Owner_Tabbed_Activity.class);
                                        context.startActivity(i);
                                        // Frag_Owner_Room_Finder frag_owner_room_finder=new Frag_Owner_Room_Finder();
                                        // frag_owner_room_finder.update_Recycler_view(room_list);

                                    } else {
                                        Toast.makeText(context, " ROOM DELETION FAILED.... TRY AGAIN ", Toast.LENGTH_LONG).show();
                                    }

                                }
                            });//Listener
                        }//Internal onClick
                    }).setNegativeButton("NO", null).setCancelable(false).setIcon(R.mipmap.logo).show();


                }//onCLick
            });//mdelete onClick

            itemView.setOnClickListener(this);

        }
    }
}//class

