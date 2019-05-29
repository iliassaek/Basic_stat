package com.example.test4;

import com.example.ensias_auth_library.FoxyAuth;
import com.example.ensias_auth_library.models.GameStat;

public class InformationClass {


    private GameStat gameStat  ;


    public InformationClass() {
        this.gameStat = new GameStat() ;
    }

    public void Load(){
        gameStat.setApp_id("aitelkouch_12_T_6_26");
        gameStat.setExercise_id("T_6_26");
        gameStat.setLevel_id("1");
        gameStat.setUpdated_at("2019-05-29 18:27:31");
        gameStat.setCreated_at("2019-05-29 18:27:00");
        gameStat.setSuccessful_attempts("1");
        gameStat.setFailed_attempts("11");
        gameStat.setMin_time_succeed_sec("50");
        gameStat.setAvg_time_succeed_sec("58");
        gameStat.setLongitude("11.2555");
        gameStat.setLatitude("-2.55547");
        FoxyAuth.storeGameStat(MainActivity.context,gameStat);
    }
}
