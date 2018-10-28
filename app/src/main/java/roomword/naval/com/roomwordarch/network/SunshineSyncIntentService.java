package roomword.naval.com.roomwordarch.network;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import roomword.naval.com.roomwordarch.utils.InjectorUtils;

public class SunshineSyncIntentService extends IntentService
{
    private static final String LOG_TAG = SunshineSyncIntentService.class.getSimpleName();

    public SunshineSyncIntentService()
    {
        super("SunshineSyncIntentService");
    }

    @Override
    protected void onHandleIntent(@NonNull Intent intent)
    {

        Log.d(LOG_TAG, "Intent service started");
        WeatherNetworkDataSource networkDataSource =
                InjectorUtils.provideNetworkDataSource(this.getApplicationContext());
        networkDataSource.fetchWeather();
    }
}
