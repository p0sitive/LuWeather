package com.lee.luweather.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016-1-27.
 */
@Table(name = "cityweather")
public class CityWeather {
    @Column(name = "id",isId = true,autoGen = true)
    int id;
    @Column(name = "cid",property = "外键，选择城市id")
    int cid;

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    @Column(name = "cityname")
    String cityname;
    @Column(name = "weather")
    String weather;

    @Column(name = "date")
    String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
