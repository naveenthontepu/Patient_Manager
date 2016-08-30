package thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.design.widget.Snackbar;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import thontepu.naveen.patientmanager.Database.PatientsDB;
import thontepu.naveen.patientmanager.R;
import thontepu.naveen.patientmanager.Retrofit.RetrofitUtil;
import thontepu.naveen.patientmanager.Retrofit.Sync.SyncApi;
import thontepu.naveen.patientmanager.Utils.Utilities;

/**
 * Created by mac on 8/31/16.
 */
public class PostSyncController {
    Activity activity;
    SendPostSyncResponse sendPostSyncResponse;
    private Retrofit retrofit;

    public PostSyncController(Activity activity, SendPostSyncResponse sendPostSyncResponse) {
        this.activity = activity;
        this.sendPostSyncResponse = sendPostSyncResponse;
    }

    public void postSyncApiCall(){
        if (Utilities.isNetworkAvailable(activity)) {
            ProgressDialog progressDialog = Utilities.getProgressDialog(activity, "Please wait Syncing Records");
            Utilities.showProgressDialog(progressDialog);
            PostSyncBody postSyncBody = null;
            postSyncBody = buildpostSyncBody();
            retrofit = RetrofitUtil.retrofitBuilder();
            Utilities.printLog("retrofit built");
            String url = "";
            SyncApi syncApi = retrofit.create(SyncApi.class);
            if (syncApi != null) {
                Call<PostSyncResponse> postSyncResponseCall = syncApi.postSyncApi(url, postSyncBody);
                Utilities.printLog("postSyncResponseCall");
                if (postSyncResponseCall != null) {
                    PostSyncCallBack postSyncCallBack = new PostSyncCallBack(progressDialog, activity) {
                        @Override
                        public void processResponse(Response<PostSyncResponse> response) {
                            processPostSyncResponse(response);
                        }
                    };
                    postSyncResponseCall.enqueue(postSyncCallBack);
                }
            } else {
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                }
                Utilities.serverError(activity);
            }
        } else {
            Snackbar.make(activity.findViewById(R.id.toolbar), R.string.networkUnavailable, Snackbar.LENGTH_SHORT).show();
        }
    }
    private PostSyncBody buildpostSyncBody(){
        PatientsDB patientsDB = new PatientsDB(activity,null,null);
        return new PostSyncBody(patientsDB.getAllSyncingPatients());
    }

    private void processPostSyncResponse(Response<PostSyncResponse> response){
        PostSyncResponse postSyncResponse = null;
        if (response.isSuccessful()){
            postSyncResponse = response.body();
        }else {
            Converter<ResponseBody, PostSyncResponse> converter = retrofit.responseBodyConverter(PostSyncResponse.class,new Annotation[0]);
            try {
                postSyncResponse = converter.convert(response.errorBody());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sendPostSyncResponse.sendPostSyncSuccess(postSyncResponse,response.message());
    }
}
