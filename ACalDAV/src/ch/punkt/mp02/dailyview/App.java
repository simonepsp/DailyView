package ch.punkt.mp02.dailyview;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context mContext;

    public App() {
        super();
        App.mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
