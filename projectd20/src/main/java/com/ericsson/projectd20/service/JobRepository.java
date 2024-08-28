package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyJob;
import org.springframework.data.repository.CrudRepository;

public interface JobRepository extends CrudRepository<MyJob, Integer> {
}