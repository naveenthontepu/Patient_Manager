package thontepu.naveen.patientmanager.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.Database.PatientsDB;
import thontepu.naveen.patientmanager.R;
import thontepu.naveen.patientmanager.RecyclerViewFiles.ItemClickInterface;
import thontepu.naveen.patientmanager.RecyclerViewFiles.PatientsRecyclerViewAdapter;
import thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi.PostSyncController;
import thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi.PostSyncResponse;
import thontepu.naveen.patientmanager.Retrofit.Sync.PostSyncApi.SendPostSyncResponse;
import thontepu.naveen.patientmanager.Utils.Constants;
import thontepu.naveen.patientmanager.Utils.Utilities;

public class PatientsView extends AppCompatActivity implements ItemClickInterface, SendPostSyncResponse {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.patientList)
    RecyclerView patientList;
    @Bind(R.id.addNewPatient)
    FloatingActionButton addNewPatient;
    List<PatientPojo> data;
    PatientsDB patientsDB;
    PatientsRecyclerViewAdapter patientsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_view);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialize();
    }

    private void initialize() {
        patientsDB = new PatientsDB(this, null, null);
        data = patientsDB.getAllPatients();
        patientsRecyclerViewAdapter = new PatientsRecyclerViewAdapter(this, this, data);
        patientList.setLayoutManager(new LinearLayoutManager(this));
        patientList.setAdapter(patientsRecyclerViewAdapter);
        syncRecords();
    }

    private void syncRecords() {
        if (patientsDB.getAllSyncingPatients().size() > 0) {
            PostSyncController postSyncController = new PostSyncController(this, this);
            postSyncController.postSyncApiCall();
        }
    }

    @OnClick(R.id.addNewPatient)
    public void onClick() {
        Intent intent = new Intent(this, PatientDetailsEdit.class);
        intent.putExtra(Constants.IntentExtraStrings.NEW_PATIENT, true);
        startActivity(intent); //, Constants.CallbackConstants.NEW_PATIENT);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent patientDetails = new Intent(this, PatientDetails.class);
        patientDetails.putExtra(Constants.IntentExtraStrings.PATIENT_POJO, data.get(position));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptionsCompat options = getActivityOtptions(view);
            startActivity(patientDetails, options.toBundle());
        } else {
            startActivity(patientDetails);
            overridePendingTransition(R.anim.fade_in, R.anim.fadi_out);
        }
    }

    private ActivityOptionsCompat getActivityOtptions(View view) {
        Pair<View, String> p1 = Pair.create(view.findViewById(R.id.patientPic), "profilePic");
        Pair<View, String> p2 = Pair.create(view.findViewById(R.id.patientName), getString(R.string.name));
        Pair<View, String> p3 = Pair.create(view.findViewById(R.id.patientProbability), getString(R.string.probTrans));
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void sendPostSyncSuccess(PostSyncResponse postSyncResponse, String msg) {
        // TODO: 8/31/16 have to remove the update records for any response as for showing kept it this way
        if (postSyncResponse != null) {
            if (postSyncResponse.getInfo().getStatus().equalsIgnoreCase("success")) {
                updateRecords();
            }else {
                Utilities.showAlert(this,postSyncResponse.getInfo().getMessage());
                updateRecords();
            }
        }else {
            Utilities.showAlert(this,msg);
            updateRecords();
        }
    }

    private void updateRecords() {
        List<PatientPojo> patientPojos = patientsDB.getAllSyncingPatients();
        for (PatientPojo patientPojo : patientPojos) {
            if (Constants.Status.DELETE.equalsIgnoreCase(patientPojo.getStatus())) {
                patientsDB.deletePatient(patientPojo);
            } else {
                patientPojo.setStatus(Constants.Status.SYNCED);
                patientsDB.updatePatient(patientPojo);
            }
        }
    }
}
