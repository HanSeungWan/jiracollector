package com.daou.jiracollector.jiradatamaker.data;

import com.daou.jiracollector.jiradatamaker.scheduler.JobMakeIssueMeasureStatus;
import com.daou.jiracollector.jiradatamaker.scheduler.JobMakeIssueTypeTransition;
import com.daou.jiracollector.jiradatamaker.scheduler.JobMakeIssuetype;
import com.daou.jiracollector.jiradatamaker.scheduler.JobMakeVersions;

public class TempCollector {

    public static void main(String[] args) throws Exception {

        JobMakeVersions jobMakeVersions = new JobMakeVersions();
        jobMakeVersions.makedayVersions();

        JobMakeIssuetype jobMakeIssuetype = new JobMakeIssuetype();
        jobMakeIssuetype.makedayIssuetype();

        JobMakeIssueTypeTransition jobMakeIssueTypeTransition = new JobMakeIssueTypeTransition();
        jobMakeIssueTypeTransition.makedayIssuetypeTransition();

        JobMakeIssueMeasureStatus jobMakeIssueMeasureStatus = new JobMakeIssueMeasureStatus();
        jobMakeIssueMeasureStatus.makedayIssueMeasureStatus();

    }
}
