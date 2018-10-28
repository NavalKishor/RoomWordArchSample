package roomword.naval.com.roomwordarch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import java.util.Date;

public class DetailViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private final SunshineRepository mRepository;
    private final Date mDate;

    public DetailViewModelFactory(SunshineRepository repository, Date date) {
        this.mRepository = repository;
        this.mDate = date;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailActivityViewModel(mRepository, mDate);
    }
}
