package bistu.ct.weather.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import bistu.ct.weather.CityMangerPage.CityManager;
import bistu.ct.weather.db.DBManager;
import bistu.ct.weather.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView addCity,more;
    LinearLayout pointLayout;
    RelativeLayout relativeLayout;
    ViewPager main;
    /*ViewPager的数据源*/
    List<Fragment> fragmentList;
    /*需要显示的城市集合*/
    List<String> citylist;
    /*表示ViewPage的页数显示集合*/
    List<ImageView> imageViewList;

    /*适配*/
    CityFragmentAdapter adapter;
    private SharedPreferences pref;
    private int bgNum;
    RelativeLayout outLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        fragmentList = new ArrayList<>();
        citylist = new ArrayList<>();
        imageViewList = new ArrayList<>();
        /* 因为可能搜索界面点击跳转此界面，会传值，所以此处获取一下*/
        try {
            Intent intent = getIntent();
            String city = intent.getStringExtra("city");
            if (!citylist.contains(city)&&!TextUtils.isEmpty(city)) {
                citylist.add(city);
            }
        }catch (Exception e){
            Log.i("animee","程序出现问题了！！");
        }
        if(citylist.size()==0){
            citylist.add("北京");
            System.out.println("每次都是北京？");
        }
        /*ViePage初始化的方法*/
        initPage();
        adapter = new CityFragmentAdapter(getSupportFragmentManager(), fragmentList);
        main.setAdapter(adapter);
        /*创建指数器*/
        ininPoint();
        /*设置为先进去最近添加的一个城市信息*/
        main.setCurrentItem(fragmentList.size()-1);
        /*设置ViewPage的页面监听*/
        setPagerListener();
    }


    /**
     * ViewPager的监听方法
     */
    private void setPagerListener() {
        /*设置监听事件*/
        main.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i=0;i<imageViewList.size();i++){
                    imageViewList.get(i).setImageResource(R.mipmap.a1);
                }
                imageViewList.get(position).setImageResource(R.mipmap.a2);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 创建小圆点作为指数器
     */
    private void ininPoint() {
   for(int i=0;i<fragmentList.size();i++){
       ImageView p=new ImageView(this);
       p.setImageResource(R.mipmap.a1);
       p.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
       LinearLayout.LayoutParams ip=(LinearLayout.LayoutParams) p.getLayoutParams();
       ip.setMargins(0,0,20,0);
       imageViewList.add(p);
       pointLayout.addView(p);
   }
   imageViewList.get(imageViewList.size()-1).setImageResource(R.mipmap.a2);
    }

    /**
     * 创建Fragment对象，添加到ViewPage中
     */
    private void initPage() {
//        List<DatabaseBean> databaseBeans = DBManager.queryAllInfo();
//        for(int i=0;i<databaseBeans.size();i++){
//            CityWeatherFragment cityWeatherFragment=new CityWeatherFragment();
//            Bundle bundle=new Bundle();
//            bundle.putString("city",databaseBeans.get(i).getCity());
//            cityWeatherFragment.setArguments(bundle);
//            fragmentList.add(cityWeatherFragment);
//        }
        if(citylist.size()!=0){
            for(int i=0;i<citylist.size();i++){
                CityWeatherFragment cityWeatherFragment=new CityWeatherFragment();
                Bundle bundle=new Bundle();
                bundle.putString("city",citylist.get(i));
                cityWeatherFragment.setArguments(bundle);
                fragmentList.add(cityWeatherFragment);
            }
        }
    }

    private void init() {
        addCity =findViewById(R.id.main_iv_add);
        more = findViewById(R.id.main_vi_more);
        pointLayout = findViewById(R.id.main_layout_point);
        main = findViewById(R.id.main_vp);
//        relativeLayout = findViewById(R.id.main_bottom_layout);
        /*添加点击事件*/
        addCity.setOnClickListener(this);
        more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.main_iv_add:
                intent.setClass(this, CityManager.class);
                break;
            case R.id.main_vi_more:
                intent.setClass(this, MoreActivity.class);
                break;
        }
        startActivity(intent);

    }
    /* 当页面重写加载时会调用的函数，这个函数在页面获取焦点之前进行调用，此处完成ViewPager页数的更新*/
    @Override
    protected void onRestart() {
        super.onRestart();
//        获取数据库当中还剩下的城市集合
        List<String> list = DBManager.queryAllCityName();
        if (list.size()==0) {
            list.add("北京");
        }
        citylist.clear();    //重写加载之前，清空原本数据源
        citylist.addAll(list);
//        剩余城市也要创建对应的fragment页面
        fragmentList.clear();
        initPage();
        adapter.notifyDataSetChanged();
//        页面数量发生改变，指示器的数量也会发生变化，重写设置添加指示器
        imageViewList.clear();
        pointLayout.removeAllViews();   //将布局当中所有元素全部移除
        ininPoint();
        main.setCurrentItem(fragmentList.size()-1);
    }
}