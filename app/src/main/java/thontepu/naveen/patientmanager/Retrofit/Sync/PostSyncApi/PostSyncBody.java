package thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import thontepu.naveen.patientmanager.Database.PatientPojo;

/**
 * Created by mac on 8/31/16.
 */
public class PostSyncBody {
    @SerializedName("patients")
    List<PatientPojo> patientPojos;

    public PostSyncBody(List<PatientPojo> patientPojos) {
        this.patientPojos = patientPojos;
    }
}
