package comflick.myportfolio.mountis.flick;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import comflick.myportfolio.mountis.flick.activities.MainActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent( this, MainActivity.class );
        startActivity( intent );
        finish();
    }
}
