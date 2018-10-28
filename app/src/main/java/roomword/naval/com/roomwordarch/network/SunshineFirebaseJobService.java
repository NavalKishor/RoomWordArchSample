package roomword.naval.com.roomwordarch.network;

import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import roomword.naval.com.roomwordarch.utils.InjectorUtils;

public class SunshineFirebaseJobService extends JobService
{
    private static final String LOG_TAG = SunshineFirebaseJobService.class.getSimpleName();

    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     * <p>
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters)
    { Log.d(LOG_TAG, "Job service started");

        WeatherNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchWeather();

        jobFinished(jobParameters, false);

        return true;
    }

    /**
     * Called when the scheduling engine has decided to interrupt the execution of a running job,
     * most likely because the runtime constraints associated with the job are no longer satisfied.
     *
     * @return whether the job should be retried
     *
     */
    @Override
    public boolean onStopJob(JobParameters job)
    {
        return true;
    }
}
