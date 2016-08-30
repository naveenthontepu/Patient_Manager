package thontepu.naveen.patientmanager.Utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.util.Log;

import thontepu.naveen.patientmanager.BuildConfig;
import thontepu.naveen.patientmanager.R;

/**
 * Created by mac on 8/30/16.
 */
public class Utilities {
    public static void printLog(String msg) {
        String TAG = "thontepu.naveen.patientmanager";
        printLog(TAG, msg);
    }

    /**
     * Prints log with tag that is provided.
     *
     * @param tag
     * @param s
     */
    public static void printLog(String tag, String s) {
        if (BuildConfig.DEBUG && s != null) {
            Log.i(tag, s);
        }
    }

    public static String[] ageArray(){
        String[] array = new String[100];
        for (int i=0;i<100;i++){
            int k = i+1;
            array[i]=""+k;
        }
        return array;
    }

    public static void goToParentActivity(Activity activity) {
        Intent intent = NavUtils.getParentActivityIntent(activity);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.fade_in, R.anim.fadi_out);
    }

}
