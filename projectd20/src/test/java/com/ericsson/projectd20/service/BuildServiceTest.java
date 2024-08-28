package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyBuild;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BuildServiceTest {
    @Autowired
    private BuildService buildService;

    @MockBean
    private BuildRepository buildRepository;

    @Test
    void should_save_one_build(){
        MyBuild build = new MyBuild();
        when(buildRepository.save(build)).thenReturn(build);
        MyBuild newbuild = buildService.create(build);
        assertEquals(build.getId(), newbuild.getId());
        verify(buildRepository).save(build);
    }

    @Test
     void should_get_build_with_id(){
        MyBuild build = new MyBuild();
        build.setId(100);
        when(buildRepository.findById(anyInt())).thenReturn(Optional.of(build));
        MyBuild new_build = buildService.findById(100);
        assertEquals(build, new_build);
        verify(buildRepository).findById(100);
    }

    @Test
    void should_get_all_builds(){
        List<MyBuild> buildList = new ArrayList<>();
        buildList.add(new MyBuild());
        buildList.add(new MyBuild());
        when(buildRepository.findAll()).thenReturn(buildList);
        List<MyBuild> expected = buildService.findAll();
        assertEquals(2, expected.size());
        assertEquals(buildList, expected);
        verify(buildRepository, times(1)).findAll();
    }

    @Test
    void should_get_all_builds_within_timestamp(){
        List<MyBuild> buildList = new ArrayList<>();
        Timestamp t1 = Timestamp.valueOf("2022-10-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-10-15 09:08:17.082000");
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        when(buildRepository.findBuildsWithinTimeStamp(t1, t2)).thenReturn(buildList);
        List<MyBuild> expected = buildService.findBuildsWithinTimeStamp(t1, t2);
        assertEquals(expected.size(), 2);
        assertEquals(buildList, expected);
        verify(buildRepository, times(1)).findBuildsWithinTimeStamp(t1, t2);
    }

    @Test
    void should_get_all_builds_for_a_particular_job(){
        List<MyBuild> buildList = new ArrayList<>();
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        when(buildRepository.findAllByjobID(anyInt())).thenReturn(buildList);
        List<MyBuild> expected = buildService.findAllByJobId(anyInt());
        assertEquals(expected.size(), 2);
        assertEquals(buildList, expected);
        verify(buildRepository, times(1)).findAllByjobID(anyInt());
    }

    @Test
    void should_get_all_builds_for_a_job_within_timestamp(){
        List<MyBuild> buildList = new ArrayList<>();
        Timestamp t1 = Timestamp.valueOf("2022-10-13 09:08:17.082000");
        Timestamp t2 = Timestamp.valueOf("2022-10-15 09:08:17.082000");
        buildList.add(new MyBuild(133, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        buildList.add(new MyBuild(134, 304, Timestamp.valueOf("2022-10-14 09:08:17.082000"), "SUCCESS"));
        when(buildRepository.findBuildOfAParticularJobWithinTimeStamp(t1, t2, 304)).thenReturn(buildList);
        List<MyBuild> expected = buildService.findBuildOfAParticularJobWithinTimeStamp(t1, t2, 304);
        assertEquals(expected.size(), 2);
        assertEquals(buildList, expected);
        verify(buildRepository, times(1)).findBuildOfAParticularJobWithinTimeStamp(t1, t2, 304);
    }

    @Test
     void should_delete_build(){
        MyBuild build = new MyBuild();
        buildService.create(build);
        buildService.delete(build);
        verify(buildRepository, times(1)).delete(build);
    }

    @Test
     void should_delete_all_builds(){
        // TODO: add
        buildService.create(new MyBuild());
        buildService.deleteAll();
        verify(buildRepository, times(1)).deleteAll();
    }

    @Test
     void should_get_exception_for_illegal_id(){
        // TODO: add test for EntityNotFoundException
        EntityNotFoundException thrown = Assertions.assertThrows(EntityNotFoundException.class, () -> {
            buildService.findById(20);
        });
        Assertions.assertEquals("Build not found with id:20", thrown.getMessage());
    }
}