package com.example.tallerredes;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Localizacion implements LocationListener {


    @Override
    public void onLocationChanged(Location location) {
        // Este metodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
        String texto = "Mi ubicación es: \n" +
                "Latitud = " + location.getLatitude() + "\n" +
                "Longitud = " + location.getLongitude();
        VariablesGlobales.latitud= Integer.toString((int) location.getLatitude());
        VariablesGlobales.longitud= Integer.toString((int) location.getLongitude());

        System.out.println(texto);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                Log.d("debug", "LocationProvider.AVAILABLE");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                break;
        }
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("GPS Activado");
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("GPS Desactivado");
    }
}
