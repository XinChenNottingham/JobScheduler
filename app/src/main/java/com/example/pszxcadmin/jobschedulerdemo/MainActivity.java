package com.example.pszxcadmin.jobschedulerdemo;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Messenger;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import static android.app.job.JobInfo.getMinPeriodMillis;

public class MainActivity extends AppCompatActivity {

    private int jobID=0;
    private long workDuration = 5000;
    public static final String WORK_DURATION_KEY =
            BuildConfig.APPLICATION_ID + ".WORK_DURATION_KEY";
    static final long minInterval=getMinPeriodMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void jobScheduleButton(View v){

        Log.d("MDP", "minimumInterval:"+minInterval);
        ComponentName mServiceComponent = new ComponentName(this, MyJobService.class);
        JobInfo.Builder builder = new JobInfo.Builder(jobID, mServiceComponent);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED);
        builder.setPersisted(true);
        builder.setPeriodic(minInterval);
        //builder.setMinimumLatency(2000);
        //builder.setOverrideDeadline(5000);

        // add an extra condition-workDuration
        PersistableBundle extras = new PersistableBundle();
        extras.putLong(WORK_DURATION_KEY, workDuration);
        builder.setExtras(extras);

        JobScheduler jobScheduler= (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(builder.build());
        Log.d("MDP","Job scheduled");
    }

    public void stopServiceButton(View v){
        JobScheduler jobScheduler= (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.cancel(jobID);
        Log.d("MDP","Job cancelled");
    }

}
