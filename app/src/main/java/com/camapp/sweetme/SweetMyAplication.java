package com.camapp.sweetme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.adjust.sdk.AdjustEvent;
import com.camapp.sweetme.remote.SupporterClass;
import com.camapp.sweetme.utilsApp.TypefaceUtils;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.sdk.perelander.Mob;
import com.sdk.perelander.MobConfig;

public class SweetMyAplication extends MultiDexApplication {
    private static SweetMyAplication mInstance;
    private static SweetMyAplication myApplication;
    public final String TAG = SweetMyAplication.class.getSimpleName();
    public Context sContext = null;
    public MobConfig config;

    public static SweetMyAplication getApplication() {
        return myApplication;
    }

    public static void setApplication(SweetMyAplication application) {
        myApplication = application;
    }

    public static synchronized SweetMyAplication getInstance() {
        synchronized (SweetMyAplication.class) {
            synchronized (SweetMyAplication.class) {
                myApplication = mInstance;
            }
        }
        return myApplication;
    }

    public void onCreate() {
        super.onCreate();

        config = new MobConfig(this, "5liz4nw9e51c","k26dcd");
        Mob.onCreate(config);
        registerActivityLifecycleCallbacks(new MobLifecycleCallbacks());

        mInstance = this;
        sContext = getApplicationContext();
        setApplication(this);
        TypefaceUtils.overrideFont(getApplicationContext(), "serif", "fonts/rubik_regular.ttf");

        mInstance = this;
        FirebaseApp.initializeApp(mInstance);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        checkAppReplacingState();
    }

    private static final class MobLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            Mob.onResume(activity);
        }
        @Override
        public void onActivityPaused(Activity activity) {
            Mob.onPause();
        }
        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        }
        @Override
        public void onActivityStarted(@NonNull Activity activity) {
        }
        @Override
        public void onActivityStopped(@NonNull Activity activity) {
        }
        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        }
        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {
        }
    }

    public void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    private void checkAppReplacingState() {
        Log.d(this.TAG, "app start...");
        if (getResources() == null) {
            Log.d(this.TAG, "app is replacing...kill");
            Process.killProcess(Process.myPid());
        }
    }

    public void showInterstitial(final Activity act, final Intent intent, final boolean isFinished) {

        if (!SupporterClass.checkConnection(sContext)) {
            SupporterClass.setFullScreenIsInView(false);
            if (intent != null)
                act.startActivity(intent);
            if (isFinished) {
                if (act != null && !act.isFinishing())
                    act.finish();
            }
            return;
        }
        SupporterClass.setFullScreenIsInView(false);
        if (intent != null)
            act.startActivity(intent);
        if (isFinished) {
            if (act != null && !act.isFinishing())
                act.finish();
        }
    }

}
