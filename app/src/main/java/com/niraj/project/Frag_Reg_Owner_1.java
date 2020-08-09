
package com.niraj.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.maps.model.LatLng;

public class Frag_Reg_Owner_1 extends Fragment {
    private static final String TAG="Add Room";
    Button step1of2;
    String complete_address;
    LatLng latLng;
    View focusview;
   // ImageButton mapButton;
    EditText maddress;
    EditText mlocality;


    EditText mfacilties;
    EditText mrent;
    EditText mpincode;
    EditText mdistrict;
    String address,locality,facilities,rent,pincode,district;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.frag_owner_reg1,container,false);
        step1of2=view.findViewById(R.id.step1of2button);
        maddress=view.findViewById(R.id.addresstext);
        mlocality=view.findViewById(R.id.localitytext);
        mfacilties=view.findViewById(R.id.facilitiestext);
        mrent=view.findViewById(R.id.renttext);
       // mapButton=view.findViewById(R.owner_id.mapButton);
        mdistrict=view.findViewById(R.id.districttext);
        mpincode=view.findViewById(R.id.pincodetext);


        step1of2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                checkEmptiness();

            }
        });
        return view;
    }
    public void checkEmptiness()
    {
        mfacilties.setError(null);
        mrent.setError(null);
        maddress.setError(null);
        mlocality.setError(null);
        mpincode.setError(null);
        mdistrict.setError(null);

        boolean cancel=false;
        View focusView=null;

        facilities=mfacilties.getText().toString();
        rent=mrent.getText().toString();
        address=maddress.getText().toString();
        locality=mlocality.getText().toString();
        pincode=mpincode.getText().toString();
        district=mdistrict.getText().toString();


        if(TextUtils.isEmpty(address))
        {   maddress.setError("This field is empty");
            focusview=maddress;
            cancel=true;

        }
        else if (TextUtils.isEmpty(locality)) {
            mlocality.setError("This field is empty");
            focusview=mlocality;
            cancel=true;

        }
        else if (TextUtils.isEmpty(district))
        {
            mdistrict.setError("This field is empty");
            focusview=mdistrict;
            cancel=true;

        }
        else if (TextUtils.isEmpty(pincode))
        {
            mpincode.setError("This field is empty");
            focusview=mpincode;
            cancel=true;

        }
        else if(TextUtils.isEmpty(rent))
        {
            mrent.setError("This field is empty");
            focusview=mrent;
            cancel=true;

        }

        if(cancel)
        {
            focusview.requestFocus();
        }
        else
        {
            Intent intent=new Intent(getActivity(),Frag_Reg_Owner_2.class);
            intent.putExtra("paddress",address);
            intent.putExtra("plocality",locality);
           intent.putExtra("pdistrict",district);
            intent.putExtra("ppincode",pincode);
            intent.putExtra("pfacilites",facilities);
            intent.putExtra("prent",rent);

            startActivity(intent);
        }
    }
/*    public boolean checkCompleteAddressEmptiness()
    {
        maddress.setError(null);
        mlocality.setError(null);
        mpincode.setError(null);
        mdistrict.setError(null);


        address=maddress.getText().toString();
        locality=mlocality.getText().toString();
        pincode=mpincode.getText().toString();
        district=mdistrict.getText().toString();

        boolean cancel=false;
        View focusView=null;

        if(TextUtils.isEmpty(address))
        {   maddress.setError("This field is empty");
            focusview=maddress;
            cancel=true;

        }
        else if (TextUtils.isEmpty(locality)) {
            mlocality.setError("This field is empty");
            focusview=mlocality;
            cancel=true;

        }
        else if (TextUtils.isEmpty(district))
        {
            mdistrict.setError("This field is empty");
            focusview=mdistrict;
            cancel=true;

        }
        else if (TextUtils.isEmpty(pincode))
        {
            mpincode.setError("This field is empty");
            focusview=mpincode;
            cancel=true;

        }
        if(cancel)
        {
            focusview.requestFocus();

        }
        return  cancel;
    }*/
}
