package bistu.ct.weather.CityMangerPage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bistu.ct.weather.ui.MainActivity;
import bistu.ct.weather.db.DBManager;
import bistu.ct.weather.db.DatabaseBean;
import bistu.ct.weather.R;

public class CityManager extends AppCompatActivity implements View.OnClickListener{
    ImageView addIv,backIv,deleteIv;
    ListView cityLv;
    List<DatabaseBean> mDatas;  //显示列表数据源
    private CityManagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manger);
        addIv = findViewById(R.id.city_iv_add);
        backIv = findViewById(R.id.city_iv_back);
        deleteIv = findViewById(R.id.city_iv_delete);
        cityLv = findViewById(R.id.city_lv);
        cityLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(CityManager.this, MainActivity.class);
                    intent.putExtra("city",DBManager.searchId(String.valueOf(id+2)));
                    startActivity(intent);
            }
        });
        mDatas = new ArrayList<>();
//        添加点击监听事件
        addIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
//        设置适配器
        System.out.println(mDatas.size()+"------------------------------");
        adapter = new CityManagerAdapter(this, mDatas);
        cityLv.setAdapter(adapter);
    }
    /*  获取数据库当中真实数据源，添加到原有数据源当中，提示适配器更新*/
    @Override
    protected void onResume() {
        super.onResume();
        List<DatabaseBean> list = DBManager.queryAllInfo();
        mDatas.clear();
        mDatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.city_iv_add:
                int cityCount = DBManager.getCityCount();
                if (cityCount<5) {
                    Intent intent = new Intent(this, SearchCityActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"存储城市数量已达上限，请删除后再增加", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.city_iv_back:
                finish();
                break;
            case R.id.city_iv_delete:
                Intent intent1 = new Intent(this, DeleteCityActivity.class);
                startActivity(intent1);
                break;
        }
    }
}
