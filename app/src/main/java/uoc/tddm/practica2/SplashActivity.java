package uoc.tddm.practica2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    public SharedPreferences credenciales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //OBTENEMOS CREDENCIALES DE SHARED PREFERENCES
        //abrimos el fichero credenciales y guardamos su referencia
        credenciales = getSharedPreferences("credenciales", MODE_PRIVATE);
        //recuperamos la propiedad registered
        boolean isRegistered = credenciales.getBoolean("registered", false);
        String user = credenciales.getString("username","Usuario no registrado");

        if (isRegistered) {
            //si usuario registrado pasamos a MainActivity
            Toast.makeText(this,"Bienvenid@: "+user,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            //si usuario NO registrado pasamos a LoginActivity
            Toast.makeText(this,user,Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
