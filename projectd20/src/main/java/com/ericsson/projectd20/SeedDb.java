package com.ericsson.projectd20;

import com.ericsson.projectd20.service.BuildRepository;
import com.ericsson.projectd20.service.JobRepository;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Component
@Profile("Initialization")
public class SeedDb{
    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private BuildRepository buildRepository;

    @Value("${jenkins.server}")
    private String uri;

    @PostConstruct
    public void init() throws URISyntaxException {
        seedDB();
    }

    public void seedDB() throws URISyntaxException {
        jobRepository.deleteAll();
        buildRepository.deleteAll();
        try(JenkinsServer jenkinsServer = new JenkinsServer(new URI(uri))){
            List<String> jobNames = allJobNames();
            for(String name: jobNames){
                Job job = jenkinsServer.getJob(name);
                // A few of the jobs don't seem to exist, so skip if needed
                if(null != job)
                {
                    MyJob myJob = new MyJob(job.getName(), job.getUrl());
                    jobRepository.save(myJob);
                    List<Build> builds = job.details().getBuilds();
                    for(Build build: builds){
                        if(build.details().getResult() != null){
                            int number = build.getNumber();
                            int jobid = myJob.getId();
                            Timestamp time = new Timestamp(build.details().getTimestamp());
                            String result = build.details().getResult().toString();
                            buildRepository.save(new MyBuild(number, jobid, time, result));
                        }
                    }
                }
            }
        }catch (Exception e){
            Logger logger = Logger.getLogger("main.java.util");
            logger.severe("Error seeding DB");
        }

    }

    public List<String> allJobNames()
    {
        List<String> jobNames = new ArrayList<>();
        jobNames.add("eric-oss-ran-topology-adapter_Publish");
        jobNames.add("eric-oss-ran-topology-adapter_PreCodeReview");
        jobNames.add("ENM-Adapter_release");
        jobNames.add("ENM-Adapter_PreCodeReview");
        jobNames.add("ENM-Stub_release");
        jobNames.add("ENM-Stub_PreCodeReview");
        jobNames.add("eric-oss-enm-discovery-adapter_Publish");
        jobNames.add("eric-oss-enm-discovery-adapter_PreCodeReview");
        jobNames.add("eric-oss-enm-model-adapter_Publish");
        jobNames.add("eric-oss-enm-model-adapter_PreCodeReview");
        jobNames.add("eric-oss-enm-notification-adapter_Publish");
        jobNames.add("eric-oss-enm-notification-adapter_PreCodeReview");
        return jobNames;
    }
}
