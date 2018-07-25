package com.toure.santepourtous;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.google.android.gms.ads.MobileAds;
import com.toure.santepourtous.utility.Utility;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        try{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

        MobileAds.initialize(this, Utility.ADMOB_APP_ID);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail, menu);
        return true;
    }
}
