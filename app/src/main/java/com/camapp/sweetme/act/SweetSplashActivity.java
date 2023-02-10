package com.camapp.sweetme.act;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.camapp.sweetme.R;
import com.camapp.sweetme.SweetMyAplication;

public class SweetSplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitysweet_splash);

        SweetMyAplication global=(SweetMyAplication)this.getApplication();
        global.config.getRemoteConfig(this);

        global.config.OnSplashListener(() -> {
                    Intent intent = new Intent(SweetSplashActivity.this, SweetMainAct.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    startActivity(intent);
                    finish();
                }
        );

    }
}