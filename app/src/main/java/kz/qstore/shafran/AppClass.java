package kz.qstore.shafran;

import android.app.Application;

        import com.squareup.picasso.OkHttpDownloader;
        import com.squareup.picasso.Picasso;
import com.vk.sdk.VKSdk;

public class AppClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        VKSdk.initialize(getApplicationContext());

        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        //built.setIndicatorsEnabled(true);
        //built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }
}