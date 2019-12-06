package com.example.passngo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.android.passngo.R;



public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        String fullname = getIntent().getExtras().getString("fullname");
        TextView FullnameDisplay = (TextView)findViewById(R.id.FullnameDisplay);

        FullnameDisplay.setText(fullname);
    //
    }


}
