package thontepu.naveen.patientmanager;

import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static org.hamcrest.CoreMatchers.*;


import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.Database.PatientsDB;
import thontepu.naveen.patientmanager.Utils.Constants;
import static org.junit.Assert.*;


/**
 * Created by mac on 8/31/16.
 */

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private PatientsDB patientsDB;
    Gson gson;

    @Before
    public void setup() throws Exception{
        getTargetContext().deleteDatabase(PatientsDB.DATABASE_NAME);
        patientsDB = new PatientsDB(getTargetContext(),null,null);
        gson = new Gson();
    }
    @After
    public void closeDB() throws Exception{
        patientsDB.close();
    }

    @Test
    public void addPatient() throws Exception{
        PatientPojo patientPojo = getTestPatient();
        patientsDB.addPatient(patientPojo);
        patientPojo = patientsDB.getPatient(PatientsDB.COLUMN_NAME,"test");
        System.out.println("pa = "+gson.toJson(patientPojo));
        assertThat(patientPojo,notNullValue());
        assertTrue("test".equalsIgnoreCase(patientPojo.getName()));
    }

    @Test
    public void deletePatient() throws Exception{
        PatientPojo patientPojo = getTestPatient();
        patientsDB.addPatient(patientPojo);
        PatientPojo de = patientsDB.getPatient(PatientsDB.COLUMN_NAME,"test");
        System.out.println("de = "+gson.toJson(de));
        patientsDB.deletePatient(de);
        assertThat(patientsDB.getPatient(PatientsDB.COLUMN_ID,de.getId()+""),nullValue());
    }

    public PatientPojo getTestPatient(){
        PatientPojo patientPojo = new PatientPojo();
        patientPojo.setName("test");
        patientPojo.setAge(17);
        patientPojo.setMigrain(0);
        patientPojo.setDrugs(1);
        patientPojo.setGender("female");
        patientPojo.setProbability(25);
        patientPojo.setTimeStamp(System.currentTimeMillis()+"");
        patientPojo.setStatus(Constants.Status.DELETE);
        return patientPojo;
    }
}
