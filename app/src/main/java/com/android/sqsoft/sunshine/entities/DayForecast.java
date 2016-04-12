package com.android.sqsoft.sunshine.entities;

import java.util.Date;

/**
 * Created by Pedro on 10/04/2016.
 */
public class DayForecast extends Entity {
    private Date date;
    private String description;
    private int weather_id;
    private double tmin;
    private double tmax;
    private double humidity;
    private double pressure;
    private double wind;
    private double windDir;
    private double clouds;
    private Date sunset;
    private Date sunrise;
    private double rain;



    //getters and setters
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(int weather_id) {
        this.weather_id = weather_id;
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

    public double getWind() {
        return wind;
    }

    public void setWind(double wind) {
        this.wind = wind;
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

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }


}
