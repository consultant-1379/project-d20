package com.ericsson.projectd20.controller;

import com.ericsson.projectd20.MyBuild;
import com.ericsson.projectd20.dto.RecoveryTime;
import com.ericsson.projectd20.dto.TimePeriodRequest;
import com.ericsson.projectd20.service.BuildRecoveryTimeV2;
import com.ericsson.projectd20.service.BuildService;
import com.ericsson.projectd20.service.JobService;
import com.ericsson.projectd20.utilities.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@RestController
@CrossOrigin
public class BuildRecoveryTimeControllerV2 {

    @Autowired
    private BuildService buildService;

    @Autowired
    private JobService jobService;

    @Autowired
    private BuildRecoveryTimeV2 buildRecoveryTimeV2;

    /**
     * Method to return the build fail/success pairs of all jobs
     * @return List of filteredBuilds
     */
    @GetMapping("/build-recovery-time-v2/jobs/builds-status")
    public List<MyBuild> getBuildsStatus() {
        List<MyBuild> builds = buildService.findAll();
        return buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
    }

    /**
     * Method to return the build fail/success pairs of all jobs within time period
     * @return List of filteredBuilds
     */
    @PostMapping("/build-recovery-time-v2/jobs/builds-status/time-period")
    public List<MyBuild> getBuildsStatusWithinTimePeriod(@RequestBody TimePeriodRequest timePeriodRequest) {
        Timestamp timestampFrom = timePeriodRequest.getTimestampFrom();
        Timestamp timestampTo = timePeriodRequest.getTimestampTo();

        List<MyBuild> builds = buildService.findBuildsWithinTimeStamp(timestampFrom, timestampTo);
        return buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
    }

    /**
     * Method to return the build fail/success pairs of one job
     * @return List of filteredBuilds
     */
    @GetMapping("/build-recovery-time-v2/job/{id}/builds-status")
    public List<MyBuild> getBuildsStatusOneJob(@PathVariable int id) {
        List<MyBuild> builds = buildService.findAllByJobId(id);
        return buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
    }

    /**
     * Method to return the build fail/success pairs of one job
     * @return List of filteredBuilds
     */
    @PostMapping("/build-recovery-time-v2/job/{id}/builds-status/time-period")
    public List<MyBuild> getBuildsStatusOneJobWithinTimePeriod(@PathVariable int id, @RequestBody TimePeriodRequest timePeriodRequest) {

        Timestamp timestampFrom = timePeriodRequest.getTimestampFrom();
        Timestamp timestampTo = timePeriodRequest.getTimestampTo();

        List<MyBuild> builds = buildService.findBuildOfAParticularJobWithinTimeStamp(timestampFrom, timestampTo, id);
        return buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
    }

    /**
     * Method to get the Build Recovery Time of all jobs
     * @return RecoveryTime
     */
    @GetMapping("/build-recovery-time-v2/jobs")
    public RecoveryTime getRecoveryTimeAllJobs() {
        List<MyBuild> builds = buildService.findAll();
        List<MyBuild> filteredBuilds = buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
        List<Long> timeDiff = buildRecoveryTimeV2.getTimeDifference(filteredBuilds);
        long median = Utilities.median(timeDiff);
        long standardDeviation = (long)Utilities.standardDeviation(timeDiff);

        return new RecoveryTime(Utilities.convertToMinutes(median), Utilities.convertToMinutes(standardDeviation));
    }

    /**
     * Method to get the Build Recovery Time of all jobs within time period
     * @param timePeriodRequest
     * @return
     * @throws IOException
     */
    @PostMapping("/build-recovery-time-v2/jobs/time-period")
    public RecoveryTime getRecoveryTimeAllJobsWithTimePeriod(@RequestBody TimePeriodRequest timePeriodRequest) {
        Timestamp timestampFrom = timePeriodRequest.getTimestampFrom();
        Timestamp timestampTo = timePeriodRequest.getTimestampTo();

        List<MyBuild> builds = buildService.findBuildsWithinTimeStamp(timestampFrom, timestampTo);
        List<MyBuild> filteredBuilds = buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
        List<Long> timeDiff = buildRecoveryTimeV2.getTimeDifference(filteredBuilds);
        long median = Utilities.median(timeDiff);
        long standardDeviation = (long)Utilities.standardDeviation(timeDiff);

        return new RecoveryTime(Utilities.convertToMinutes(median), Utilities.convertToMinutes(standardDeviation));
    }

    /**
     * Method to get the Build Recovery Time of a particular job
     * @return RecoveryTime
     */
    @GetMapping("/build-recovery-time-v2/job/{id}")
    public RecoveryTime getRecoveryTime(@PathVariable int id) {
        List<MyBuild> builds = buildService.findAllByJobId(id);

        List<MyBuild> filteredBuilds = buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
        List<Long> timeDiff = buildRecoveryTimeV2.getTimeDifference(filteredBuilds);
        long median = Utilities.median(timeDiff);
        long standardDeviation = (long)Utilities.standardDeviation(timeDiff);

        return new RecoveryTime(Utilities.convertToMinutes(median), Utilities.convertToMinutes(standardDeviation));
    }

    /**
     * Method to get the Build Recovery Time of a particular job within time period
     * @return RecoveryTime
     */
    @PostMapping("/build-recovery-time-v2/job/{id}/time-period")
    public RecoveryTime getRecoveryTime(@PathVariable int id, @RequestBody TimePeriodRequest timePeriodRequest) {
        Timestamp timestampFrom = timePeriodRequest.getTimestampFrom();
        Timestamp timestampTo = timePeriodRequest.getTimestampTo();

        List<MyBuild> builds = buildService.findBuildOfAParticularJobWithinTimeStamp(timestampFrom, timestampTo, id);

        List<MyBuild> filteredBuilds = buildRecoveryTimeV2.getFailedAndSuccessfulBuilds(builds);
        List<Long> timeDiff = buildRecoveryTimeV2.getTimeDifference(filteredBuilds);
        long median = Utilities.median(timeDiff);
        long standardDeviation = (long)Utilities.standardDeviation(timeDiff);

        return new RecoveryTime(Utilities.convertToMinutes(median), Utilities.convertToMinutes(standardDeviation));
    }

}
