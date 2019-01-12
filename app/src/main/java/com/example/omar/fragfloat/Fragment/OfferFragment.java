package com.example.omar.fragfloat.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.omar.fragfloat.Adapter.CustomInfoWindow;
import com.example.omar.fragfloat.Activities.QuakeListActivity;
import com.example.omar.fragfloat.Data.Constants;
import com.example.omar.fragfloat.Model.EarthQuake;
import com.example.omar.fragfloat.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class OfferFragment extends Fragment implements OnMapReadyCallback , GoogleMap.OnInfoWindowClickListener ,
        GoogleMap.OnMarkerClickListener{
    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private RequestQueue queue;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private BitmapDescriptor[] iconColor;
    private Button showList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = getLayoutInflater().inflate(R.layout.fragment_offer, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment)this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        showList = view.findViewById(R.id.showList);
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext() , QuakeListActivity.class));
            }
        });
        iconColor = new BitmapDescriptor[]{
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE),
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory. HUE_YELLOW)

        };
        queue = Volley.newRequestQueue(getContext());
        getEarthQuakes();


        return view;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getContext()));
        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (Build.VERSION.SDK_INT < 23) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }else {
            if (ContextCompat.checkSelfPermission(getContext() ,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity() , new String[]{Manifest.permission.ACCESS_FINE_LOCATION } , 1);
            }else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                    Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory
//                            .defaultMarker(BitmapDescriptorFactory.HUE_RED)).title("your Location"));
//                    marker.showInfoWindow();
                    mMap.setBuildingsEnabled(true);
                    mMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                }else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(getContext() , Manifest.permission.ACCESS_FINE_LOCATION )== PackageManager.PERMISSION_GRANTED){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        getQuakeDetails(marker.getTag().toString());
    }
    private void getEarthQuakes() {

        final EarthQuake earthQuake = new EarthQuake();
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, Constants.URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray features = response.getJSONArray("features");
                            for (int i=0 ; i<Constants.LIMIT ; i++){
                                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");

                                JSONObject geometry = features.getJSONObject(i).getJSONObject("geometry");

                                JSONArray coordinates = geometry.getJSONArray("coordinates");
                                double lon = coordinates.getDouble(0);
                                double lat = coordinates.getDouble(1);

                                earthQuake.setPlace(properties.getString("place"));
                                earthQuake.setType(properties.getString("type"));
                                earthQuake.setTime(properties.getLong("time"));
                                earthQuake.setLat(lat);
                                earthQuake.setLon(lon);
                                earthQuake.setMagnitude(properties.getDouble("mag"));
                                earthQuake.setDetailLink(properties.getString("detail"));

                                java.text.DateFormat dateFormat = java.text.DateFormat.getDateInstance();
                                String formattedDate = dateFormat.format(new Date(Long.valueOf(properties.getLong("time"))).getTime());
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.icon(iconColor[Constants.randomInt(iconColor.length , 0)]);
                                markerOptions.title(earthQuake.getPlace());
                                markerOptions.position(new LatLng(lat , lon));
                                markerOptions.snippet("Magnitude: " + earthQuake.getMagnitude() + "\n" + " Date: " + formattedDate);

                                if (earthQuake.getMagnitude()>= 2.0){
                                    CircleOptions circleOptions = new CircleOptions();
                                    circleOptions.center(new LatLng(earthQuake.getLat() , earthQuake.getLon()));
                                    circleOptions.radius(30000);
                                    circleOptions.strokeWidth(3.6f);
                                    circleOptions.fillColor(Color.RED);
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                    mMap.addCircle(circleOptions);
                                }
                                Marker marker = mMap.addMarker(markerOptions);
                                marker.setTag(earthQuake.getDetailLink());
                                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat , lon),1));



                            }
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    private void getQuakeDetails(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String detailsUrl = "";
                try {
                    JSONObject properties = response.getJSONObject("properties");
                    JSONObject products = properties.getJSONObject("products");
                    JSONArray geoserve = products.getJSONArray("geoserve");
                    for (int i=0 ; i<geoserve.length() ; i++){
                        JSONObject geoserveObj = geoserve.getJSONObject(i);
                        JSONObject contentObj = geoserveObj.getJSONObject("contents");
                        JSONObject jeoJsonObj = contentObj.getJSONObject("geoserve.json");
                        detailsUrl = jeoJsonObj.getString("url");
                        getMoreDetails(detailsUrl);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(jsonObjectRequest);

    }

    private void getMoreDetails(String url){
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                dialogBuilder = new AlertDialog.Builder(getContext());
                View view = getLayoutInflater().inflate(R.layout.popup_layout , null);
                Button dismissButton = view.findViewById(R.id.dismissPop);
                Button dismissButtonTop = view.findViewById(R.id.dismissPopTop);
                TextView popList = view.findViewById(R.id.popList);
                WebView htmlPop = view.findViewById(R.id.htmlWebView);

                StringBuilder stringBuilder = new StringBuilder();
                try {
                    if (response.has("tectonicSummary")&&response.getString("tectonicSummary")!= null){
                        JSONObject tetonic = response.getJSONObject("tectonicSummary");
                        if (tetonic.has("text")&&tetonic.getString("text")!=null){
                            String text = tetonic.getString("text");
                            htmlPop.loadDataWithBaseURL(null , text , "text/html" , "UTF-8" , null);
                        }
                    }
                    JSONArray cities = response.getJSONArray("cities");
                    for (int i =0 ; i<cities.length() ; i++){
                        JSONObject citiesObj = cities.getJSONObject(i);
                        stringBuilder.append("City: "+ citiesObj.getString("name")
                                +"\n" + "Distance: " + citiesObj.getString("distance")
                                +"\n" + "population: " + citiesObj.getString("population"));
                        stringBuilder.append("\n\n");
                    }
                    popList.setText(stringBuilder);
                    dismissButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dismissButtonTop.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialogBuilder.setView(view);
                    dialog = dialogBuilder.create();
                    dialog.show();
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
