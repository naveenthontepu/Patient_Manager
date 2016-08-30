package thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi;

import com.google.gson.annotations.SerializedName;

import thontepu.naveen.patientmanager.Retrofit.Info;

/**
 * Created by mac on 8/31/16.
 */
public class PostSyncResponse {
    @SerializedName("info")
    Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }
}
