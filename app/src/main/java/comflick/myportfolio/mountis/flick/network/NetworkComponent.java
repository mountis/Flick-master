package comflick.myportfolio.mountis.flick.network;

import javax.inject.Singleton;

import comflick.myportfolio.mountis.flick.activities.DetailActivity;
import comflick.myportfolio.mountis.flick.activities.MainActivity;
import comflick.myportfolio.mountis.flick.fragment.DetailFragment;
import comflick.myportfolio.mountis.flick.fragment.GridFragment;
import comflick.myportfolio.mountis.flick.fragment.SortingFragment;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface NetworkComponent {

    void inject(GridFragment gridFragment);

    void inject(MainActivity mainActivity);

    void inject(SortingFragment sortingFragment);

    void inject(DetailActivity detailActivity);

    void inject(DetailFragment detailFragment);

}
