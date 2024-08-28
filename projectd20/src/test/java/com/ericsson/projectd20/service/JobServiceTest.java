package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyBuild;
import com.ericsson.projectd20.MyJob;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class JobServiceTest {
    @MockBean
    private JobRepository jobRepository;

    @Autowired
    private BuildService buildService;

    @MockBean
    private BuildRepository buildRepository;

    @Autowired
    private JobService jobService;

    @Test
    void test_save_one_job(){
        MyJob job = new MyJob();
        when(jobRepository.save(job)).thenReturn(job);
        MyJob newJob = jobService.create(job);
        Assertions.assertEquals(job, newJob);
        verify(jobRepository, times(1)).save(job);
    }

    @Test
    void test_get_job_with_id(){
        MyJob job = new MyJob();
        job.setId(100);
        when(jobRepository.findById(anyInt())).thenReturn(Optional.of(job));
        MyJob newJob = jobService.findById(100);
        Assertions.assertEquals(job, newJob);
        verify(jobRepository, times(1)).findById(100);
    }

    @Test
    void test_get_all_jobs(){
        List<MyJob> jobs = new ArrayList<>();
        jobs.add(new MyJob());
        jobs.add(new MyJob());
        when(jobRepository.findAll()).thenReturn(jobs);
        List<MyJob> myJobs = jobService.findAll();
        Assertions.assertEquals(jobs, myJobs);
        verify(jobRepository, times(1)).findAll();
    }

    @Test
    void test_delete_job(){
        MyJob jobs = new MyJob();
        jobService.create(jobs);
        jobService.delete(jobs);
        verify(jobRepository, times(1)).delete(jobs);
    }

    @Test
    void test_failure_rate(){
        List<MyBuild> buildList = new ArrayList<>();
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(135, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        buildList.add(new MyBuild(136, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        double actual = jobService.calculateFailureRate(buildList);
        double expected = 50;
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void test_failure_rate_with_particular_job_id(){
        List<MyBuild> buildList = new ArrayList<>();
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(135, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(136, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        buildList.add(new MyBuild(137, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        when(buildRepository.findAllByjobID(anyInt())).thenReturn(buildList);
        double actual = jobService.buildFailureRate(134);
        double expected = 50;
        Assertions.assertEquals(actual, expected);
        verify(buildRepository, times(1)).findAllByjobID(134);
    }

    @Test
    void test_failure_rate_for_all_builds_in_a_given_time(){
        List<MyBuild> buildList = new ArrayList<>();
        Timestamp t1 = Timestamp.valueOf("2022-10-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-10-15 09:08:17.082000");
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(135, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        buildList.add(new MyBuild(136, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        when(buildRepository.findBuildsWithinTimeStamp(t1, t2)).thenReturn(buildList);
        double actual = jobService.buildFailureRate(t1, t2);
        double expected = 50;
        Assertions.assertEquals(actual, expected);
        verify(buildRepository, times(1)).findBuildsWithinTimeStamp(t1, t2);
    }

    @Test
    void test_failure_rate_for_all_builds_from_the_same_job_in_a_given_time(){
        List<MyBuild> buildList = new ArrayList<>();
        Timestamp t1 = Timestamp.valueOf("2022-10-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-10-15 09:08:17.082000");
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(135, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        buildList.add(new MyBuild(136, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        when(buildRepository.findBuildOfAParticularJobWithinTimeStamp(t1, t2, 134)).thenReturn(buildList);
        double actual = jobService.buildFailureRate(t1, t2, 134);
        double expected = 50;
        Assertions.assertEquals(actual, expected);
        verify(buildRepository, times(1)).findBuildOfAParticularJobWithinTimeStamp(t1, t2, 134);
    }

    @Test
    void test_failure_rate_for_all_builds(){
        List<MyBuild> buildList = new ArrayList<>();
        Timestamp t1 = Timestamp.valueOf("2022-10-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-10-15 09:08:17.082000");
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(135, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        buildList.add(new MyBuild(136, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "FAILURE"));
        buildList.add(new MyBuild(137, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "ABORTED"));
        buildList.add(new MyBuild(138, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        when(buildRepository.findAll()).thenReturn(buildList);
        double actual = jobService.buildFailureRate();
        double expected = 50;
        Assertions.assertEquals(actual, expected);
        verify(buildRepository, times(1)).findAll();
    }

    @Test
    void should_get_exception_for_illegal_id(){
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            jobService.findById(20);
        });
        Assertions.assertEquals("Job not found with id:20", thrown.getMessage());
    }

    @Test
    void test_seed_db_works(){
        // TODO : test seed db function
    }
}
