package com.ericsson.projectd20.dto;

import java.sql.Timestamp;

public class TimePeriodRequest {

    private Timestamp timestampFrom;
    private Timestamp timestampTo;

    public TimePeriodRequest(){}

    public TimePeriodRequest(Timestamp timestampFrom, Timestamp timestampTo)
    {
        this.timestampFrom = timestampFrom;
        this.timestampTo = timestampTo;
    }

    public TimePeriodRequest(long timestampFrom, long timestampTo)
    {
        this.timestampFrom = new Timestamp(timestampFrom);
        this.timestampTo = new Timestamp(timestampTo);
    }

    public TimePeriodRequest(Long timestampFrom, Long timestampTo)
    {
        this.timestampFrom = new Timestamp(timestampFrom);
        this.timestampTo = new Timestamp(timestampTo);
    }

    public Timestamp getTimestampFrom() {
        return timestampFrom;
    }

    public void setTimestampFrom(Timestamp timestampFrom) {
        this.timestampFrom = timestampFrom;
    }

    public Timestamp getTimestampTo() {
        return timestampTo;
    }

    public void setTimestampTo(Timestamp timestampTo) {
        this.timestampTo = timestampTo;
    }

    @Override
    public String toString() {
        return "TimePeriodRequest{" +
                "timestampFrom=" + timestampFrom +
                ", timestampTo=" + timestampTo +
                '}';
    }
}
