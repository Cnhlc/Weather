package bistu.ct.weather.CityMangerPage;

import android.content.Intent;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import bistu.ct.weather.ui.MainActivity;
import bistu.ct.weather.ui.BaseActivity;
import bistu.ct.weather.juhe.WeatherBean;
import bistu.ct.weather.juhe.URLUtil;
import bistu.ct.weather.R;

import com.google.gson.Gson;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener{
    EditText searchEt;
    ImageView submitIv;
    GridView searchGv;
    String[]hotCitys = {"北京","上海","广州","深圳","珠海","佛山","南京","苏州","厦门","长沙","成都","福州",
            "杭州","武汉","青岛","西安","太原","沈阳","重庆","天津","南宁"};
    private ArrayAdapter<String> adapter;

    String city;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        searchEt = findViewById(R.id.search_et);
        submitIv = findViewById(R.id.search_iv_submit);
        searchGv = findViewById(R.id.search_gv);
        submitIv.setOnClickListener(this);
//        设置适配器
        adapter = new ArrayAdapter<>(this, R.layout.item_hotcity, hotCitys);
        searchGv.setAdapter(adapter);
        setListener();
    }
/* 设置监听事件*/
    private void setListener() {
        searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCitys[position];
                String url = URLUtil.getTem_url(city);
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_submit:
                city = searchEt.getText().toString();
                if (!TextUtils.isEmpty(city)) {
//                      判断是否能够找到这个城市
                        String url = URLUtil.getTem_url(city);
                        loadData(url);
                }else {
                    Toast.makeText(this,"输入内容不能为空！",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        WeatherBean weatherBean = new Gson().fromJson(result, WeatherBean.class);
        /*判断城市正错*/
        if (weatherBean.getError_code()==0) {
            Intent intent = new Intent(this, MainActivity.class);
            /*通过标签清空之前的activity，new新的task*/
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("city",city);
            startActivity(intent);
        }else{
            Toast.makeText(this,"暂时未收入此城市天气信息...",Toast.LENGTH_SHORT).show();
        }
    }
}
