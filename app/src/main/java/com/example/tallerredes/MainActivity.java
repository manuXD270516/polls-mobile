package com.example.tallerredes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements Comunicacion {
    private ProgressBar pgbEjecutanto;
    private Button btnIngresar;
    private TextView txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pgbEjecutanto = findViewById(R.id.pgbEjecutanto);
        btnIngresar = findViewById(R.id.btnIngresar);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TareaAsincrona(MainActivity.this).execute(txtUsername.getText().toString(), txtPassword.getText().toString(), 3000);

            }
        });
    }

    @Override
    public void toggleProgressBar(boolean status) {
        if (status) {
            pgbEjecutanto.setVisibility(View.VISIBLE);
        } else {
            pgbEjecutanto.setVisibility(View.GONE);
        }
    }

    @Override
    public void lanzarActividad(Class<?> tipoActividad) {
        Intent intent = new Intent(this, tipoActividad);
        startActivity(intent);
    }

    @Override
    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
