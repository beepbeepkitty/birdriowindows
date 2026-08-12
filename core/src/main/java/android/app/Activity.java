package android.app;

import android.content.SharedPreferences;

public class Activity {
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return new SharedPreferences(name);
    }
}
