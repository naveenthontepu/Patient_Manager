package thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi;

import android.app.Activity;
import android.app.ProgressDialog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import thontepu.naveen.patientmanager.Utils.Utilities;

import com.google.gson.Gson;

/**
 * Created by mac on 8/31/16.
 */
public abstract class PostSyncCallBack implements Callback<PostSyncResponse> {

    ProgressDialog progressDialog;
    Activity activity;

    public PostSyncCallBack(ProgressDialog progressDialog, Activity activity) {
        this.progressDialog = progressDialog;
        this.activity = activity;
    }

    @Override
    public void onResponse(Call<PostSyncResponse> call, Response<PostSyncResponse> response) {
        Gson gson = new Gson();
        String s = gson.toJson(response.body());
        Utilities.printLog("response body = " + s);
        Utilities.printLog("onResponse code = " + response.code());
        Utilities.printLog("body = " + response.body());
        Utilities.printLog("message = " + response.message());
        Utilities.printLog("success = " + response.isSuccessful());
        processResponse(response);
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onFailure(Call<PostSyncResponse> call, Throwable t) {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
        Utilities.serverError(activity);
    }

    public abstract void processResponse(Response<PostSyncResponse> response);

}
