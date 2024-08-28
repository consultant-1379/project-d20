package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyBuild;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.List;

// 360
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BuildRepositoryTest {
    @Autowired
    private TestEntityManager em;

    @Autowired
    private BuildRepository repository;

    @Test
    void test_builds_within_timestamp() {
        Timestamp t1 = Timestamp.valueOf("2022-12-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-12-15 09:08:17.082000");
        em.persist(new MyBuild(133, 304, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(135, 304, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(136, 304, Timestamp.valueOf("2022-12-16 09:08:17.082000"), "SUCCESS"));
        List<MyBuild> buildList = repository.findBuildsWithinTimeStamp(t1, t2);
        Assertions.assertEquals(2, buildList.size());
    }

    @Test
    void test_find_build_particular_job_within_time_stamp() {
        Timestamp t1 = Timestamp.valueOf("2022-12-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-12-15 09:08:17.082000");
        em.persist(new MyBuild(133, 304, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(135, 305, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(136, 305, Timestamp.valueOf("2022-12-16 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(133, 306, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(135, 303, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(136, 301, Timestamp.valueOf("2022-12-16 09:08:17.082000"), "SUCCESS"));
        List<MyBuild> buildList = repository.findBuildOfAParticularJobWithinTimeStamp(t1, t2, 305);
        Assertions.assertEquals(1, buildList.size());
    }

    @Test
    void test_find_all_jobs_by_id() {
        Timestamp t1 = Timestamp.valueOf("2022-12-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-12-15 09:08:17.082000");
        em.persist(new MyBuild(133, 304, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(135, 305, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(136, 305, Timestamp.valueOf("2022-12-16 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(133, 306, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(135, 303, Timestamp.valueOf("2022-12-14 09:08:17.082000"), "SUCCESS"));
        em.persist(new MyBuild(136, 301, Timestamp.valueOf("2022-12-16 09:08:17.082000"), "SUCCESS"));
        List<MyBuild> buildList = repository.findAllByjobID(305);
        Assertions.assertEquals(2, buildList.size());
    }
}
