package com.termal.ayojaki;

import android.app.Application;
import android.content.Context;

/**
 * Created by akbar on 05/09/17.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static Context getContext(){
        return context;
    }
}
