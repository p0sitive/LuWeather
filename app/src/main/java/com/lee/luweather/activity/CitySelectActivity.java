package com.lee.luweather.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.lee.luweather.R;
import com.lee.luweather.adapter.CityListAdapter;
import com.lee.luweather.base.Constant;
import com.lee.luweather.base.MyApplication;
import com.lee.luweather.model.City;
import com.lee.luweather.model.SelectCity;
import com.lee.luweather.util.DateUtil;
import com.lee.luweather.util.DbHelper;
import com.lee.luweather.util.StatusBarUtils;
import com.lee.luweather.util.Utils;
import com.lee.luweather.view.LetterFilterListView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

public class CitySelectActivity extends AppCompatActivity {

    List<City> cityList;
    List<City> selectList;
    MyApplication application;
    DbManager db;
    ListView cityListView;
    LetterFilterListView letterFilterListView;
    EditText editText_input;
    CityListAdapter cityListAdapter;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        application = (MyApplication) getApplication();
        application.pushTask(this);
        initData();

        initView();
    }

    private void initData() {
        cityList = new ArrayList<>();
        db = Utils.getDbHelper();

        if (db != null) {
            try {
                cityList = getCityList();
            } catch (Exception e) {
            }
        }
    }

    private void initView() {

        StatusBarUtils.setColor(this,getResources().getColor(R.color.colorPrimaryDark));

        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("城市选择");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        cityListView = (ListView) findViewById(R.id.listview_select_city);
        letterFilterListView = (LetterFilterListView) findViewById(R.id.letterFilterlv_select_city);
        editText_input = (EditText) findViewById(R.id.editText_select_city);
        View header = LayoutInflater.from(this).inflate(R.layout.header_city_list, null);
        cityListView.addHeaderView(header);

        cityListAdapter = new CityListAdapter(this, cityList);
        cityListView.setAdapter(cityListAdapter);

        cityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = cityListAdapter.getList().get(i - 1);
                String cityId = city.getNumber();
                String cityName = city.getCity();

                SelectCity selectCity = new SelectCity();
                selectCity.setCityid(cityId);
                selectCity.setCityname(cityName);
                selectCity.setDate(DateUtil.getNow());
//                selectCity.
                DbHelper.getInstance().savaSelectCity(selectCity);

                Bundle bundle = new Bundle();
                bundle.putString("city", cityName);
                bundle.putString("cityId", cityId);
                Intent intent = new Intent(Constant.INTENT_ACTION_GETCITY);
                intent.putExtras(bundle);
                CitySelectActivity.this.sendBroadcast(intent);
                CitySelectActivity.this.finish();

            }
        });

        editText_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = editText_input.getText().toString();
                int length = str.length();
                if (length > 0) {
                    filterCityData(str);
                } else {
                    cityListAdapter.updateListView(getCityList());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public List<City> getCityList() {
        try {
            return db.selector(City.class).orderBy("allpy").findAll();
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void filterCityData(String str) {
        try {
            selectList = db.selector(City.class).where("city", "like", str)
                    .or("allpy", "like", str)
                    .or("firstpy", "like", str)
                    .or("allfirstpy", "like", str).findAll();
        } catch (DbException e) {
            e.printStackTrace();
        }
        cityListAdapter.updateListView(selectList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
