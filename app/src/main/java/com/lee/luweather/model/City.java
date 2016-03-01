package com.lee.luweather.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 城市类
 * Created by Administrator on 2016-1-21.
 */
@Table(name = "city")
public class City {
    @Column(name = "id", isId = true)
    int id;
    @Column(name = "privince")
    String privince;
    @Column(name = "city")
    String city;
    @Column(name = "number")
    String number;
    @Column(name = "allpy")
    String allpy;
    @Column(name = "allfirstpy")
    String allfirstpy;
    @Column(name = "firstpy")
    String firstpy;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrivince() {
        return privince;
    }

    public void setPrivince(String privince) {
        this.privince = privince;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAllpy() {
        return allpy;
    }

    public void setAllpy(String allpy) {
        this.allpy = allpy;
    }

    public String getAllfirstpy() {
        return allfirstpy;
    }

    public void setAllfirstpy(String allfirstpy) {
        this.allfirstpy = allfirstpy;
    }

    public String getFirstpy() {
        return firstpy;
    }

    public void setFirstpy(String firstpy) {
        this.firstpy = firstpy;
    }
}
