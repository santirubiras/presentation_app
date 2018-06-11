package com.example.santirubiras.catalog_slider_app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Landing extends AppCompatActivity {


    // This is the first activity launched.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
    }

    public void goToGallery (View v) {
        Intent k = new Intent(this, MainActivity.class);
        startActivity(k);
    }

}
