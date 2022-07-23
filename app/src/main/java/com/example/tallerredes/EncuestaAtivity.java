package com.example.tallerredes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;

public class EncuestaAtivity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    public boolean isOn = true;
    Thread cronos;
    int mili = 0, seg = 0, min = 0;
    Handler h = new Handler();
    FusedLocationProviderClient fusedLocationProviderClient;

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encuesta);

        Switch simpleSwitch = (Switch) findViewById(R.id.simpleSwitch); // initiate Switch

        preferences = getSharedPreferences("preferences", MODE_PRIVATE);

        Button btnIngresar = findViewById(R.id.btn_nuevaEncuesta);
        simpleSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (simpleSwitch.isChecked()) {
                    isOn = true;
                    hilocronometro();
                    iniciarLocalizacion();


                } else {

                }
            }
        });

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EncuestaAtivity.this, Formulario.class);
                startActivity(intent);
            }
        });

    }

    private void iniciarLocalizacion() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Localizacion localizacion = new Localizacion(preferences);


        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);

        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, localizacion);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, localizacion);

        Log.d("debug", "Localizacion agregada");
    }

    private void hilocronometro() {
        cronos = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (isOn) {
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mili++;
                        if (mili == 999) {
                            seg++;
                            mili = 0;
                            System.out.println(min + ":" + seg + ":" + mili);
                            VariablesGlobales.tiempoConexion = min + ":" + seg + ":" + mili;
                        }

                        if (seg == 59) {
                            min++;
                            seg = 0;
                        }
                        h.post(new Runnable() {
                            @Override
                            public void run() {
                                String m = "", s = "", mi = "";
                                if (mili < 10) {
                                    m = "00" + mili;
                                } else if (mili < 100) {
                                    m = "0" + mili;
                                } else {
                                    m = "" + mili;
                                }
                                if (seg < 10) {
                                    s = "0" + seg;
                                } else {
                                    s = "" + seg;
                                }
                                if (min < 10) {
                                    mi = "0" + min;
                                } else {
                                    mi = "" + min;
                                }

                            }

                        });

                    }

                }
            }
        });
        cronos.start();
    }
}