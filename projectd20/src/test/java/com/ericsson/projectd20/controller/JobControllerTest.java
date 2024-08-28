package com.ericsson.projectd20.controller;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class JobControllerTest {

    @LocalServerPort
    Integer port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    void test_get_builds_status_for_all_jobs() throws JSONException {
        ResponseEntity<List> responseEntity = testRestTemplate.exchange("/jobs", HttpMethod.GET, null, List.class);
        List responseBody = responseEntity.getBody();
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseBody.size() > 0);
    }

}
