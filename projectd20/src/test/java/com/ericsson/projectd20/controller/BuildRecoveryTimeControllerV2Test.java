package com.ericsson.projectd20.controller;

import com.ericsson.projectd20.MyBuild;
import com.ericsson.projectd20.dto.RecoveryTime;
import com.ericsson.projectd20.dto.TimePeriodRequest;
import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildRecoveryTimeControllerV2Test {

    @LocalServerPort
    Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void test_get_builds_status_for_all_jobs() throws JSONException {
        //TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-13 09:08:17.082000"), Timestamp.valueOf("2022-10-15 09:08:17.082000"));
        ResponseEntity<List> responseEntity = testRestTemplate.exchange("/build-recovery-time-v2/jobs/builds-status", HttpMethod.GET, null, List.class);
        List responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.size() > 0);
    }

    @Test
    void test_get_builds_status_for_all_jobs_within_time_period() throws JSONException {
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-13 09:08:17.082000"), Timestamp.valueOf("2022-10-15 09:08:17.082000"));
        ResponseEntity<List> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/build-recovery-time-v2/jobs/builds-status/time-period", timePeriodRequest, List.class);
        List responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.size() > 0);
    }

    @Test
    void test_get_builds_status_for_one_jobs() throws JSONException {
        int id = 127;
        ResponseEntity<List> responseEntity = testRestTemplate.exchange("/build-recovery-time-v2/job/"+id+"/builds-status", HttpMethod.GET, null, List.class);
        List responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.size() > 0);
    }

    @Test
    void test_get_builds_status_for_one_job_within_time_period() throws JSONException {
        int id = 127;
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-07 11:48:32.487000"), Timestamp.valueOf("2022-10-25 10:00:21.888000"));
        ResponseEntity<List> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/build-recovery-time-v2/job/"+id+"/builds-status/time-period", timePeriodRequest, List.class);
        List responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.size() > 0);
    }

    @Test
    void test_get_recovery_time_for_all_jobs() throws JSONException {
        //TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-13 09:08:17.082000"), Timestamp.valueOf("2022-10-15 09:08:17.082000"));
        ResponseEntity<RecoveryTime> responseEntity = testRestTemplate.exchange("/build-recovery-time-v2/jobs", HttpMethod.GET, null, RecoveryTime.class);
        RecoveryTime responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.getMedianInMinutes() >= 0);
    }

    @Test
    void test_get_recovery_time_for_all_jobs_within_time_period() throws JSONException {
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-07 11:48:32.487000"), Timestamp.valueOf("2022-10-25 10:00:21.888000"));
        ResponseEntity<RecoveryTime> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/build-recovery-time-v2/jobs/time-period", timePeriodRequest, RecoveryTime.class);
        RecoveryTime responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.getMedianInMinutes() >= 0);
    }

    @Test
    void test_get_recovery_time_for_one_jobs() throws JSONException {
        //TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-13 09:08:17.082000"), Timestamp.valueOf("2022-10-15 09:08:17.082000"));
        int id = 127;
        ResponseEntity<RecoveryTime> responseEntity = testRestTemplate.exchange("/build-recovery-time-v2/job/"+id, HttpMethod.GET, null, RecoveryTime.class);
        RecoveryTime responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.getMedianInMinutes() >= 0);
    }

    @Test
    void test_get_recovery_time_for_one_jobs_within_time_period() throws JSONException {
        int id = 127;
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-07 11:48:32.487000"), Timestamp.valueOf("2022-10-25 10:00:21.888000"));
        ResponseEntity<RecoveryTime> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/build-recovery-time-v2/job/"+id+"/time-period", timePeriodRequest, RecoveryTime.class);
        RecoveryTime responseBody = responseEntity.getBody();
        //System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.getMedianInMinutes() >= 0);
    }

}
