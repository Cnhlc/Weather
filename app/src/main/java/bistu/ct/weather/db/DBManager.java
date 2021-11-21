package bistu.ct.weather.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库的增删改查方法
 */
public class DBManager {
    /*获得数据库名*/
    public static SQLiteDatabase database;
    /* 初始化数据库信息*/
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }
    /* 查找数据库当中所有城市列表*/
    public static List<String>queryAllCityName(){
        Cursor  cursor = database.query("city", null, null, null, null, null,null);
        List<String> cityList = new ArrayList<>();
        if(cursor.getColumnCount()>0){
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        }
        return cityList;
    }
    /* 根据城市名称，替换信息内容*/
    public static int updateCity(String city,String content){
        ContentValues values = new ContentValues();
        values.put("content",content);
        return database.update("city",values,"city=?",new String[]{city});
    }
    /* 新增一条城市*/
    public static long addCity(String city,String content){
        ContentValues values = new ContentValues();
        values.put("city",city);
        values.put("content",content);
        return database.insert("city",null,values);
    }
    /* 根据城市名，查询数据库当中的内容*/
    @SuppressLint("Range")
    public static String searchCity(String city){

        Cursor cursor = database.query("city", null, "city=?", new String[]{city}, null, null, null);
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            return content;
        }
        return null;
    }

    /**
     *
     * @return
     */
    public static String searchId(String id){

        Cursor cursor=database.query("city",null,"_id=?",new String[]{id},null,null,null);
        if(cursor.getCount()>0){
            cursor.moveToFirst();
        @SuppressLint("Range") String city=cursor.getString(cursor.getColumnIndex("city"));
            System.out.println(city+"*******************");
            return  city;
        }
        return null;
    }

    /* 存储城市天气要求最多存储5个城市的信息，一旦超过5个城市就不能存储了，获取目前已经存储的数量*/
    public static int getCityCount(){
        Cursor cursor = database.query("city", null, null, null, null, null, null);
        int count = cursor.getCount();
        return count;
    }

    /* 查询数据库当中的全部信息*/
    public static List<DatabaseBean>queryAllInfo(){
        Cursor cursor = database.query("city", null, null, null, null, null, null);
        List<DatabaseBean>list = new ArrayList<>();
        while (cursor.moveToNext()) {
            int a=cursor.getColumnIndex("_id");
            int id = cursor.getInt(a);
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
            DatabaseBean bean = new DatabaseBean(id, city, content);
            list.add(bean);
        }
        return list;
    }

    /* 根据城市名称，删除这个城市在数据库当中的数据*/
    public static int deleteInfoByCity(String city){
        return database.delete("city","city=?",new String[]{city});
    }

    /* 删除表当中所有的数据信息*/
    public static void deleteAllInfo(){
        String sql = "delete from city";
        database.execSQL(sql);
    }
}
