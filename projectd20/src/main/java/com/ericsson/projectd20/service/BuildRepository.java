package com.ericsson.projectd20.service;
import com.ericsson.projectd20.MyBuild;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface BuildRepository extends CrudRepository<MyBuild, Integer> {
    @Query("select b from MyBuild b where b.timeCreated >= ?1 and b.timeCreated <= ?2")
    List<MyBuild> findBuildsWithinTimeStamp(Timestamp from, Timestamp to);

    @Query("select b from MyBuild b where b.timeCreated >= ?1 and b.timeCreated <= ?2 and b.jobID = ?3")
    List<MyBuild> findBuildOfAParticularJobWithinTimeStamp(Timestamp from, Timestamp to, int jobID);

    List<MyBuild> findAllByjobID(int jobID);
}

