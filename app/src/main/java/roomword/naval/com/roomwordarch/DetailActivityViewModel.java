package roomword.naval.com.roomwordarch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Date;

public class DetailActivityViewModel extends ViewModel
{




    // Weather forecast the user is looking at
    private final LiveData<WeatherEntry> mWeather;

    // Date for the weather forecast
    private final Date mDate;
    private final SunshineRepository mRepository;

    public DetailActivityViewModel(SunshineRepository repository, Date date) {
        mRepository = repository;
        mDate = date;
        mWeather = mRepository.getWeatherByDate(mDate);
    }

    public LiveData<WeatherEntry> getWeather() {
        return mWeather;
    }
}
