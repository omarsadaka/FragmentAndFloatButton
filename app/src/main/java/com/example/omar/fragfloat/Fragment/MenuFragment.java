package com.example.omar.fragfloat.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.omar.fragfloat.Adapter.RecyclerviewAdapter;
import com.example.omar.fragfloat.Data.ApiClient;
import com.example.omar.fragfloat.Data.ApiInterface;
import com.example.omar.fragfloat.Activities.MainActivity;
import com.example.omar.fragfloat.Model.Movies;
import com.example.omar.fragfloat.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MenuFragment extends Fragment {

    private RecyclerView recyclerView;
    public RecyclerviewAdapter adapter;
    private List<Movies> movies;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_menu, container, false);
        recyclerView = view.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();

        ApiInterface apiInterface = ApiClient.createService(ApiInterface.class);
        Call<List<Movies>> call = apiInterface.getcontacts();
        call.enqueue(new Callback<List<Movies>>() {
            @Override
            public void onResponse(@NonNull Call<List<Movies>> call, @NonNull Response<List<Movies>> response) {

                movies = new ArrayList<>();
                movies = response.body();
                adapter = new RecyclerviewAdapter(getContext(), movies);
                recyclerView.setAdapter(adapter);
                dialog.dismiss();

            }

            @Override
            public void onFailure(@NonNull Call<List<Movies>> call, @NonNull Throwable t) {
                Log.e("error", t.getMessage());

            }
        });

        ((MainActivity) getActivity()).search_view.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s!=null)
                adapter.getFilter().filter(s.toString());
            }
        });
        return view;
    }


}
