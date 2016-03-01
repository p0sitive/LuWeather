package com.lee.luweather.model;

import java.util.List;

/**
 * Created by Administrator on 2016-1-21.
 */
public class Weather {

    /**
     * city : 海淀
     * city_en : haidian
     * cityid : 101010200
     * date :
     * date_y : 2016年01月22日
     * fchh : 08
     * fl1 : 4-5级转3-4级
     * fl2 : 5-6级转3-4级
     * fl3 : 4-5级转小于3级
     * fl4 : 小于3级
     * fl5 : 3-4级转小于3级
     * fl6 : 微风
     * fx1 : 北风
     * fx2 : 北风
     * img1 :
     * img10 :
     * img11 :
     * img12 :
     * img2 :
     * img3 :
     * img4 :
     * img5 :
     * img6 :
     * img7 :
     * img8 :
     * img9 :
     * img_single :
     * img_title1 : 多云
     * img_title10 : 晴
     * img_title11 : 晴
     * img_title12 : 晴
     * img_title2 : 晴
     * img_title3 : 晴
     * img_title4 : 晴
     * img_title5 : 晴
     * img_title6 : 晴
     * img_title7 : 晴
     * img_title8 : 晴
     * img_title9 : 晴
     * img_title_single :
     * index : 寒冷
     * index48 :
     * index48_d :
     * index48_uv :
     * index_ag : 极不易发
     * index_cl : 不宜
     * index_co : 很不舒适
     * index_d :
     * index_ls : 基本适宜
     * index_tr : 一般
     * index_uv : 最弱
     * index_xc : 较不宜
     * st1 :
     * st2 :
     * st3 :
     * st4 :
     * st5 :
     * st6 :
     * temp1 : -6℃~-17℃
     * temp2 : -10℃~-14℃
     * temp3 : -2℃~-11℃
     * temp4 : 2℃~-10℃
     * temp5 : 2℃~-10℃
     * temp6 : 0℃~0℃
     * tempF1 :
     * tempF2 :
     * tempF3 :
     * tempF4 :
     * tempF5 :
     * tempF6 :
     * weather1 : 多云转晴
     * weather2 : 晴
     * weather3 : 晴
     * weather4 : 晴
     * weather5 : 晴
     * weather6 : 晴
     * week : 星期二
     * wind1 : 北风
     * wind2 : 北风
     * wind3 : 北风转无持续风向
     * wind4 : 微风
     * wind5 : 北风转无持续风向
     * wind6 : 微风
     */

    private ForecastEntity forecast;
    /**
     * SD : 16%
     * WD : 北风
     * WS : 3级
     * WSE :
     * city :
     * cityid : 101010200
     * isRadar : 1
     * radar : JC_RADAR_AZ9010_JB
     * temp : -11
     * time : 10:50
     * weather : 多云
     */

    private RealtimeEntity realtime;
    /**
     * city : 海淀
     * city_id : 101010200
     * pub_time : 2016-01-22 10:00
     * aqi : 58
     * pm25 : 5
     * pm10 : 66
     * so2 : 2
     * no2 : 11
     * src : 中国环境监测总站
     * spot :
     */

    private AqiEntity aqi;
    /**
     * cityCode : 101010200
     * date : 2016-01-22
     * humidityMax : 78
     * humidityMin : 16
     * precipitationMax : 0
     * precipitationMin : 0
     * tempMax : -6
     * tempMin : -11
     * weatherEnd : 多云
     * weatherStart : 小雪
     * windDirectionEnd : 北风
     * windDirectionStart : 东北风
     * windMax : 5
     * windMin : 0
     */

    private TodayEntity today;
    /**
     * abnormal :
     * city_code : 101010200
     * detail : 海淀区气象台21日17时14分发布寒潮黄色预警,受强冷空气影响，预计22日至23日，海淀区最低气温将下降10℃左右，最低气温达零下17℃左右；并伴有5、6级偏北风，阵风可达8级左右，请注意防范。

     * holiday :
     * level : 黄色
     * pub_time : 1453367640000
     * title : 海淀区气象台21日17时发布寒潮黄色预警!
     * type : 寒潮
     */

    private List<AlertEntity> alert;
    /**
     * code : fs
     * details : 属弱紫外辐射天气，长期在户外，建议涂擦SPF在8-12之间的防晒护肤品。
     * index : 弱
     * name : 防晒指数
     * otherName :
     */

    private List<IndexEntity> index;

    public void setForecast(ForecastEntity forecast) {
        this.forecast = forecast;
    }

    public void setRealtime(RealtimeEntity realtime) {
        this.realtime = realtime;
    }

    public void setAqi(AqiEntity aqi) {
        this.aqi = aqi;
    }

    public void setToday(TodayEntity today) {
        this.today = today;
    }

    public void setAlert(List<AlertEntity> alert) {
        this.alert = alert;
    }

    public void setIndex(List<IndexEntity> index) {
        this.index = index;
    }

    public ForecastEntity getForecast() {
        return forecast;
    }

    public RealtimeEntity getRealtime() {
        return realtime;
    }

    public AqiEntity getAqi() {
        return aqi;
    }

    public TodayEntity getToday() {
        return today;
    }

    public List<AlertEntity> getAlert() {
        return alert;
    }

    public List<IndexEntity> getIndex() {
        return index;
    }

    public static class ForecastEntity {
        private String city;
        private String date_y;
        private String temp1;
        private String temp2;
        private String temp3;
        private String temp4;
        private String temp5;
        private String weather1;
        private String weather2;
        private String weather3;
        private String weather4;
        private String weather5;

        public void setCity(String city) {
            this.city = city;
        }

        public void setDate_y(String date_y) {
            this.date_y = date_y;
        }

        public void setTemp1(String temp1) {
            this.temp1 = temp1;
        }

        public void setTemp2(String temp2) {
            this.temp2 = temp2;
        }

        public void setTemp3(String temp3) {
            this.temp3 = temp3;
        }

        public void setTemp4(String temp4) {
            this.temp4 = temp4;
        }

        public void setTemp5(String temp5) {
            this.temp5 = temp5;
        }

        public void setWeather1(String weather1) {
            this.weather1 = weather1;
        }

        public void setWeather2(String weather2) {
            this.weather2 = weather2;
        }

        public void setWeather3(String weather3) {
            this.weather3 = weather3;
        }

        public void setWeather4(String weather4) {
            this.weather4 = weather4;
        }

        public void setWeather5(String weather5) {
            this.weather5 = weather5;
        }

        public String getCity() {
            return city;
        }

        public String getDate_y() {
            return date_y;
        }

        public String getTemp1() {
            return temp1;
        }

        public String getTemp2() {
            return temp2;
        }

        public String getTemp3() {
            return temp3;
        }

        public String getTemp4() {
            return temp4;
        }

        public String getTemp5() {
            return temp5;
        }

        public String getWeather1() {
            return weather1;
        }

        public String getWeather2() {
            return weather2;
        }

        public String getWeather3() {
            return weather3;
        }

        public String getWeather4() {
            return weather4;
        }

        public String getWeather5() {
            return weather5;
        }
    }

    public static class RealtimeEntity {
        private String SD;
        private String WD;
        private String WS;
        private String WSE;
        private String temp;
        private String time;
        private String weather;

        public void setSD(String SD) {
            this.SD = SD;
        }

        public void setWD(String WD) {
            this.WD = WD;
        }

        public void setWS(String WS) {
            this.WS = WS;
        }

        public void setWSE(String WSE) {
            this.WSE = WSE;
        }

        public void setTemp(String temp) {
            this.temp = temp;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setWeather(String weather) {
            this.weather = weather;
        }

        public String getSD() {
            return SD;
        }

        public String getWD() {
            return WD;
        }

        public String getWS() {
            return WS;
        }

        public String getWSE() {
            return WSE;
        }

        public String getTemp() {
            return temp;
        }

        public String getTime() {
            return time;
        }

        public String getWeather() {
            return weather;
        }
    }

    public static class AqiEntity {
        private String city;
        private String city_id;
        private String pub_time;
        private String aqi;
        private String pm25;
        private String pm10;
        private String so2;
        private String no2;
        private String src;
        private String spot;

        public void setCity(String city) {
            this.city = city;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public void setPub_time(String pub_time) {
            this.pub_time = pub_time;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public void setPm10(String pm10) {
            this.pm10 = pm10;
        }

        public void setSo2(String so2) {
            this.so2 = so2;
        }

        public void setNo2(String no2) {
            this.no2 = no2;
        }

        public void setSrc(String src) {
            this.src = src;
        }

        public void setSpot(String spot) {
            this.spot = spot;
        }

        public String getCity() {
            return city;
        }

        public String getCity_id() {
            return city_id;
        }

        public String getPub_time() {
            return pub_time;
        }

        public String getAqi() {
            return null==aqi?"未知":aqi;
        }

        public String getPm25() {
            return null==pm25?"未知":pm25;
        }

        public String getPm10() {
            return null==pm10?"未知":pm10;
        }

        public String getSo2() {
            return so2;
        }

        public String getNo2() {
            return no2;
        }

        public String getSrc() {
            return src;
        }

        public String getSpot() {
            return spot;
        }
    }

    public static class TodayEntity {
        private String date;
        private int humidityMax;
        private int humidityMin;
        private int precipitationMax;
        private int precipitationMin;
        private int tempMax;
        private int tempMin;
        private String weatherEnd;
        private String weatherStart;
        private String windDirectionEnd;
        private String windDirectionStart;
        private int windMax;
        private int windMin;

        public void setDate(String date) {
            this.date = date;
        }

        public void setHumidityMax(int humidityMax) {
            this.humidityMax = humidityMax;
        }

        public void setHumidityMin(int humidityMin) {
            this.humidityMin = humidityMin;
        }

        public void setPrecipitationMax(int precipitationMax) {
            this.precipitationMax = precipitationMax;
        }

        public void setPrecipitationMin(int precipitationMin) {
            this.precipitationMin = precipitationMin;
        }

        public void setTempMax(int tempMax) {
            this.tempMax = tempMax;
        }

        public void setTempMin(int tempMin) {
            this.tempMin = tempMin;
        }

        public void setWeatherEnd(String weatherEnd) {
            this.weatherEnd = weatherEnd;
        }

        public void setWeatherStart(String weatherStart) {
            this.weatherStart = weatherStart;
        }

        public void setWindDirectionEnd(String windDirectionEnd) {
            this.windDirectionEnd = windDirectionEnd;
        }

        public void setWindDirectionStart(String windDirectionStart) {
            this.windDirectionStart = windDirectionStart;
        }

        public void setWindMax(int windMax) {
            this.windMax = windMax;
        }

        public void setWindMin(int windMin) {
            this.windMin = windMin;
        }

        public String getDate() {
            return date;
        }

        public int getHumidityMax() {
            return humidityMax;
        }

        public int getHumidityMin() {
            return humidityMin;
        }

        public int getPrecipitationMax() {
            return precipitationMax;
        }

        public int getPrecipitationMin() {
            return precipitationMin;
        }

        public int getTempMax() {
            return tempMax;
        }

        public int getTempMin() {
            return tempMin;
        }

        public String getWeatherEnd() {
            return weatherEnd;
        }

        public String getWeatherStart() {
            return weatherStart;
        }

        public String getWindDirectionEnd() {
            return windDirectionEnd;
        }

        public String getWindDirectionStart() {
            return windDirectionStart;
        }

        public int getWindMax() {
            return windMax;
        }

        public int getWindMin() {
            return windMin;
        }
    }

    public static class AlertEntity {
        private String abnormal;
        private String city_code;
        private String detail;
        private String holiday;
        private String level;
        private long pub_time;
        private String title;
        private String type;

        public void setAbnormal(String abnormal) {
            this.abnormal = abnormal;
        }

        public void setCity_code(String city_code) {
            this.city_code = city_code;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public void setHoliday(String holiday) {
            this.holiday = holiday;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public void setPub_time(long pub_time) {
            this.pub_time = pub_time;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAbnormal() {
            return abnormal;
        }

        public String getCity_code() {
            return city_code;
        }

        public String getDetail() {
            return detail;
        }

        public String getHoliday() {
            return holiday;
        }

        public String getLevel() {
            return level;
        }

        public long getPub_time() {
            return pub_time;
        }

        public String getTitle() {
            return title;
        }

        public String getType() {
            return type;
        }
    }

    public static class IndexEntity {
        private String code;
        private String details;
        private String index;
        private String name;
        private String otherName;

        public void setCode(String code) {
            this.code = code;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setOtherName(String otherName) {
            this.otherName = otherName;
        }

        public String getCode() {
            return code;
        }

        public String getDetails() {
            return details;
        }

        public String getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }

        public String getOtherName() {
            return otherName;
        }
    }
}
