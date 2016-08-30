package thontepu.naveen.patientmanager.Retrofit;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mac on 8/31/16.
 */
public class RetrofitUtil {
    public static Retrofit retrofitBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("")
                .build();
        return retrofit;
    }
}
