package thontepu.naveen.patientmanager;

import android.content.Context;

import org.junit.Test;
import org.mockito.Mock;

import thontepu.naveen.patientmanager.Calculations.CalculateProbability;
import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.Database.PatientsDB;
import thontepu.naveen.patientmanager.Utils.Constants;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void calculateProba() throws Exception{
        PatientPojo patientPojo = new PatientPojo(0,null,17,0,1,null,"female",0);
        assertEquals(CalculateProbability.getProbability(patientPojo),25);
    }
    @Mock
    Context context;
    @Test
    public void delete() throws Exception{
        PatientsDB patientsDB = new PatientsDB(context,null,null);
        PatientPojo patientPojo = new PatientPojo();
        patientPojo.setName("test");
        patientPojo.setAge(17);
        patientPojo.setMigrain(0);
        patientPojo.setDrugs(1);
        patientPojo.setGender("female");
        patientPojo.setProbability(25);
        patientPojo.setTimeStamp(System.currentTimeMillis()+"");
        patientPojo.setStatus(Constants.Status.DELETE);
        patientsDB.addPatient(patientPojo);
        PatientPojo de = patientsDB.getPatient(PatientsDB.COLUMN_NAME,"test");
        if (de !=null) {
            patientsDB.deletePatient(de);
            assertThat(patientsDB.getPatient(PatientsDB.COLUMN_ID,""+de.getId()),nullValue());
        }
        throw new Exception();
    }
    @Test
    public void add() throws Exception{
        System.out.println("add");
        PatientsDB patientsDB = new PatientsDB(context,null,null);
        patientsDB.onCreate(patientsDB.getWritableDatabase());
        PatientPojo patientPojo = new PatientPojo();
        patientPojo.setName("test");
        patientPojo.setAge(17);
        patientPojo.setMigrain(0);
        patientPojo.setDrugs(1);
        patientPojo.setGender("female");
        patientPojo.setProbability(25);
        patientPojo.setTimeStamp(System.currentTimeMillis()+"");
        patientPojo.setStatus(Constants.Status.DELETE);
        System.out.println("pojo = "+patientPojo);
        patientsDB.addPatient(patientPojo);
        PatientPojo ad = patientsDB.getPatient(PatientsDB.COLUMN_NAME,"test");
        assertThat(ad,notNullValue());
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}