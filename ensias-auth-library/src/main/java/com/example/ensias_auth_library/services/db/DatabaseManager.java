package com.example.ensias_auth_library.services.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ensias_auth_library.models.Assignments;
import com.example.ensias_auth_library.models.Enrollment;
import com.example.ensias_auth_library.models.GameStat;
import com.example.ensias_auth_library.models.Kid;
import com.example.ensias_auth_library.models.Organisation;

import java.util.ArrayList;
import java.util.List;

import static com.example.ensias_auth_library.utils.AppConstants.TABLE_GAME_STATS;
import static com.example.ensias_auth_library.utils.AppConstants.GameStatTableConstants.*;

/**
 * Created by younes on 8/19/2018.
 */

public class DatabaseManager {
    private DatabaseHelper mDBHelper;
    private static DatabaseManager mDatabaseManager;
    private SQLiteDatabase mDatabase;
    Context mContext;



    private DatabaseManager(Context context){
        mContext = context;
        mDBHelper = DatabaseHelper.getInstance(context);

    }
    public static DatabaseManager getInstance(Context context){
        if (mDatabaseManager == null){
            mDatabaseManager = new DatabaseManager(context);
        }
        return mDatabaseManager;
    }

    public void storeOrganisations(List<Organisation> organisationList){
    for(Organisation organisation: organisationList )
        storeOrganisation(organisation);
}
    public void storeOrganisation(Organisation organisation){
        setToWritableData();
        if (mDatabase != null){
            //prepare the transaction information that will be saved to the database
            ContentValues organisationRow = new ContentValues();
            organisationRow.put("id", organisation.getId());
            organisationRow.put("name", organisation.getName());
            organisationRow.put("description", organisation.getDescription());
            organisationRow.put("phone", organisation.getPhone());
            organisationRow.put("email", organisation.getEmail());
            organisationRow.put("address", organisation.getAddress());
            organisationRow.put("deleted_at", organisation.getDeletedAt());
            organisationRow.put("created_at", organisation.getCreatedAt());
            organisationRow.put("updated_at", organisation.getUpdatedAt());
            try {
                mDatabase.insertOrThrow("organisations", null, organisationRow);
                Log.d("Database Insertion", "Organisation Added");

            } catch (Exception e) {
                Log.e("Database Insertion", "Error " + e.getCause() + " " + e.getMessage());
            }
        }
    }
    public void storeOrganisationsChildren(Assignments assignments ){
        for(Assignments.Assignment assignment : assignments.getAssignments())
            storeOrganisationChild(assignment);
    }
    public void storeParentChildren(List<Enrollment> enrollments ){
        for(Enrollment enrollment : enrollments)
            storeParentChild(enrollment);
    }
    public void storeOrganisationChild(Assignments.Assignment assignment){
        setToWritableData();
        if (mDatabase != null){
            //prepare the transaction information that will be saved to the database
            ContentValues kidRow = new ContentValues();
            kidRow.put("id", assignment.getKid().getId());

            kidRow.put("id_organisation", assignment.getOrganisationId());

            kidRow.put("first_name", assignment.getKid().getFirstName());
            kidRow.put("last_name", assignment.getKid().getLastName());
            kidRow.put("gender", assignment.getKid().getGender());
            kidRow.put("birthday", assignment.getKid().getBirthday());
            kidRow.put("parent_email", assignment.getKid().getParentEmail());
            kidRow.put("deleted_at", assignment.getKid().getDeletedAt());
            kidRow.put("created_at", assignment.getKid().getCreatedAt());
            kidRow.put("updated_at", assignment.getKid().getUpdatedAt());


            try {
                mDatabase.insertOrThrow("kids", null, kidRow);
                Log.d("Database Insertion", "Kid ("+assignment.getKid().getId()+") Added");

            } catch (Exception e) {
                Log.e("Database Insertion", "Error " + e.getCause() + " " + e.getMessage());
            }
        }
    }
    public void storeParentChild(Enrollment enrollment){
        setToWritableData();
        if (mDatabase != null){
            //prepare the transaction information that will be saved to the database
            ContentValues kidRow = new ContentValues();
            kidRow.put("id", enrollment.getKid().getId());
            kidRow.put("first_name", enrollment.getKid().getFirstName());
            kidRow.put("last_name", enrollment.getKid().getLastName());
            kidRow.put("gender", enrollment.getKid().getGender());
            kidRow.put("birthday", enrollment.getKid().getBirthday());
            kidRow.put("parent_email", enrollment.getKid().getParentEmail());
            kidRow.put("deleted_at", enrollment.getKid().getDeletedAt());
            kidRow.put("created_at", enrollment.getKid().getCreatedAt());
            kidRow.put("updated_at", enrollment.getKid().getUpdatedAt());

            try {
                mDatabase.insertOrThrow("kids", null, kidRow);
                Log.d("Database Insertion", "Kid ("+enrollment.getKid().getId()+") Added");

            } catch (Exception e) {
                Log.e("Database Insertion", "Error " + e.getCause() + " " + e.getMessage());
            }
        }
    }
    public void storeGameStat(GameStat gameStat){
        setToWritableData();
        ContentValues gameStatRow = new ContentValues();
        gameStatRow.put(TABLE_GAME_STAT_APP_ID                   , gameStat.getApp_id());
        gameStatRow.put(TABLE_GAME_STAT_KID_ID                   , gameStat.getChild_id());
        gameStatRow.put(TABLE_GAME_STAT_ACCOMPAGNANT_ID          , gameStat.getUser_id());
        gameStatRow.put(TABLE_GAME_STAT_EXERCICE_ID              , gameStat.getExercise_id());
        gameStatRow.put(TABLE_GAME_STAT_LEVEL_ID                 , gameStat.getLevel_id());
        gameStatRow.put(TABLE_GAME_STAT_CURRENT_DATE             , gameStat.getGame_date_id());
        gameStatRow.put(TABLE_GAME_STAT_START_TIME               , gameStat.getCreated_at());
        gameStatRow.put(TABLE_GAME_STAT_FINISH_TIME              , gameStat.getUpdated_at());
        gameStatRow.put(TABLE_GAME_STAT_SUCCESS_OPERATIONS_NUMBER, gameStat.getSuccessful_attempts());
        gameStatRow.put(TABLE_GAME_STAT_FAIL_OPERATION_NUMBER    , gameStat.getFailed_attempts());
        gameStatRow.put(TABLE_GAME_STAT_SUCCESS_MIN_TIME         , gameStat.getMin_time_succeed_sec());
        gameStatRow.put(TABLE_GAME_STAT_SUCCESS_AVG_TIME         , gameStat.getAvg_time_succeed_sec());
        gameStatRow.put(TABLE_GAME_STAT_LONGITUDE                , gameStat.getLongitude());
        gameStatRow.put(TABLE_GAME_STAT_LATITUDE                 , gameStat.getLatitude());
        gameStatRow.put(TABLE_GAME_STAT_DEVICE_MAC_ADDRESS       , gameStat.getDevice());
        gameStatRow.put(TABLE_GAME_STAT_FLAG                     , gameStat.getFlag());

        try {
            mDatabase.insertOrThrow(TABLE_GAME_STATS, null, gameStatRow);
            Log.e("Database Insertion", "Game ("+ gameStat.getApp_id()+") Added");

        } catch (Exception e) {
            Log.e("Database Insertion", "Game Error " + e.getCause() + " " + e.getMessage());
        }
    }
    public List<Organisation> getOrganisationsList(){
        setToReadableData();
        List<Organisation> myOrganisations = new ArrayList<>();
        Cursor cursor = mDatabase.query("organisations",null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                myOrganisations.add(Organisation.getOrganisationFromCursor(cursor));
            }while(cursor.moveToNext());
        }
        return myOrganisations;
    }
    public List<Kid> getOrganisationKidsList(int organisationId){
        setToReadableData();
        List<Kid> selectedOrganisationKids = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM kids WHERE id_organisation = "+organisationId, null);
        if(cursor.moveToFirst()){
            do{
                selectedOrganisationKids.add(Kid.getKidFromCursor(cursor));
            }while(cursor.moveToNext());
        }
        return selectedOrganisationKids;
    }
    public List<Kid> getParentKidsList() {
        List<Kid> selectedOrganisationKids = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM kids", null);
        if(cursor.moveToFirst()){
            do{
                selectedOrganisationKids.add(Kid.getKidFromCursor(cursor));
            }while(cursor.moveToNext());
        }
        return selectedOrganisationKids;
    }
    public int gameStatsRowCount (){
        return DatabaseHelper.getInstance(mContext).tableRowCount(TABLE_GAME_STATS);
    }

    private void setToWritableData(){
        mDatabase  = mDBHelper.getWritableDatabase();
    }
    private void setToReadableData(){
        mDatabase  = mDBHelper.getReadableDatabase();
    }
}
