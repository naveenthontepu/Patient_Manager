package thontepu.naveen.patientmanager.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import thontepu.naveen.patientmanager.Utils.Constants;
import thontepu.naveen.patientmanager.Utils.Utilities;

public class PatientsDB extends SQLiteOpenHelper {

    private static int DATABASE_VERSION=1;
    private static final String TAG = "com.rapido.passenger";
    public static final String DATABASE_NAME = "Patients.db";
    private static final String TABLE_PATIENTS = "Patients";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ID = "_id";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_MIGRAIN = "migrain";
    private static final String COLUMN_DRUGS = "drugs";
    private static final String COLUMN_STATUS = "status";
    private static final String COLUMN_PROBABILITY = "probability";
    private static final String COLUMN_TIMESTAMP = "timeStamp";

    /**
     * Patients DB is initilized
     * @param context
     * @param name
     * @param factory
     */
    public PatientsDB(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context,DATABASE_NAME, factory,DATABASE_VERSION);
    }

    /**
     * crates the SQLite database
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("db on create");
        String query = "CREATE TABLE "+ TABLE_PATIENTS +"( "+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME +" TEXT, "+
                COLUMN_AGE +" INTEGER DEFAULT 0, "+
                COLUMN_MIGRAIN+" INTEGER DEFAULT 0, "+
                COLUMN_DRUGS+" INTEGER DEFAULT 0, "+
                COLUMN_STATUS+" TEXT, "+
                COLUMN_PROBABILITY+" INTEGER DEFAULT 0,"+
                COLUMN_TIMESTAMP+" TEXT, "+
                COLUMN_GENDER +" TEXT );";
        db.execSQL(query);
    }

    /**
     * add patient to the database
     * @param patientPojo
     */
    public void addPatient(PatientPojo patientPojo){
        ContentValues v= getContentValue(patientPojo);
        SQLiteDatabase db =getReadableDatabase();
        db.insert(TABLE_PATIENTS,null,v);
        db.close();
        Log.i(TAG, "Addition Done");
    }

    private ContentValues getContentValue(PatientPojo patientPojo){
        ContentValues v= new ContentValues();
        v.put(COLUMN_NAME,patientPojo.getName());
        v.put(COLUMN_GENDER,patientPojo.getGender());
        v.put(COLUMN_AGE,patientPojo.getAge());
        v.put(COLUMN_DRUGS, patientPojo.getDrugs());
        v.put(COLUMN_MIGRAIN,patientPojo.getMigrain());
        v.put(COLUMN_STATUS,patientPojo.getStatus());
        v.put(COLUMN_PROBABILITY, patientPojo.getProbability());
        v.put(COLUMN_TIMESTAMP, patientPojo.getTimeStamp());
        return v;
    }

    public void updatePatient(PatientPojo patientPojo){
        ContentValues v = getContentValue(patientPojo);
        SQLiteDatabase db = getReadableDatabase();
        db.update(TABLE_PATIENTS,v,COLUMN_ID+"="+patientPojo.getId(),null);
    }

    public void deletePatient(PatientPojo patientPojo){
        ContentValues v = getContentValue(patientPojo);
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_PATIENTS,COLUMN_ID+"="+patientPojo.getId(),null);
    }

    public PatientPojo getPatient(String selection,String id){
        SQLiteDatabase db = getReadableDatabase();
//        Cursor c = db.query(TABLE_PATIENTS,null,selection+" =? ",new String[]{id},null,null,null);
        Cursor c = db.query(true,TABLE_PATIENTS,null,selection+" =? ",new String[]{id},null,null,null,null);
        Utilities.printLog("the size = "+c.getCount());
        c.moveToFirst();
        if (c.getCount()==0){
            return null;
        }
        PatientPojo patientPojo = new PatientPojo();
        patientPojo.setId(c.getInt(c.getColumnIndex(COLUMN_ID)));
        patientPojo.setProbability(c.getInt(c.getColumnIndex(COLUMN_PROBABILITY)));
        patientPojo.setName(c.getString(c.getColumnIndex(COLUMN_NAME)));
        return patientPojo;
    }

    /**
     * deleting all records from the database
     */
    public void deletingDatabase(){
        SQLiteDatabase db = getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PATIENTS + ";");
        onCreate(db);
    }

    /**
     * get patients list from the data
     * @return
     */
    public List<PatientPojo> getAllPatients(){
        return getPatientsList(Constants.Status.DELETE);
    }
    public List<PatientPojo> getAllSyncingPatients(){
        return getPatientsList(Constants.Status.SYNCED);
    }
    public List<PatientPojo> getPatientsList(String status){
        SQLiteDatabase db = getReadableDatabase();
        List<PatientPojo> pojoList = new ArrayList<>();
        PatientPojo temp;
        Cursor c = db.query(TABLE_PATIENTS,null,null,null,null,null,COLUMN_NAME+" COLLATE LOCALIZED ASC");
        c.moveToFirst();
        while (!c.isAfterLast()){
            temp = new PatientPojo(c.getInt(c.getColumnIndex(COLUMN_ID)),
                    c.getString(c.getColumnIndex(COLUMN_NAME)),
                    c.getInt(c.getColumnIndex(COLUMN_AGE)),
                    c.getInt(c.getColumnIndex(COLUMN_MIGRAIN)),
                    c.getInt(c.getColumnIndex(COLUMN_DRUGS)),
                    c.getString(c.getColumnIndex(COLUMN_STATUS)),
                    c.getString(c.getColumnIndex(COLUMN_GENDER)),
                    c.getInt(c.getColumnIndex(COLUMN_PROBABILITY)));
            if (!status.equalsIgnoreCase(temp.getStatus())) {
                pojoList.add(temp);
            }
            Utilities.printLog("pa = "+ DatabaseUtils.dumpCurrentRowToString(c));
            c.moveToNext();
        }
        if (pojoList.size()!=0){
            return pojoList;
        }
        return Collections.emptyList();
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXITS "+ TABLE_PATIENTS);
        DATABASE_VERSION = newVersion;
        onCreate(db);
    }
}

