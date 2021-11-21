package bistu.ct.weather.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 数据库实例化
 */
public class DBHelper extends SQLiteOpenHelper{
   public static  final String TABLE_NAME="city";
    public DBHelper(Context context){
        super(context,"forecast.db",null,1);
    }

    /**
     * 创建表
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table city(_id integer primary key autoincrement," +
                "city varchar(20) unique not null," +
                "content text not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
