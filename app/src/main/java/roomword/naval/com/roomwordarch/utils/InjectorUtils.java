package roomword.naval.com.roomwordarch.utils;

import android.content.Context;

import java.util.Date;

import roomword.naval.com.roomwordarch.AppExecutors;
import roomword.naval.com.roomwordarch.DetailViewModelFactory;
import roomword.naval.com.roomwordarch.SunShineViewModelFactory;
import roomword.naval.com.roomwordarch.SunshineRepository;
import roomword.naval.com.roomwordarch.WordRoomDatabase;
import roomword.naval.com.roomwordarch.network.WeatherNetworkDataSource;

public class InjectorUtils
{
    public static SunshineRepository provideRepository(Context context) {
        WordRoomDatabase database = WordRoomDatabase.getDatabase(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        WeatherNetworkDataSource networkDataSource =
                WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
        return SunshineRepository.getInstance(database.weatherDao(), networkDataSource, executors);
    }

    public static WeatherNetworkDataSource provideNetworkDataSource(Context context) {
        // This call to provide repository is necessary if the app starts from a service - in this
        // case the repository will not exist unless it is specifically created.
        provideRepository(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return WeatherNetworkDataSource.getInstance(context.getApplicationContext(), executors);
    }

    public static DetailViewModelFactory provideDetailViewModelFactory(Context context, Date date) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        return new DetailViewModelFactory(repository, date);
    }

    public static SunShineViewModelFactory provideSunShineActivityViewModelFactory(Context context) {
        SunshineRepository repository = provideRepository(context.getApplicationContext());
        return new SunShineViewModelFactory(repository);
    }
}
