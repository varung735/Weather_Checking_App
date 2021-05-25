package com.example.wheather_forecasting_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient fusedLocationProviderClient;
    Button locationBtn;
    TextView tv_1, tv_2;
    float latitude;
    float longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationBtn = findViewById(R.id.location_btn);
        tv_1 = findViewById(R.id.tv_1);
        tv_2 = findViewById(R.id.tv_2);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        locationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getLocation();
            }
        });
    }

    private void getLocation(){
        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if(location != null){
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());

                        try {
                            List<Address> addresses = geocoder.getFromLocation(
                                    location.getLatitude(),location.getLongitude(), 1);

                            latitude = (float) addresses.get(0).getLatitude();
                            longitude = (float) addresses.get(0).getLongitude();

                            tv_1.setText(Html.fromHtml(
                                    "<font color= '#000000'><b>Latitude :</b> <br></font>"
                                    + addresses.get(0).getLatitude()
                            ));

                            tv_2.setText(Html.fromHtml(
                                    "<font color= '#000000'><b>Longitude :</b> <br></font>"
                                            + addresses.get(0).getLongitude()
                            ));

                            Log.i("LATITUDE", String.valueOf(latitude));
                            Log.i("LONGITUDE", String.valueOf(longitude));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }else{
            ActivityCompat.requestPermissions(MainActivity.this
                    ,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }
}