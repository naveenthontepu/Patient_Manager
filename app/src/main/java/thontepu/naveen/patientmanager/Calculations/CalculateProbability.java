package thontepu.naveen.patientmanager.Calculations;

import android.support.annotation.NonNull;

import thontepu.naveen.patientmanager.Database.PatientPojo;
import thontepu.naveen.patientmanager.R;
import thontepu.naveen.patientmanager.Utils.Constants;

/**
 * Created by mac on 8/31/16.
 */
public class CalculateProbability {
    private static final double AGE_WEIGHT = 1;
    private static final double GENDER_WEIGHT = 1;
    private static final double MIGRAIN_WEIGHT = 1;
    private static final double DRUG_WEIGHT = 1;
    private static final double TOTAL = 4;



    public static int getProbability(@NonNull PatientPojo patientPojo){
        double proba = 0;
        if (patientPojo.getAge() <= 15) {
            proba += (100*(AGE_WEIGHT/TOTAL));
        }
        if (Constants.PatientPojoConstants.MALE.equalsIgnoreCase(patientPojo.getGender())) {
            proba += (100*(GENDER_WEIGHT/TOTAL));
        }
        if (patientPojo.getMigrain() == 1) {
            proba += (100*(MIGRAIN_WEIGHT/TOTAL));
        }
        if (patientPojo.getDrugs() == 1) {
            proba += (100*(DRUG_WEIGHT/TOTAL));
        }
        return (int) proba;
    }
}
