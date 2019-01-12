package com.example.omar.fragfloat.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omar.fragfloat.Adapter.FirebaseAdapter;
import com.example.omar.fragfloat.Activities.MainActivity;
import com.example.omar.fragfloat.Model.ListItem;
import com.example.omar.fragfloat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CartFragment extends Fragment {

    private DatabaseReference mReference;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private List<ListItem> items;
    private FirebaseAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_cart, container, false);

        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mReference = mDatabase.getReference().child("Users");
        progressDialog = new ProgressDialog(getContext());
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        items = new ArrayList<>();
        getData();

        ((MainActivity) getActivity()).search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null||!s.toString().isEmpty())
                    adapter.getFilter().filter(s.toString());
            }
        });
        return view;
    }

     public void getData(){



         progressDialog.setMessage("Loading...");
         progressDialog.show();

         mReference.addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(DataSnapshot dataSnapshot) {
                 items.clear();
                 for(DataSnapshot ds : dataSnapshot.getChildren()) {

                     String name = ds.child("UserName").getValue(String.class);
                     String job = ds.child("Job").getValue(String.class);
                     String phone = ds.child("Phone").getValue(String.class);
                     String id = ds.child("Id").getValue(String.class);
                     Log.d("TAG", job + " / " + name + " / " + phone+ " / " + id);

                     ListItem listItem = new ListItem();
                     listItem.setName(name);
                     listItem.setJob(job);
                     listItem.setPhone(phone);
                     listItem.setUserId(id);
                     items.add(listItem);
                     adapter = new FirebaseAdapter(getContext() , items);
                     recyclerView.setAdapter(adapter);
                     adapter.notifyDataSetChanged();
                     progressDialog.dismiss();
                 }

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {
                 Log.e("error" , String.valueOf(databaseError));

             }
         });
     }

}
