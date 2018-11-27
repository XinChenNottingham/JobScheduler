package com.example.pszxcadmin.jobschedulerdemo;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.util.Log;

import static com.example.pszxcadmin.jobschedulerdemo.MainActivity.WORK_DURATION_KEY;

/**
 * Created by pszxcadmin on 26/11/2017.
 */

public class MyJobService extends JobService {

    boolean jobCancelled;
    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("MDP", "Job service created");
    }

    public boolean onStartJob(final JobParameters params) {
        // The work that this service "does" is simply wait for a certain duration and finish
        // the job (on another thread).
        backgroundWork(params);
        return true;
    }
    private void backgroundWork(final JobParameters params) {
        long duration = params.getExtras().getLong(WORK_DURATION_KEY);
        Log.d("MDP", "Run the job for " + duration + " milliseconds");
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    Log.d("MDP", "run" + i);
                    if (jobCancelled) {
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Log.d("MDP", "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }

    public boolean onStopJob(JobParameters params) {
        // Stop tracking these job parameters, as we've 'finished' executing.
        Log.d("MDP", "job cancelled before completion: " + params.getJobId());
        // Return false to drop the job.
        jobCancelled=true;
        return false;
    }
}
