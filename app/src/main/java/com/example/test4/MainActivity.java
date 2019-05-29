package com.example.test4;

import android.content.Context;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.ensias_auth_library.FoxyAuth;

public class MainActivity extends AppCompatActivity {


    public static Context context ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // static variable help us use storeGameStat whereever we want in our Java classes
        context = getApplicationContext() ;

        FoxyAuth.emerge(this,MainActivity.class);

        InformationClass informationClass = new InformationClass() ;


        // we call this method whenever we wanna save the game state
        informationClass.Load();

    }
}
