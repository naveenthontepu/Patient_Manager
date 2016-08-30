package thontepu.naveen.patientmanager.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;
import thontepu.naveen.patientmanager.Calculations.CalculateProbability;
import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.Database.PatientsDB;
import thontepu.naveen.patientmanager.R;
import thontepu.naveen.patientmanager.Utils.Constants;
import thontepu.naveen.patientmanager.Utils.Utilities;

public class PatientDetailsEdit extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    boolean newPatient;
    PatientPojo patientPojo;
    PatientsDB patientsDB;
    @Bind(R.id.profile_pic)
    ImageView profilePic;
    @Bind(R.id.patientNameEt)
    EditText patientNameEt;
    @Bind(R.id.patientNameTil)
    TextInputLayout patientNameTil;
    @Bind(R.id.patientAgeEt)
    AutoCompleteTextView patientAgeEt;
    @Bind(R.id.maleRadioButton)
    RadioButton maleRadioButton;
    @Bind(R.id.femaleRadioButton)
    RadioButton femaleRadioButton;
    @Bind(R.id.genderRadioGroup)
    RadioGroup genderRadioGroup;
    @Bind(R.id.migrainCheckBox)
    CheckBox migrainCheckBox;
    @Bind(R.id.drugsCheckBox)
    CheckBox drugsCheckBox;
    InputMethodManager imm;
    int age;
    int probability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_details_edit);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_done_black_24dp);
        }
        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            newPatient = intent.getBooleanExtra(Constants.IntentExtraStrings.NEW_PATIENT, false);
            if (!newPatient) {
                patientPojo = (PatientPojo) intent.getSerializableExtra(Constants.IntentExtraStrings.PATIENT_POJO);
            } else {
                patientPojo = new PatientPojo();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialize();
    }

    private void initialize() {
        patientsDB = new PatientsDB(this, null, null);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        if (!newPatient) {
            populateFields();
            imm.hideSoftInputFromWindow(patientAgeEt.getWindowToken(), InputMethodManager.RESULT_HIDDEN);
        } else {
            setDefaultValues();
        }
        String[] ageArray = Utilities.ageArray();
        Utilities.printLog("array = " + Arrays.toString(ageArray));
        ArrayAdapter<String> ageAdapter = new ArrayAdapter<>(this, R.layout.autocomplete_item_view, ageArray);
        patientAgeEt.setThreshold(0);
        patientAgeEt.setAdapter(ageAdapter);
        patientAgeEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if (i == EditorInfo.IME_ACTION_DONE) {
                    Utilities.printLog("####the action = " + i);
                    imm.hideSoftInputFromWindow(textView.getWindowToken(), InputMethodManager.RESULT_HIDDEN);
                    return true;
                }
                return false;
            }
        });
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.maleRadioButton:
                        patientPojo.setGender(Constants.PatientPojoConstants.MALE);
                        break;
                    case R.id.femaleRadioButton:
                        patientPojo.setGender(Constants.PatientPojoConstants.FEMALE);
                        break;
                }
            }
        });
        migrainCheckBox.setOnCheckedChangeListener(this);
        drugsCheckBox.setOnCheckedChangeListener(this);
    }

    private void setDefaultValues() {
        patientPojo.setGender(Constants.PatientPojoConstants.MALE);
        patientPojo.setDrugs(0);
        patientPojo.setMigrain(0);
        patientPojo.setProbability(0);
    }

    private void populateFields() {
        patientNameEt.setText(patientPojo.getName() != null ? patientPojo.getName() : "");
        patientAgeEt.setText(patientPojo.getAge() + "");
        if (patientPojo.getGender().equalsIgnoreCase(Constants.PatientPojoConstants.MALE)) {
            maleRadioButton.setChecked(true);
        } else {
            femaleRadioButton.setChecked(true);
        }
        migrainCheckBox.setChecked(patientPojo.getMigrain() == 1);
        drugsCheckBox.setChecked(patientPojo.getDrugs() == 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Utilities.printLog("back button clicked");
                savePatientData();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void savePatientData() {
        if (validFields()) {
            probability = CalculateProbability.getProbability(patientPojo);
            patientPojo.setProbability(probability);
            patientPojo.setTimeStamp(System.currentTimeMillis() + "");
            Utilities.printLog("patientPojo = " + (new Gson().toJson(patientPojo)));
            if (newPatient) {
                patientPojo.setStatus(Constants.Status.NEW);
                patientsDB.addPatient(patientPojo);
            } else {
                patientPojo.setStatus(Constants.Status.UPDATED);
                patientsDB.updatePatient(patientPojo);
            }
            Intent patientDetails = new Intent(this, PatientDetails.class);
            patientDetails.putExtra(Constants.IntentExtraStrings.PATIENT_POJO, patientPojo);
            if (newPatient) {
                startActivity(patientDetails);
            } else {
                setResult(RESULT_OK,patientDetails);
            }
            finish();
        }
    }

    private boolean validFields() {
        if (!patientNameEt.getText().toString().equals("")) {
            patientPojo.setName(patientNameEt.getText().toString());
            if (!patientAgeEt.getText().toString().equals("")) {
                try {
                    age = Integer.parseInt(patientAgeEt.getText().toString());
                    patientPojo.setAge(age);
                    return true;
                } catch (Exception e) {
                    showError(null, "Please Enter Patients age in years", patientAgeEt);
                }
            } else {
                showError(null, "Please Enter Patients Age", patientAgeEt);
            }
        } else {
            showError(patientNameTil, "Please enter Patients Name", patientNameEt);
        }
        return false;
    }

    public void showError(TextInputLayout textInputLayout, String message, EditText editText) {
        if (textInputLayout != null) {
            textInputLayout.setErrorEnabled(true);
            textInputLayout.setError(message);
        } else {
            Snackbar.make(editText, message, Snackbar.LENGTH_SHORT).show();
        }
        editText.requestFocus();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        switch (compoundButton.getId()) {
            case R.id.drugsCheckBox:
                patientPojo.setDrugs(b ? 1 : 0);
                break;
            case R.id.migrainCheckBox:
                patientPojo.setMigrain(b ? 1 : 0);
                break;
        }
    }
}
