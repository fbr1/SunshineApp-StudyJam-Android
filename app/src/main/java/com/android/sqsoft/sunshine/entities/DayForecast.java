package com.android.sqsoft.sunshine.entities;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pedro on 10/04/2016.
 */
public class DayForecast extends Entity {


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

}
