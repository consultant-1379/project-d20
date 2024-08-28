package com.ericsson.projectd20.dto;

public class RecoveryTime {

    private double medianInMinutes;
    private double standardDeviationInMinutes;

    public RecoveryTime(double medianInMinutes, double standardDeviationInMinutes) {
        this.medianInMinutes = medianInMinutes;
        this.standardDeviationInMinutes = standardDeviationInMinutes;
    }

    public double getMedianInMinutes() {
        return medianInMinutes;
    }

    public void setMedianInMinutes(double medianInMinutes) {
        this.medianInMinutes = medianInMinutes;
    }

    public double getStandardDeviationInMinutes() {
        return standardDeviationInMinutes;
    }

    public void setStandardDeviationInMinutes(double standardDeviationInMinutes) {
        this.standardDeviationInMinutes = standardDeviationInMinutes;
    }

    @Override
    public String toString() {
        return "RecoveryTime{" +
                "medianInMinutes=" + medianInMinutes +
                ", standardDeviationInMinutes=" + standardDeviationInMinutes +
                '}';
    }
}
