package comflick.myportfolio.mountis.flick;

import android.app.Application;

import comflick.myportfolio.mountis.flick.network.AppModule;
import comflick.myportfolio.mountis.flick.network.DaggerNetworkComponent;
import comflick.myportfolio.mountis.flick.network.NetworkComponent;
import comflick.myportfolio.mountis.flick.network.NetworkModule;

public class App extends Application {

    private NetworkComponent networkComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        networkComponent = DaggerNetworkComponent.builder()
                .appModule( new AppModule( this ) )
                .networkModule( new NetworkModule() )
                .build();
    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

}
