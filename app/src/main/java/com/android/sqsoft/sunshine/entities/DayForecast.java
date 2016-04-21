package com.android.sqsoft.sunshine.entities;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DayForecast implements Parcelable {


    private Date date;
    private double currentTemp;
    private double tmin;
    private double tmax;
    private double humidity;
    private double pressure;
    private List<Weather> weather = new ArrayList<>();
    private double windSpeed;
    private double windDir;
    private double clouds;
    private double rain;
    private Date sunset;
    private Date sunrise;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getTmin() {
        return tmin;
    }

    public void setTmin(double tmin) {
        this.tmin = tmin;
    }

    public double getTmax() {
        return tmax;
    }

    public void setTmax(double tmax) {
        this.tmax = tmax;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double wind) {
        this.windSpeed = wind;
    }

    public double getWindDir() {
        return windDir;
    }

    public void setWindDir(double windDir) {
        this.windDir = windDir;
    }

    public double getClouds() {
        return clouds;
    }

    public void setClouds(double clouds) {
        this.clouds = clouds;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public Date getSunset() {
        return sunset;
    }

    public void setSunset(Date sunset) {
        this.sunset = sunset;
    }

    public Date getSunrise() {
        return sunrise;
    }

    public void setSunrise(Date sunrise) {
        this.sunrise = sunrise;
    }

    public double getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(double currentTemp) {
        this.currentTemp = currentTemp;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date != null ? date.getTime() : -1);
        dest.writeDouble(this.currentTemp);
        dest.writeDouble(this.tmin);
        dest.writeDouble(this.tmax);
        dest.writeDouble(this.humidity);
        dest.writeDouble(this.pressure);
        dest.writeList(this.weather);
        dest.writeDouble(this.windSpeed);
        dest.writeDouble(this.windDir);
        dest.writeDouble(this.clouds);
        dest.writeDouble(this.rain);
        dest.writeLong(sunset != null ? sunset.getTime() : -1);
        dest.writeLong(sunrise != null ? sunrise.getTime() : -1);
    }

    public DayForecast() {
    }

    protected DayForecast(Parcel in) {
        long tmpDate = in.readLong();
        this.date = tmpDate == -1 ? null : new Date(tmpDate);
        this.currentTemp = in.readDouble();
        this.tmin = in.readDouble();
        this.tmax = in.readDouble();
        this.humidity = in.readDouble();
        this.pressure = in.readDouble();
        this.weather = new ArrayList<Weather>();
        in.readList(this.weather, Weather.class.getClassLoader());
        this.windSpeed = in.readDouble();
        this.windDir = in.readDouble();
        this.clouds = in.readDouble();
        this.rain = in.readDouble();
        long tmpSunset = in.readLong();
        this.sunset = tmpSunset == -1 ? null : new Date(tmpSunset);
        long tmpSunrise = in.readLong();
        this.sunrise = tmpSunrise == -1 ? null : new Date(tmpSunrise);
    }

    public static final Parcelable.Creator<DayForecast> CREATOR = new Parcelable.Creator<DayForecast>() {
        @Override
        public DayForecast createFromParcel(Parcel source) {
            return new DayForecast(source);
        }

        @Override
        public DayForecast[] newArray(int size) {
            return new DayForecast[size];
        }
    };
}
