package com.ericsson.projectd20.controller;

import com.ericsson.projectd20.dto.TimePeriodRequest;
import com.ericsson.projectd20.service.BuildService;

import com.ericsson.projectd20.service.JobService;
import com.ericsson.projectd20.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;

@CrossOrigin
@RestController
public class BuildFailureController {

    @Autowired
    private BuildService buildService;

    @Autowired
    private JobService jobService;

    /**
     * Method that returns the build failure rate for all builds for every job
     * @return double failPercentage
     */
    @GetMapping("/build-failure-rate/jobs")
    public double buildFailureRateAllJobs()
    {
        double failPercentage = jobService.buildFailureRate();
        return Utilities.validateDouble(failPercentage);
    }

    /**
     * Method that returns the build failure rate for all builds for every job within a time period
     * @return double failPercentage
     */
    @PostMapping("/build-failure-rate/jobs/time-period")
    public double buildFailureRateAllJobsInTimePeriod(@RequestBody TimePeriodRequest timePeriodRequest)
    {
        Timestamp timestampFrom = timePeriodRequest.getTimestampFrom();
        Timestamp timestampTo = timePeriodRequest.getTimestampTo();

        double failPercentage = jobService.buildFailureRate(timestampFrom, timestampTo);
        return Utilities.validateDouble(failPercentage);
    }

    /**
     * Method that returns the build failure rate for all builds in ONE job
     * @param id
     * @return double failPercentage
     */
    @GetMapping("/build-failure-rate/job/{id}")
    public double buildFailureRateOneJob(@PathVariable int id)
    {
        double failPercentage = jobService.buildFailureRate(id);
        return Utilities.validateDouble(failPercentage);
    }

    /**
     * Method that returns the build failure rate for all builds for every job within a time period
     * @return double failPercentage
     */
    @PostMapping("/build-failure-rate/job/{id}/time-period")
    public double buildFailureRateOneJobInTimePeriod(@PathVariable int id, @RequestBody TimePeriodRequest timePeriodRequest)
    {

        Timestamp timestampFrom = timePeriodRequest.getTimestampFrom();
        Timestamp timestampTo = timePeriodRequest.getTimestampTo();

        double failPercentage = jobService.buildFailureRate(timestampFrom, timestampTo, id);
        return Utilities.validateDouble(failPercentage);
    }
}
