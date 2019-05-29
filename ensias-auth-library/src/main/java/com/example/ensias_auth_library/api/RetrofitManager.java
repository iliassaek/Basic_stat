package com.example.ensias_auth_library.api;

import android.content.Context;

import com.example.ensias_auth_library.interfaces.AuthEndPointInterface;
import com.example.ensias_auth_library.models.Assignments;
import com.example.ensias_auth_library.models.GameStat;
import com.example.ensias_auth_library.models.UserAssignmentsRequestBody;
import com.example.ensias_auth_library.models.UserInfo;
import com.example.ensias_auth_library.models.UserLoginInfo;
import com.example.ensias_auth_library.utils.AppConstants;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by younes on 8/19/2018.
 */

public class RetrofitManager {
    private static RetrofitManager mInstance;
    private Context mContextFragment;
    private Context mContext;

    private RetrofitManager() {
    }


    public static RetrofitManager getInstance(Context context) {
        RetrofitManager instance = mInstance != null ? mInstance : (mInstance = new RetrofitManager());
        if (context != null)
            mInstance.mContext = context;
        return instance;
    }

//    private OkHttpClient getHttpClient() {
//        OkHttpClient okClient = new OkHttpClient.Builder()
//                .addInterceptor(
//                        new Interceptor() {
//                            @Override
//                            public Response intercept(Interceptor.Chain chain) throws IOException {
//                                Request original = chain.request();
//                                Request.Builder requestBuilder = original.newBuilder()
//                                        .method(original.method(), original.body());
//                                requestBuilder.removeHeader("User-Agent");
//                                Request request = requestBuilder.build();
//                                Response response = chain.proceed(request);
//                                ResponseBody body = response.body();
//                                return response;
//                            }
//                        })
//                .readTimeout(60, TimeUnit.SECONDS)
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .build();
//        return okClient;
//    }

    private Retrofit getRetrofit(String baseURL) {
        return new Retrofit.Builder()
                //.client(okHttpClient)
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())

                .build();
    }

    private AuthEndPointInterface getAPIService() {
        return getRetrofit(AppConstants.URL_ENDPOINT_PRIMARY).create(AuthEndPointInterface.class);
    }

    public Call<UserInfo> getUserInfo(UserLoginInfo userLoginInfo, Callback<UserInfo> callback){
        Call<UserInfo> call = getAPIService().getUserInfo(userLoginInfo);
        call.enqueue(callback);
        return call;
    }
    public Call<Assignments> getUserAssignments(UserAssignmentsRequestBody userAssignmentsRequestBody,String Authorization, Callback<Assignments> callback){
        Call<Assignments> call = getAPIService().getUserOrganisationAssignments(userAssignmentsRequestBody,Authorization);
        call.enqueue(callback);
        return call;
    }
    public Call<RequestBody> sendGameStat(GameStat gameStat, String Authorization, Callback<RequestBody> callback){
        Call<RequestBody> call = getAPIService().sendGameInfo(gameStat,Authorization);
        call.enqueue(callback);
        return call;
    }

    OkHttpClient okHttpClient = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder();

            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }
    }).build();
}
