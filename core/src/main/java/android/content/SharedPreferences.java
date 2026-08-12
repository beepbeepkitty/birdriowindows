package android.content;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class SharedPreferences {
    private Preferences prefs;

    public SharedPreferences(String name) {
        prefs = Gdx.app.getPreferences(name);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return prefs.getBoolean(key, defValue);
    }

    public int getInt(String key, int defValue) {
        return prefs.getInteger(key, defValue);
    }

    public String getString(String key, String defValue) {
        return prefs.getString(key, defValue);
    }

    public Editor edit() {
        return new Editor(prefs);
    }

    public static class Editor {
        private Preferences prefs;
        public Editor(Preferences prefs) { this.prefs = prefs; }
        public void putBoolean(String key, boolean value) { prefs.putBoolean(key, value); }
        public void putInt(String key, int value) { prefs.putInteger(key, value); }
        public void putString(String key, String value) { prefs.putString(key, value); }
        public void commit() { prefs.flush(); }
        public void apply() { prefs.flush(); }
    }
}
