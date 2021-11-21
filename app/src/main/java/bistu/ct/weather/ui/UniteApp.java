package bistu.ct.weather.ui;

import android.app.Application;
import org.xutils.x;

import bistu.ct.weather.db.DBManager;

public class UniteApp extends Application {
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        DBManager.initDB(this);
    }
}
