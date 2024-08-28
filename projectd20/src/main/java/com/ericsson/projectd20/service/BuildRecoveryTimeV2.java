package com.ericsson.projectd20.service;

import com.ericsson.projectd20.MyBuild;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BuildRecoveryTimeV2 {

    @Autowired
    private BuildRepository buildRepository;

    /**
     * Method that returns the list of N failed build, followed by N+1 successful build
     * @param builds
     * @return
     */
    public List<MyBuild> getFailedAndSuccessfulBuilds(List<MyBuild> builds) {
        //This will be the filtered results list
        List<String> filteredResults = new ArrayList<>();
        List<MyBuild> filteredBuilds = new ArrayList<>();

        MyBuild currentFailedBuild = new MyBuild();
        boolean failure = false;

        for (int i = builds.size()-1; i > 0; i--) {
            MyBuild build = builds.get(i);
            if (build.getResult().equalsIgnoreCase("FAILURE") || build.getResult().equalsIgnoreCase("ABORTED")) {
                failure = true;
                currentFailedBuild = build;
            } else if (failure && build.getResult().equalsIgnoreCase("SUCCESS")) {
                filteredResults.add(currentFailedBuild.getResult());
                filteredResults.add(build.getResult());
                filteredBuilds.add(currentFailedBuild);
                filteredBuilds.add(build);
                failure = false;
            }
        }
        return filteredBuilds;
    }

    public List<Long> getTimeDifference(List<MyBuild> builds)
    {
        List<Long> allTimeDiff = new ArrayList<>();
        long timeDiff = 0;
        for(int i = 1; i < builds.size(); i+=2)
        {
            timeDiff = builds.get(i).getTimeCreated().getTime() - builds.get(i-1).getTimeCreated().getTime();
            allTimeDiff.add(timeDiff);
        }
        return allTimeDiff;
    }

}
