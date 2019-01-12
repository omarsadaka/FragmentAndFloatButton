package com.example.omar.fragfloat.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.fragfloat.Data.Constants;
import com.example.omar.fragfloat.Model.EarthQuake;
import com.example.omar.fragfloat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuakeListActivity extends AppCompatActivity {
    private ArrayList<String> arrayList;
    private ListView listView;
    private RequestQueue queue;
    private ArrayAdapter arrayAdapter;
    private List<EarthQuake> earthQuakeList;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quake_list);

        arrayList = new ArrayList<>();
        listView = findViewById(R.id.listView);
        back = findViewById(R.id.back);
        queue = Volley.newRequestQueue(this);
        earthQuakeList = new ArrayList<>();
        getAllQuakes(Constants.URL);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                QuakeListActivity.this.finish();
            }
        });

    }

    private void getAllQuakes(String url) {

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                final EarthQuake earthQuake = new EarthQuake();

                try {
                    JSONArray features = response.getJSONArray("features");
                    for (int i = 0; i< Constants.LIMIT ; i++){
                        JSONObject properties = features.getJSONObject(i).getJSONObject("properties");

                        JSONObject geometry = features.getJSONObject(i).getJSONObject("geometry");

                        JSONArray coordinates = geometry.getJSONArray("coordinates");
                        double lon = coordinates.getDouble(0);
                        double lat = coordinates.getDouble(1);

                       earthQuake.setPlace(properties.getString("place"));
//                        earthQuake.setType(properties.getString("type"));
//                        earthQuake.setTime(properties.getLong("time"));
//                        earthQuake.setLat(lat);
//                        earthQuake.setLon(lon);

                        arrayList.add(earthQuake.getPlace());
                    }
                    arrayAdapter = new ArrayAdapter(QuakeListActivity.this ,android.R.layout.simple_list_item_1,
                            android.R.id.text1 ,arrayList);
                    listView.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(QuakeListActivity.this, "You Clicked: " +arrayList.get(position) , Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(objectRequest);
    }
}
