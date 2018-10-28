package roomword.naval.com.roomwordarch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

public class SunShineViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    private final SunshineRepository mRepository;

    public SunShineViewModelFactory(SunshineRepository mRepository) {this.mRepository = mRepository;}

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new SunShineActivityViewModel(mRepository);
    }
}
