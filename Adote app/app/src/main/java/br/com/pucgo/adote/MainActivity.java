package br.com.pucgo.adote;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import br.com.pucgo.adote.persistencia.UsuarioAppDAOBD;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        permissoes();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                irTelaLogin();
            }
        }, 2000);

    }

    private void irTelaLogin() {
        UsuarioAppDAOBD bd = new UsuarioAppDAOBD(MainActivity.this);
        if (bd.buscar().isEmpty()) {
            Intent intentTelaEditarPerfil = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intentTelaEditarPerfil);
            finish();
        } else {
            Intent intentTelaPrincipal = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(intentTelaPrincipal);
            finish();
        }
    }

    private void permissoes() {
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("WRITE_EXTERNAL_STORAGE", "Permissão garantida");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Log.v("READ_EXTERNAL_STORAGE", "Permissão garantida");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
        if (checkSelfPermission(android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
            Log.v("INTERNET", "Permissão garantida");
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 0);
        }
    }
}
