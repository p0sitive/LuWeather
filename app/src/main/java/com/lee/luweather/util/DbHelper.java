package com.lee.luweather.util;

import com.lee.luweather.model.CityWeather;
import com.lee.luweather.model.SelectCity;

import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by Administrator on 2016-1-27.
 */
public class DbHelper {
    static DbHelper instance;
    static DbManager dbManager;

    private DbHelper() {
        this.dbManager = Utils.getDbHelper();
    }


    public static DbHelper getInstance() {
        if (instance == null) {
            instance = new DbHelper();
        }
        return instance;
    }

    /**
     * 保存所选城市
     *
     * @param selectCity
     */
    public void savaSelectCity(SelectCity selectCity) {
        try {
            if (!(dbManager.selector(SelectCity.class)
                    .where("cityname", "=", selectCity.getCityname())
                    .findAll().size() > 0)) {
                dbManager.save(selectCity);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取所有选取的城市
     *
     * @return
     */
    public List<SelectCity> getAllSelectCity() {
        try {
            dbManager.selector(SelectCity.class);
            return dbManager.selector(SelectCity.class).findAll();
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 保存城市天气信息
     *
     * @param cityWeather
     */
    public void saveCityWeather(CityWeather cityWeather) {
        try {
            dbManager.selector(CityWeather.class);
            CityWeather dbWeather = getLastCityWeather(cityWeather.getCityname());
            if (dbWeather == null ||
                    !dbWeather.getWeather().equals(cityWeather.getWeather())) {
                dbManager.save(cityWeather);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取最近的天气信息
     *
     * @param cityName
     * @return
     */
    public CityWeather getLastCityWeather(String cityName) {
        try {
            return dbManager.selector(CityWeather.class)
                    .where("cityname", "=", cityName)
                    .orderBy("date", true)
                    .findFirst();
        } catch (DbException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除所有的城市和天气信息
     */
    public void deleteAllCity() {
        try {
            dbManager.delete(CityWeather.class);
            dbManager.delete(SelectCity.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param selectCity
     */
    public void deleteCity(SelectCity selectCity) {
        try {
            dbManager.delete(selectCity);
            dbManager.delete(CityWeather.class,
                    WhereBuilder.b("cityname", "=", selectCity.getCityname()));
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    public int getSelectCityIndex() {
        try {
            return dbManager.findAll(SelectCity.class).size();
        } catch (DbException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
