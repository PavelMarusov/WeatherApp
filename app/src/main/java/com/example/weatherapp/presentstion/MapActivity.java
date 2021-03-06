package com.example.weatherapp.presentstion;

import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.weatherapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends FragmentActivity implements View.OnClickListener {
    private GoogleMap maps;
    private SupportMapFragment mapFragment;
    private Button drawLine;
    private List<LatLng> coordinates;
    private Polygon polуgon;
    private Context context;
    public static SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        drawLine = findViewById(R.id.draw_line);
        coordinates = new ArrayList<>();
        context = getApplicationContext();
        preferences = context.getSharedPreferences("coord", MODE_PRIVATE);
        drawLine.setOnClickListener(this::onClick);
        showMap();
        if (getSharedPreferences("coord",context.MODE_PRIVATE)!=null&& coordinates!=null){
            coordinates=getLocation(coordinates);
        }
    }

    private void showMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().
                findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                maps = googleMap;
                CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(42.8706944, 74.5836545)).zoom(11.67f).build();
                maps.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                maps.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        maps.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker()));
                        coordinates.add(latLng);
                    }
                });
            }
        });

    }

    public void saveCoordinates(List<LatLng> coordinates) {
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(coordinates);
        editor.putString("coords", json);
        editor.apply();
        Log.d("pop", "LatLng" + json);
        Toast.makeText(this, "Координаты сохранены", Toast.LENGTH_SHORT).show();



    }


    @Override
    public void onClick(View v) {
        PolygonOptions options = new PolygonOptions();
        options.strokeWidth(4f);
        options.strokeColor(getResources().getColor(R.color.colorLine));
        for (LatLng latLng : coordinates) {
            options.add(latLng);
        }
        maps.addPolygon(options);
        saveCoordinates(coordinates);
    }
    public static List<LatLng> getLocation(List<LatLng> latLngs) {
        Gson gson = new Gson();
        String json = preferences.getString("coords", null);
        Type type = new TypeToken<List<LatLng>>() {
        }.getType();
        latLngs = gson.fromJson(json, type);
        if (latLngs == null) {
            latLngs = new ArrayList<>();
        }
        return latLngs;
    }
}

