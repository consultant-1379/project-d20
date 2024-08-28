package com.ericsson.projectd20.controller;

import com.ericsson.projectd20.MyBuild;
import com.ericsson.projectd20.utilities.Utilities;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.Assert.assertEquals;
import com.ericsson.projectd20.dto.TimePeriodRequest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import java.sql.Timestamp;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BuildFailureControllerTest {

    @LocalServerPort
    Integer port;
    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void validateDoubleNANTest() {
        double mockPercent = Double.NaN;
        Assertions.assertEquals(-1.0, Utilities.validateDouble(mockPercent));
    }

    @Test
    void validateDoubleTest() {
        double mockPercent = 42.35;
        Assertions.assertEquals(42.35, Utilities.validateDouble(mockPercent));
    }

    @Test
    void test_get_build_failure_rate_for_all_jobs(){
        ResponseEntity<Double> responseEntity = testRestTemplate.exchange("/build-failure-rate/jobs", HttpMethod.GET, null, new ParameterizedTypeReference<Double>() {});
        double responseBody = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(53, Math.ceil(responseBody));
    }

    @Test
    void test_get_build_failure_rate_for_a_given_time() throws JSONException {
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-10-13 09:08:17.082000"), Timestamp.valueOf("2022-10-15 09:08:17.082000"));
        ResponseEntity<Double> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/build-failure-rate/jobs/time-period", timePeriodRequest, Double.class);
        double responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(53, Math.ceil(responseBody));
    }

    @Test
    void test_get_build_failure_for_all_builds_in_a_job(){
        int id = 127;
        ResponseEntity<Double> responseEntity = testRestTemplate.getForEntity("http://localhost:" + port + "/build-failure-rate/job/" + id, Double.class);
        double responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(57, Math.ceil(responseBody));
    }

    @Test
    void test_get_build_failure_rate_for_all_builds_in_a_job_within_a_time_period(){
        int id = 127;
        TimePeriodRequest timePeriodRequest = new TimePeriodRequest(Timestamp.valueOf("2022-09-13 09:08:17.082000"), Timestamp.valueOf("2022-09-16 09:08:17.082000"));
        ResponseEntity<Double> responseEntity = testRestTemplate.postForEntity("http://localhost:" + port + "/build-failure-rate/job/" + id + "/time-period", timePeriodRequest, Double.class);
        double responseBody = responseEntity.getBody();
        System.out.println(responseBody);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(80, Math.ceil(responseBody));
    }
}
