package thontepu.naveen.patientmanager.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.NavUtils;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.R;
import thontepu.naveen.patientmanager.Utils.Constants;
import thontepu.naveen.patientmanager.Utils.Utilities;

public class PatientDetails extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    PatientPojo patientPojo;
    Gson gson;
    @Bind(R.id.profile_pic)
    ImageView profilePic;
    @Bind(R.id.patientName)
    TextView patientName;
    @Bind(R.id.patientAge)
    TextView patientAge;
    @Bind(R.id.gender)
    TextView gender;
    @Bind(R.id.migrainCheckBox)
    CheckBox migrainCheckBox;
    @Bind(R.id.drugsCheckBox)
    CheckBox drugsCheckBox;
    @Bind(R.id.probability)
    TextView probability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onCreate");
        setContentView(R.layout.activity_patient_details);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        gson = new Gson();
        if (intent.getExtras() != null) {
            patientPojo = (PatientPojo) intent.getSerializableExtra(Constants.IntentExtraStrings.PATIENT_POJO);
            Utilities.printLog("pojo = " + (gson.toJson(patientPojo)));
        }else {
            onBackPressed();
        }
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onRestart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onRestoreInstanceState");
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateViews();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onResume");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onPostCreate");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onPostResume");
    }


    private void populateViews() {
        patientName.setText(patientPojo.getName());
        patientAge.setText(getString(R.string.patient_age)+" "+patientPojo.getAge()+" years");
        gender.setText(getString(R.string.gender)+" "+patientPojo.getGender().toUpperCase());
        migrainCheckBox.setChecked(patientPojo.getMigrain()==1);
        migrainCheckBox.setClickable(false);
        drugsCheckBox.setChecked(patientPojo.getDrugs() == 1);
        drugsCheckBox.setClickable(false);
        probability.setText(patientPojo.getProbability()+" %");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patient_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent(this, PatientDetailsEdit.class);
                intent.putExtra(Constants.IntentExtraStrings.NEW_PATIENT, false);
                intent.putExtra(Constants.IntentExtraStrings.PATIENT_POJO, patientPojo);
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    startActivityForResult(intent, Constants.CallbackConstants.EDIT_PATIENT,getActivityOptions().toBundle());
                }else {
                    startActivityForResult(intent, Constants.CallbackConstants.EDIT_PATIENT);
                    overridePendingTransition(R.anim.fade_in, R.anim.fadi_out);
                }
                break;
            case android.R.id.home:
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    supportFinishAfterTransition();
                }else {
                    Utilities.goToParentActivity(this);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private ActivityOptionsCompat getActivityOptions() {
        Pair<View, String> p1 = Pair.create((View) profilePic, getString(R.string.profilePic));
        Pair<View, String> p2 = Pair.create((View) patientName, getString(R.string.name));
        Pair<View, String> p3 = Pair.create((View) patientAge, getString(R.string.age));
        Pair<View, String> p4 = Pair.create((View) gender, getString(R.string.gender));
        return ActivityOptionsCompat.makeSceneTransitionAnimation(this, p1, p2, p3,p4);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK){
            if (requestCode == Constants.CallbackConstants.EDIT_PATIENT){
                patientPojo = (PatientPojo) data.getSerializableExtra(Constants.IntentExtraStrings.PATIENT_POJO);
                Utilities.printLog("result = "+gson.toJson(patientPojo));
                if (patientPojo!=null){
                    populateViews();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onPause");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onSaveInstanceState 1");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utilities.printLog(Constants.Tags.ACTIVITY_STATE,"onDestroy");
    }
}
