package com.example.ensias_auth_library.services.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.content.Context.MODE_PRIVATE;
import static com.example.ensias_auth_library.utils.AppConstants.DATABASE_NAME;
import static com.example.ensias_auth_library.utils.AppConstants.DATABASE_VERSION;
import static com.example.ensias_auth_library.utils.AppConstants.GameStatTableConstants.*;

import static com.example.ensias_auth_library.utils.AppConstants.TABLE_GAME_STATS;

/**
 * Created by younes on 8/19/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static DatabaseHelper mDatabaseInstance = null;
    private Context mContext;



    public static DatabaseHelper getInstance(Context context){
        if (mDatabaseInstance == null){
            mDatabaseInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return mDatabaseInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table BasicInfoTable (" +
                "mac_address Text Not NULL," +
                "id_user Integer Not NULL," +
                "anomalie_state INTEGER NOT NULL );");
        db.execSQL("create table Organisations (" +
                "id INTEGER Not NULL," +
                "name TEXT Not NULL," +
                "description TEXT Not NULL," +
                "phone TEXT ," +
                "email TEXT ," +
                "address TEXT ," +
                "deleted_at TEXT ," +
                "created_at TEXT ," +
                "updated_at TEXT);");
        db.execSQL("create table Kids (" +
                "id INTEGER Not NULL," +
                "id_organisation INTEGER," +
                "first_name TEXT," +
                "last_name TEXT," +
                "gender TEXT," +
                "birthday TEXT," +
                "parent_email TEXT," +
                "deleted_at TEXT," +
                "created_at TEXT," +
                "updated_at TEXT);");
        db.execSQL("create table " + TABLE_GAME_STATS + "("
                + TABLE_GAME_STAT_ID                          + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TABLE_GAME_STAT_APP_ID                      + " VARCHAR(100),"
                + TABLE_GAME_STAT_KID_ID                      + " VARCHAR(100),"
                + TABLE_GAME_STAT_ACCOMPAGNANT_ID             + " VARCHAR(100),"
                + TABLE_GAME_STAT_EXERCICE_ID                 + " VARCHAR(100),"
                + TABLE_GAME_STAT_LEVEL_ID                    + " VARCHAR(100),"
                + TABLE_GAME_STAT_CURRENT_DATE                + " VARCHAR(100),"
                + TABLE_GAME_STAT_START_TIME                  + " VARCHAR(100),"
                + TABLE_GAME_STAT_FINISH_TIME                 + " VARCHAR(100),"
                + TABLE_GAME_STAT_SUCCESS_OPERATIONS_NUMBER   + " INTEGER,"
                + TABLE_GAME_STAT_FAIL_OPERATION_NUMBER       + " INTEGER,"
                + TABLE_GAME_STAT_SUCCESS_MIN_TIME            + " INTEGER,"
                + TABLE_GAME_STAT_SUCCESS_AVG_TIME            + " INTEGER,"
                + TABLE_GAME_STAT_LONGITUDE                   + " DOUBLE,"
                + TABLE_GAME_STAT_LATITUDE                    + " DOUBLE,"
                + TABLE_GAME_STAT_DEVICE_MAC_ADDRESS          + " VARCHAR(100),"
                + TABLE_GAME_STAT_FLAG                        + " BOOLEAN"
                +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public int tableRowCount(String tableName) {
        int rowsNumber;
        SQLiteDatabase db;
        Cursor cursor;

        db = mContext.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        cursor = db.rawQuery("SELECT COUNT(*) FROM " + tableName,null);
        cursor.moveToFirst();
        rowsNumber = cursor.getInt(0);
        db.close();
        return rowsNumber;
    }
}
