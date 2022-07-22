package com.example.tallerredes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class Formulario extends AppCompatActivity {

    private MediaRecorder grabacion;
    private Button btn_recorder;
    private String archivoSalida;
    private String jsonFormulario;
    private String latitud;
    private String longitud;
    private TextView Tv_Estado;
    private EditText Et_nombre, Et_fechaNacimiento, Et_Direccion, Et_Celular, Et_pregunta1, Et_pregunta2, Et_pregunta3, Et_pregunta4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        btn_recorder = findViewById(R.id.btn_record);
        Tv_Estado = findViewById(R.id.estado);
        Et_nombre = findViewById(R.id.nombre);
        Et_fechaNacimiento = findViewById(R.id.fechanacimiento);
        Et_Direccion = findViewById(R.id.direccion);
        Et_Celular = findViewById(R.id.celular);
        Et_pregunta1 = findViewById(R.id.respuesta1);
        Et_pregunta2 = findViewById(R.id.respuesta2);
        Et_pregunta3 = findViewById(R.id.respuesta3);
        Et_pregunta4 = findViewById(R.id.respuesta4);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Formulario.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }


    public String convertAudioToBase64(){
        File file = new File(archivoSalida);
        String encoded = "";
        try {
            byte[] bytes = FileUtils.readFileToByteArray(file);
            encoded = Base64.encodeToString(bytes,0);
        } catch (IOException e) {
            encoded = null;
        } finally {
            return encoded;
        }
    }
    public void Recorder(View view) {
        if (grabacion == null) {
            archivoSalida = getExternalFilesDir(null).getAbsolutePath() + "/Grabacion.mp3";
            grabacion = new MediaRecorder();
            grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
            grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            grabacion.setOutputFile(archivoSalida);
            try {
                grabacion.prepare();
                grabacion.start();
                Tv_Estado.setText("Grabando..");
            } catch (IOException e) {

            }
            btn_recorder.setBackgroundResource(R.drawable.rec);

            Toast.makeText(getApplicationContext(), "Grabando..", Toast.LENGTH_SHORT).show();
        } else if (grabacion != null) {
            grabacion.stop();
            grabacion.release();
            grabacion = null;
            btn_recorder.setBackgroundResource(R.drawable.stop_rec);
            Tv_Estado.setText("Grabacion Finalizada.");
            Toast.makeText(getApplicationContext(), "Grabacion finalizada.", Toast.LENGTH_SHORT).show();
        }
    }

    public void reproducir(View view) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(archivoSalida);
            mediaPlayer.prepare();
        } catch (IOException e) {

        }
        mediaPlayer.start();
        Tv_Estado.setText("Reproduciendo..");
        Toast.makeText(getApplicationContext(), "Reproduciendo audio..", Toast.LENGTH_SHORT).show();
    }

    public void enviardatos(View view) {
        System.out.println("FORMULARIO JSON");
        jsonFormulario = "" + Et_nombre.getText() + "," + Et_fechaNacimiento.getText() + "," + Et_Direccion.getText() + "," + Et_Celular.getText() + "," + Et_pregunta1.getText() + "," + Et_pregunta2.getText() + "," + Et_pregunta3.getText() + "," + Et_pregunta4.getText();
        System.out.println(jsonFormulario);
        System.out.println(archivoSalida);
        Intent intent = new Intent(Formulario.this, EncuestaAtivity.class);
        startActivity(intent);
    }

}