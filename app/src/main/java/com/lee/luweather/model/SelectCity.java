package com.lee.luweather.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016-1-27.
 */
@Table(name = "selectcity")
public class SelectCity {
    @Column(name = "id", isId = true, autoGen = true)
    int id;

    @Column(name = "cityname")
    String cityname;

    @Column(name = "cityid")
    String cityid;
    @Column(name = "weather")
    String weather;
    @Column(name = "date")
    String date;
    @Column(name = "showindex")
    int showindex;

    public int getShowindex() {
        return showindex;
    }

    public void setShowindex(int showindex) {
        this.showindex = showindex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
