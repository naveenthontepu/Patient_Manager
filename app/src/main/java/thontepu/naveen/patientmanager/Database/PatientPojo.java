package thontepu.naveen.patientmanager.Database;

import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by mac on 8/30/16.
 */
public class PatientPojo implements Serializable{
    int id;
    String name;
    int age;
    int migrain;
    int drugs;
    String status;
    String gender;
    int probability;
    String timeStamp;

    public PatientPojo() {
    }

    public PatientPojo(int id, String name, int age, int migrain, int drugs, String status, String gender, int probability) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.migrain = migrain;
        this.drugs = drugs;
        this.status = status;
        this.gender = gender;
        this.probability = probability;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMigrain() {
        return migrain;
    }

    public void setMigrain(int migrain) {
        this.migrain = migrain;
    }

    public int getDrugs() {
        return drugs;
    }

    public void setDrugs(int drugs) {
        this.drugs = drugs;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
