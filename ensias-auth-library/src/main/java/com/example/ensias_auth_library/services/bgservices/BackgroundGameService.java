package com.example.ensias_auth_library.services.bgservices;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ensias_auth_library.api.RetrofitManager;
import com.example.ensias_auth_library.interfaces.AuthEndPointInterface;
import com.example.ensias_auth_library.models.GameStat;
import com.example.ensias_auth_library.utils.SaveSharedPreference;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.ensias_auth_library.utils.AppConstants.DATABASE_NAME;
import static com.example.ensias_auth_library.utils.AppConstants.TABLE_GAME_STATS;

/**
 * Created by younes on 08/05/2018.
 */

public class BackgroundGameService extends IntentService {

    SQLiteDatabase db;

    // Must create a default constructor
    public BackgroundGameService() {
        // Used to name the worker thread, important only for debugging.
        super("test-service");
        Log.e("fff", "BackgroundGameService: The Intent Service is running Correctly" );
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.print("Intent started");
        db = this.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        Cursor cursor = db.rawQuery("select * from "+ TABLE_GAME_STATS,null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                GameStat gameStat = createGameStat(cursor);

                RetrofitManager.getInstance(this).sendGameStat(gameStat,"Bearer " + SaveSharedPreference.getAccessToken(BackgroundGameService.this),
                        new Callback<RequestBody>() {
                            @Override
                            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {

                            }

                            @Override
                            public void onFailure(Call<RequestBody> call, Throwable t) {
                                Log.e("error", "onFailure: testing sending to server failed" );
                            }
                        });

                db.execSQL("DELETE FROM "+ TABLE_GAME_STATS +" Where date_actuelle = '"+ gameStat.updated_at+"' AND heure_debut = '"+ gameStat.created_at+"'");
                cursor.moveToNext();
            }
            db.execSQL("DELETE FROM " + TABLE_GAME_STATS);


        }
        db.close();
    }
    private GameStat createGameStat(Cursor cursor){
        GameStat gameStat = new GameStat();
        gameStat.setApp_id(cursor.getString(cursor.getColumnIndex("id_application")));
        gameStat.setChild_id(cursor.getString(cursor.getColumnIndex("id_apprenant")));
        gameStat.setUser_id(cursor.getString(cursor.getColumnIndex("id_accompagnant")));
        gameStat.setExercise_id(cursor.getString(cursor.getColumnIndex("id_exercice")));
        gameStat.setLevel_id(cursor.getString(cursor.getColumnIndex("id_niveau")));
        gameStat.setGame_date_id(cursor.getString(cursor.getColumnIndex("date_actuelle")));
        gameStat.setCreated_at(cursor.getString(cursor.getColumnIndex("heure_debut")));
        gameStat.setUpdated_at(cursor.getString(cursor.getColumnIndex("heure_fin")));
        gameStat.setSuccessful_attempts(Integer.toString(cursor.getInt(cursor.getColumnIndex("Nombre_operation_reuss"))));
        gameStat.setFailed_attempts(Integer.toString(cursor.getInt(cursor.getColumnIndex("Nombre_operation_echou"))));
        gameStat.setMin_time_succeed_sec(Integer.toString(cursor.getInt(cursor.getColumnIndex("minimum_temps_operation_sec"))));
        gameStat.setAvg_time_succeed_sec(Integer.toString(cursor.getInt(cursor.getColumnIndex("moyen_temps_operation_sec"))));
        gameStat.setLongitude(Double.toString(cursor.getDouble(cursor.getColumnIndex("longitude"))));
        gameStat.setLatitude(Double.toString(cursor.getDouble(cursor.getColumnIndex("latitude"))));
        gameStat.setDevice(cursor.getString(cursor.getColumnIndex("device")));
        gameStat.setFlag(cursor.getInt(cursor.getColumnIndex("flag")));
        return gameStat;
    }

}