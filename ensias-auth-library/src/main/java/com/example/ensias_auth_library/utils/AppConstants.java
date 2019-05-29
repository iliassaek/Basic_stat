package com.example.ensias_auth_library.utils;

/**
 * Created by younes on 8/19/2018.
 */

public class AppConstants {
    public static String URL_ENDPOINT_PRIMARY = "http://access-apps.ma/androidApi/public/";
    public static final String DATABASE_NAME = "Game";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_GAME_STATS = "game_stats";


    // Game stat Constants
    public class GameStatTableConstants {
        public static final String TABLE_GAME_STAT_ID                         ="id";
        public static final String TABLE_GAME_STAT_APP_ID                     ="id_application";
        public static final String TABLE_GAME_STAT_KID_ID                     ="id_apprenant";
        public static final String TABLE_GAME_STAT_ACCOMPAGNANT_ID            ="id_accompagnant";
        public static final String TABLE_GAME_STAT_EXERCICE_ID                ="id_exercice";
        public static final String TABLE_GAME_STAT_LEVEL_ID                   ="id_niveau";
        public static final String TABLE_GAME_STAT_CURRENT_DATE               ="date_actuelle";
        public static final String TABLE_GAME_STAT_START_TIME                 ="heure_debut";
        public static final String TABLE_GAME_STAT_FINISH_TIME                ="heure_fin";
        public static final String TABLE_GAME_STAT_SUCCESS_OPERATIONS_NUMBER  ="Nombre_operation_reuss";
        public static final String TABLE_GAME_STAT_FAIL_OPERATION_NUMBER      ="Nombre_operation_echou";
        public static final String TABLE_GAME_STAT_SUCCESS_MIN_TIME           ="minimum_temps_operation_sec";
        public static final String TABLE_GAME_STAT_SUCCESS_AVG_TIME           ="moyen_temps_operation_sec";
        public static final String TABLE_GAME_STAT_LONGITUDE                  ="longitude";
        public static final String TABLE_GAME_STAT_LATITUDE                   ="latitude";
        public static final String TABLE_GAME_STAT_DEVICE_MAC_ADDRESS         ="device";
        public static final String TABLE_GAME_STAT_FLAG                       ="flag";
    }



}
