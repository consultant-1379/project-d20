package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyBuild;
import com.ericsson.projectd20.MyJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class JobService {
    private JobRepository jobRepository;
    @Autowired
    private BuildService service;

    public JobService(JobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public MyJob create(MyJob job){
        return jobRepository.save(job);
    }

    public MyJob findById(int id){
        return jobRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Job not found with id:" + id));
    }

    public List<MyJob> findAll(){
        List<MyJob> jobList = new ArrayList<>();
        Iterator<MyJob> iterator = jobRepository.findAll().iterator();
        iterator.forEachRemaining(jobList::add);
        return jobList;
    }

    public void delete(MyJob job){
        jobRepository.delete(job);
    }

    // Calculates failure for a particular job without caring about time
    public double buildFailureRate(int jobID){
        return calculateFailureRate(service.findAllByJobId(jobID));
    }

    // Calculates build failure rate for all builds within a particular time for a particular job
    public double buildFailureRate(Timestamp from, Timestamp to, int jobID){
        return calculateFailureRate(service.findBuildOfAParticularJobWithinTimeStamp(from, to, jobID));
    }

    // Calculates build failure rate for all builds within a particular time
    public double buildFailureRate(Timestamp from, Timestamp to){
        return calculateFailureRate(service.findBuildsWithinTimeStamp(from, to));
    }

    // Calculates build failure for all builds
    public double buildFailureRate(){
        return calculateFailureRate(service.findAll());
    }

    // Performs the calculations for failure rate
    public double calculateFailureRate(List<MyBuild> buildsForJob){
        double failCounter = 0;
        for(MyBuild build: buildsForJob){
            if(build.getResult().equalsIgnoreCase("FAILURE") || build.getResult().equalsIgnoreCase("ABORTED")){
                failCounter++;
            }
        }
        return (failCounter / buildsForJob.size()) * 100;
    }
}
