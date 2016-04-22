package com.android.sqsoft.sunshine.logic;

import android.content.Context;
import android.util.Log;

import com.android.sqsoft.sunshine.BuildConfig;
import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.sqsoft.sunshine.entities.Weather;
import com.android.volley.NoConnectionError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ForecastLogic extends Logic{

    // Contants
    private static final String TAG = ForecastLogic.class.getSimpleName();
    private static final String OPENWEATHER_API_KEY = BuildConfig.OPEN_WEATHER_API_KEY;
    private static final String OPENWEATHER_UNIT = "metric";
    private static final String OPENWEATHER_LIST_SIZE = "4";
    private static final String OPENWEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private static final String OPENWEATHER_DAILY_URL = OPENWEATHER_BASE_URL + "forecast/daily?mode=json&units=" +
            OPENWEATHER_UNIT + "&cnt=" +
            OPENWEATHER_LIST_SIZE + "&appid=" + OPENWEATHER_API_KEY;
    private static final String OPENWEATHER_CURRENT_URL = OPENWEATHER_BASE_URL + "weather?units=" +
            OPENWEATHER_UNIT + "&appid=" + OPENWEATHER_API_KEY;
    private static final long CACHE_LIFE = 60000; // 1 minute

    // Variables

    private double lastLat = 0;
    private double lastLon = 0;
    private long lastRequest = 0;
    private List<DayForecast> lastForecastL;

    private static ForecastLogic forecastLogic = null;

    public void getExtendedWeatherByCoords(final Listener listener, final double latitude, final double longitude, boolean useCache) {
        String url = OPENWEATHER_DAILY_URL +
                "&lat=" + String.valueOf(latitude) +
                "&lon=" + String.valueOf(longitude);

        if(isCacheValid(latitude,longitude) && useCache){
            listener.onResult(lastForecastL);
        }else{
            JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<DayForecast> forecastList = jsonToDayForecastList(response);

                            storeToCache(forecastList,latitude,longitude);

                            listener.onResult(forecastList);

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Error in JSON response: " + error.getMessage());

                    String errormessage;
                    if(error instanceof TimeoutError || error instanceof NoConnectionError){
                        errormessage = "NO INTERNET CONNECTION";
                    }else{
                        errormessage=null;
                    }
                    listener.onError(errormessage);
                }
            });

            jsObjRequest.setShouldCache(useCache);

            addToRequestQueue(jsObjRequest);
        }


    }

    public void getCurrentWeather(final Listener listener) {
        // TODO remove hardcoded city
        String url = OPENWEATHER_CURRENT_URL + "&q=London";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    listener.onResult(jsonToDayForecast(response));

                }
            },
            new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Error in JSON response: " + error.getMessage());

                String errormessage;
                if(error instanceof TimeoutError || error instanceof NoConnectionError){
                    errormessage = "NO INTERNET CONNECTION";
                }else{
                    errormessage=null;
                }
                listener.onError(errormessage);
            }
        });

        addToRequestQueue(jsObjRequest);
    }

    private boolean isCacheValid(Double lat,Double lon){
        Long time = System.currentTimeMillis();
        if(lat == lastLat && lon == lastLon){
            if( time - lastRequest < CACHE_LIFE){
                return true;
            }
        }

        // Set last request to current one
        lastLat = lat;
        lastLon = lon;
        lastRequest = time;

        return false;
    }

    private void storeToCache(List<DayForecast> forecastList,double lat, double lon){
        lastForecastL = forecastList;
        lastLat = lat;
        lastLon = lon;
        lastRequest =  System.currentTimeMillis();
    }

    private List<DayForecast> jsonToDayForecastList(JSONObject response) {
        List<DayForecast> forecastlist = new ArrayList<>();

        try {
            // Get list node
            JSONArray forecastjArray = response.getJSONArray("list");

            for (int i = 0; i < forecastjArray.length(); i++) {
                JSONObject jsonObject = forecastjArray.getJSONObject(i);
                forecastlist.add(jsonToDayForecastExtended(jsonObject));
            }

        } catch (JSONException e) {
            Log.d(TAG, "Error in JSON parsing. " + e.getMessage());
        }

        return forecastlist;
    }

    private DayForecast jsonToDayForecastExtended(JSONObject jsonObject) throws JSONException {
        DayForecast dayForecast = new DayForecast();

        // temp JSONObject
        JSONObject temp = jsonObject.getJSONObject("temp");
        dayForecast.setTmax(temp.getDouble("max"));
        dayForecast.setTmin(temp.getDouble("min"));

        // weather JSONArray
        List<Weather> weatherList = jsonToWeather(jsonObject.getJSONArray("weather"));

        dayForecast.setWeather(weatherList);

        // Remaining fields
        // TODO pasar date a local time
        dayForecast.setDate(new Date(jsonObject.getLong("dt")*1000));
        dayForecast.setPressure(jsonObject.getDouble("pressure"));
        dayForecast.setHumidity(jsonObject.getDouble("humidity"));
        dayForecast.setWindSpeed(jsonObject.getDouble("speed"));
        dayForecast.setWindDir(jsonObject.getDouble("deg"));
        dayForecast.setClouds(jsonObject.getDouble("clouds"));

        // If rain filed isn't missing
        if(!jsonObject.isNull("rain")){
            dayForecast.setRain(jsonObject.getDouble("rain"));
        }

        return dayForecast;
    }

    private DayForecast jsonToDayForecast(JSONObject response) {
        DayForecast dayForecast = new DayForecast();

        try {
            // TODO pasar date a local time
            // weather JSONArray
            List<Weather> weatherList = jsonToWeather(response.getJSONArray("weather"));
            dayForecast.setWeather(weatherList);

            // wind JSONObject
            JSONObject wind = response.getJSONObject("wind");
            dayForecast.setWindSpeed(wind.getDouble("speed"));
            dayForecast.setWindDir(wind.getDouble("deg"));

            // sys JSONObject
            JSONObject sys = response.getJSONObject("sys");
            dayForecast.setSunrise(new Date(sys.getLong("sunrise")*1000));
            dayForecast.setSunset(new Date(sys.getLong("sunset")*1000));

            // clouds JSONObject
            JSONObject clouds = response.getJSONObject("clouds");
            dayForecast.setClouds(clouds.getDouble("all"));

            // main JSONObject
            JSONObject main = response.getJSONObject("main");
            dayForecast.setCurrentTemp(main.getDouble("temp"));
            dayForecast.setPressure(main.getDouble("pressure"));
            dayForecast.setHumidity(main.getDouble("humidity"));

            // Remaining fields
            dayForecast.setDate(new Date(response.getLong("dt")*1000));


        } catch (JSONException e) {
            Log.d(TAG, "Error in JSON parsing. " + e.getMessage());
        }

        return dayForecast;
    }


    private List<Weather> jsonToWeather(JSONArray weatherjArray) throws JSONException {

        List<Weather> weatherList = new ArrayList<>();
        for (int i = 0; i < weatherjArray.length(); i++) {
            JSONObject weatherjObject = weatherjArray.getJSONObject(i);
            Weather weather = new Weather();
            weather.setId(weatherjObject.getInt("id"));
            weather.setName(weatherjObject.get("main").toString());
            weather.setDescription(weatherjObject.get("description").toString());
            weatherList.add(weather);
        }

        return weatherList;

    }

    private ForecastLogic(Context context) {
        super(context);
        requestQueue = getRequestQueue();
    }

    public synchronized static ForecastLogic initializeInstance(Context context) {
        if (forecastLogic == null) {
            forecastLogic = new ForecastLogic(context);
        }

        return forecastLogic;
    }

    public synchronized static ForecastLogic getInstance() {
        if (forecastLogic == null) {
            throw new IllegalStateException(TAG +
                    " is not initialized, call initializeInstance(...) first");
        }
        return forecastLogic;
    }




}
