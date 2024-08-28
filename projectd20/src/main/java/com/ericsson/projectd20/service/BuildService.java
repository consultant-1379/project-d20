package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyBuild;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.*;

@Service
public class BuildService {

    private BuildRepository buildRepository;

    public BuildService(BuildRepository buildRepository){
        this.buildRepository = buildRepository;
    }

    // Create new builds in the my_builds db
    public MyBuild create(MyBuild build){
        return buildRepository.save(build);
    }

    // Find the build with the ID
    public MyBuild findById(int id){
        return buildRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Build not found with id:" + id));
    }

    // Returns all the builds in the db
    public List<MyBuild> findAll(){
        List<MyBuild> buildList = new ArrayList<>();
        Iterator<MyBuild> iterator = buildRepository.findAll().iterator();
        iterator.forEachRemaining(buildList::add);
        return buildList;
    }

    // delete a particular build from the db
    public void delete(MyBuild build){
        buildRepository.delete(build);
    }

    // find all builds that were created within the timestamp
    public List<MyBuild> findBuildsWithinTimeStamp(Timestamp from, Timestamp to){
        return buildRepository.findBuildsWithinTimeStamp(from, to);
    }

    // finds all builds that were created a particular job within a timestamp
    public List<MyBuild> findBuildOfAParticularJobWithinTimeStamp(Timestamp from, Timestamp to, int jobID){
        return buildRepository.findBuildOfAParticularJobWithinTimeStamp(from, to, jobID);
    }

    // finds all the builds for a particular job
    public List<MyBuild> findAllByJobId(int jobID){
        return buildRepository.findAllByjobID(jobID);
    }

    // deletes all the elements in the db
    public void deleteAll(){
        buildRepository.deleteAll();
    }
}