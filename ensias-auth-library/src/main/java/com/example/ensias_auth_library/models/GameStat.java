package com.example.ensias_auth_library.models;

import com.example.ensias_auth_library.utils.CrossVariables;
import com.example.ensias_auth_library.utils.SaveSharedPreference;

/**
 * Created by younes on 09/05/2018.
 */

public class GameStat {


    public  String app_id;
    private String child_id;
    private String user_id;
    private String exercise_id;
    private String level_id;
    public  String updated_at;
    public  String created_at;
    private String game_date_id;
    private String successful_attempts;
    private String failed_attempts;
    private String min_time_succeed_sec;
    private String avg_time_succeed_sec;
    private String longitude;
    private String latitude;
    private String device = "test";
    private int flag   = 0;

    public GameStat(){
        this.child_id = CrossVariables.kidId;
        this.user_id = CrossVariables.accompagnantId;
    }


    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getExercise_id() {
        return exercise_id;
    }

    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }

    public String getLevel_id() {
        return level_id;
    }

    public void setLevel_id(String level_id) {
        this.level_id = level_id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getGame_date_id() {
        return game_date_id;
    }

    public void setGame_date_id(String game_date_id) {
        this.game_date_id = game_date_id;
    }

    public String getSuccessful_attempts() {
        return successful_attempts;
    }

    public void setSuccessful_attempts(String successful_attempts) {
        this.successful_attempts = successful_attempts;
    }

    public String getFailed_attempts() {
        return failed_attempts;
    }

    public void setFailed_attempts(String failed_attempts) {
        this.failed_attempts = failed_attempts;
    }

    public String getMin_time_succeed_sec() {
        return min_time_succeed_sec;
    }

    public void setMin_time_succeed_sec(String min_time_succeed_sec) {
        this.min_time_succeed_sec = min_time_succeed_sec;
    }

    public String getAvg_time_succeed_sec() {
        return avg_time_succeed_sec;
    }

    public void setAvg_time_succeed_sec(String avg_time_succeed_sec) {
        this.avg_time_succeed_sec = avg_time_succeed_sec;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
