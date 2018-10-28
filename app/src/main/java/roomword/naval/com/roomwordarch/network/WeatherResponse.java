package roomword.naval.com.roomwordarch.network;

import android.support.annotation.NonNull;

import roomword.naval.com.roomwordarch.WeatherEntry;

public class WeatherResponse
{
    @NonNull
    private final WeatherEntry[] mWeatherForecast;

    public WeatherResponse(@NonNull final WeatherEntry[] weatherForecast) {
        mWeatherForecast = weatherForecast;
    }

    public WeatherEntry[] getWeatherForecast() {
        return mWeatherForecast;
    }
}
