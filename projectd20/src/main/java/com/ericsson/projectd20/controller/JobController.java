package com.ericsson.projectd20.controller;

import com.ericsson.projectd20.MyJob;
import com.ericsson.projectd20.service.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class JobController {

    @Autowired
    private JobRepository jobRepository;

    @GetMapping("/jobs")
    public List<String> getJobNames()
    {
        Iterable<MyJob> jobs = jobRepository.findAll();
        List<String> jobNames = new ArrayList<>();
        for(MyJob job: jobs)
        {
            String value = job.getName() + ',' + job.getUrl() + ',' + job.getId();
            jobNames.add(value);
        }
        return jobNames;
    }

}
