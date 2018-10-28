package roomword.naval.com.roomwordarch;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

public class SunShineActivityViewModel extends ViewModel
{
    private final SunshineRepository mRepository;
    private final LiveData<List<ListWeatherEntry>> mForecast;

    public SunShineActivityViewModel(SunshineRepository mRepository) {
        this.mRepository = mRepository;
        mForecast = mRepository.getCurrentWeatherForecasts();
    }

    public LiveData<List<ListWeatherEntry>> getForecast() {
        return mForecast;
    }
}
