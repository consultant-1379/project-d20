package com.ericsson.projectd20;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class MyBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int number;
    private int jobID;
    private Timestamp timeCreated;
    private String result;

    public MyBuild(){

    }

    public MyBuild(int number, int jobID, Timestamp timeCreated, String result){
        this.number = number;
        this.jobID = jobID;
        this.timeCreated = timeCreated;
        this.result = result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Timestamp getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(Timestamp timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}


