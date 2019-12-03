package uoc.tddm.practica2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    public SharedPreferences registeredUser;
    public Boolean isRegistered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //obtenemos datos de sharedPreferences
        registeredUser = getPreferences(Context.MODE_PRIVATE);
        //recuperamos la propiedad registered
        isRegistered = registeredUser.getBoolean("registered", false);

        if (isRegistered) {
            //si usuario registrado pasamos a MainActivity
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

        } else {
            //si usuario NO registrado pasamos a LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
