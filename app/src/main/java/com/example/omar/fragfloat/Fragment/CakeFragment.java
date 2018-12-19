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
import android.widget.Toast;

import com.example.omar.fragfloat.Adapter.FilmRecyclerViewAdapter;
import com.example.omar.fragfloat.Avtivities.MainActivity;
import com.example.omar.fragfloat.Data.FilmApiClient;
import com.example.omar.fragfloat.Data.FilmApiInterface;
import com.example.omar.fragfloat.Model.Films;
import com.example.omar.fragfloat.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CakeFragment extends Fragment {
    private RecyclerView recyclerView;
    private FilmRecyclerViewAdapter adapter;
    private Films resultsBeans;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cake, container, false);
        recyclerView = view.findViewById(R.id.films_recycler);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dialog = new ProgressDialog(getContext());
        dialog.setMessage("Loading...");
        dialog.show();
        apiClient();

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

    private void apiClient() {
        FilmApiInterface filmApiInterface = FilmApiClient.createService(FilmApiInterface.class);
        Call<Films> listCall = filmApiInterface.getcontacts();
        listCall.enqueue(new Callback<Films>() {
            @Override
            public void onResponse(@NonNull Call<Films> call, @NonNull Response<Films> response) {
                Log.e("response" , String.valueOf(response));
                resultsBeans=new Films();
                resultsBeans = response.body();
                if (resultsBeans.getStatus().equals("ok")) {
                    adapter = new FilmRecyclerViewAdapter(resultsBeans.getResults(),getContext());
                    recyclerView.setAdapter(adapter);
                }

                dialog.dismiss();

            }

            @Override
            public void onFailure(@NonNull Call<Films> call, @NonNull Throwable t) {
                Log.e("error", t.getMessage());
                dialog.dismiss();
            }
        });
    }


}
