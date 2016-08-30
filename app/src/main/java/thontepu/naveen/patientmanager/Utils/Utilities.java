package thontepu.naveen.patientmanager.Utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager connectivityManager = null;
        try {
            connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager!=null){
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info!=null && info.isConnected()){
                    return true;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public static void serverError(Activity activity){
//        Log.i(TAG, "server Error");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setTitle(activity.getResources().getString(R.string.error));
        alertDialogBuilder
                .setMessage(activity.getResources().getString(R.string.servererror))
                .setCancelable(false)

                .setNegativeButton(activity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        try {
            if (!activity.isFinishing()) {
                try {
                    alertDialog.show();
                }catch (Exception ignored){}
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static ProgressDialog getProgressDialog(Activity activity, String msg){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(msg);
        return progressDialog;
    }
    public static void showProgressDialog(ProgressDialog progressDialog){
        if (progressDialog!=null){
            if (!progressDialog.isShowing()){
                progressDialog.show();
            }
        }
    }


}
