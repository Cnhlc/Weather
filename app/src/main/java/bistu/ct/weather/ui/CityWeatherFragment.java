package bistu.ct.weather.ui;

import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import bistu.ct.weather.db.DBManager;
import bistu.ct.weather.juhe.URLUtil;
import bistu.ct.weather.juhe.WeatherBean;
import bistu.ct.weather.R;


public class CityWeatherFragment extends BaseFragment {
   TextView temp,city,condition,wind,tempRange,data;
   ImageView day;
   LinearLayout future;
   public  static String icity;
    ScrollView outLayout;
    private SharedPreferences pref;
    private int bgNum;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_city_weather, container, false);
        initView(view);
        /*通过activity传递获取当前fragment加载的是哪个地方的天气情况*/
        Bundle bundle = getArguments();
        icity = bundle.getString("city");
        /*获取温度信息的网址*/
        String url= URLUtil.getTem_url(icity);
        System.out.println(url);
       /*调用父类的获取数据的方法*/
        loadData(url);

        return view;
    }



    /**
     * 重写成功的方法，解析并展示的方法
     */
    @Override
    public void onSuccess(String result){
       parseShowData(result);
       /*更新数据*/
        int i= DBManager.updateCity(icity,result);
        if(i<=0){
            /*更新失败,说明没有该城市信息，增加该城市信息*/
            DBManager.addCity(icity,result);
        }
    }

    /**
     * 失败的方法
     * @param ex
     * @param isOnCallback
     */
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        /*数据库中查找上一次显示在Fragment中的内容*/
        String s=DBManager.searchCity(icity);
        if (!TextUtils.isEmpty(s)){
            parseShowData(s);
        }
    }

    /**
     * 解析并展示数据
     * @param result
     */
    private void parseShowData(String result) {
        /*使用gson解析数据*/
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        WeatherBean.ResultBean weatherBeanResult = weatherBean.getResult();
        /*获取信息时间和城市*/
        data.setText(weatherBeanResult.getFuture().get(0).getDate());
        city.setText(weatherBeanResult.getCity());
        /*获取今天的天气情况*/
        WeatherBean.ResultBean.FutureBean Todayfuture = weatherBeanResult.getFuture().get(0);
        WeatherBean.ResultBean.RealtimeBean realtime = weatherBeanResult.getRealtime();
        wind.setText(realtime.getDirect()+realtime.getPower());
        tempRange.setText(Todayfuture.getTemperature());
        condition.setText(realtime.getInfo());
        switch (realtime.getInfo()){
            case "晴":
                day.setImageResource(R.mipmap.sun);
                break;
            case "多云":
                day.setImageResource(R.mipmap.clody);
                break;
            case "小雪":
            case "中雪":
            case "大雪":
                day.setImageResource(R.mipmap.snow);
                break;
            case "阴":
                  day.setImageResource(R.mipmap.yintian);
                 break;
            case "雨":
            case "小雨":
            case "中雨":
            case "大雨":
            case "雷阵雨":
                day.setImageResource(R.mipmap.rain);
                break;
        }
        /*获取实时温度*/
        temp.setText(realtime.getTemperature()+"℃");
        /*获取未来三天的天气情况，并加载到Layout中*/
        List<WeatherBean.ResultBean.FutureBean> futureList = weatherBeanResult.getFuture();
        futureList.remove(0);
        for(int i=0;i<futureList.size();i++){
            View itemView=LayoutInflater.from(getActivity()).inflate(R.layout.item_main_center,null);
            /*注意在看一下视频*/
            itemView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            future.addView(itemView);
            TextView data=itemView.findViewById(R.id.item_center_tv_date);
            TextView conditon=itemView.findViewById(R.id.item_center_tv_condition);
            TextView wind=itemView.findViewById(R.id.item_center_tv_wind);
            TextView temprange=itemView.findViewById(R.id.item_center_tv_temp);
            /*获取对应位置的天气情况*/
            WeatherBean.ResultBean.FutureBean databean = futureList.get(i);
            data.setText(databean.getDate());
            conditon.setText(databean.getWeather());
            wind.setText(databean.getDirect());
            temprange.setText(databean.getTemperature());

        }
    }
    /**
     * 初始化控件操作
     * @param view
     */
    private void initView(View view) {
        temp = view.findViewById(R.id.frag_tv_currentTemp);
        city = view.findViewById(R.id.frag_tv_city);
        condition = view.findViewById(R.id.frag_tv_condition);
        wind= view.findViewById(R.id.frag_tv_wind);
        tempRange = view.findViewById(R.id.frag_tv_tempranger);
        data= view.findViewById(R.id.frag_tv_data);
        day = view.findViewById(R.id.frag_iv_today);
        future = view.findViewById(R.id.frag_center_layout);


    }

    }
