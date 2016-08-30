package thontepu.naveen.patientmanager.Retrofit.Sync;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;
import thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi.PostSyncBody;
import thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi.PostSyncResponse;

/**
 * Created by mac on 8/31/16.
 */
public interface SyncApi {
    @POST
    Call<PostSyncResponse> postSyncApi(@Url String  url, @Body PostSyncBody postSyncBody);

}
