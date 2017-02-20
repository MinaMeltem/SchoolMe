package nyc.c4q.ashiquechowdhury.schoolme.splashScreen;

import android.content.Intent;

import com.daimajia.androidanimations.library.Techniques;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import nyc.c4q.ashiquechowdhury.schoolme.MainActivity;
import nyc.c4q.ashiquechowdhury.schoolme.R;

/**
 * Created by meltemyildirim on 2/19/17.
 */

public class SplashScreen extends AwesomeSplash {
    @Override
    public void initSplash(ConfigSplash configSplash) {

        //Customize Circular Reveal
        configSplash.setBackgroundColor(R.color.darkGreen);
        configSplash.setAnimCircularRevealDuration(2000);
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM);

        //Customize Logo
        configSplash.setLogoSplash(R.drawable.ic_school_black_24dp);
        configSplash.setAnimLogoSplashDuration(1000);
        configSplash.setAnimLogoSplashTechnique(Techniques.FadeIn);
        configSplash.setTitleFont("fonts/Montserrat-Regular.ttf");


        //Customize Title
        configSplash.setTitleSplash("SchoolMe");
        configSplash.setTitleTextColor(R.color.white);
        configSplash.setTitleTextSize(45f);
        configSplash.setAnimTitleDuration(1500);
        configSplash.setAnimTitleTechnique(Techniques.FadeIn);

    }

    @Override
    public void animationsFinished() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
