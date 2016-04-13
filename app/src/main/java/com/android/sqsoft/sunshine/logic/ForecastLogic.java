package com.android.sqsoft.sunshine.logic;

import android.content.Context;
import android.util.Log;

import com.android.sqsoft.sunshine.BuildConfig;
import com.android.sqsoft.sunshine.entities.DayForecast;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Pedro on 11/04/2016.
 */
public class ForecastLogic {

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

    private static ForecastLogic forecastLogic = null;
    private final Context context;
    public RequestQueue requestQueue;

    public void getExtendedWeather(final ForecastListener listener) {

        String url = OPENWEATHER_DAILY_URL + "&q=London";

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        listener.onResult(getForecastList(response));

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error in JSON response: " + error.getMessage());
                        listener.onResult(null);
                    }
                });

        addToRequestQueue(jsObjRequest);
    }

    public void getCurrentWeather() {
    }

    private List<DayForecast> getForecastList(JSONObject response){
        List<DayForecast> forecastlist = new ArrayList<>();

        try {
            JSONArray jArray = response.getJSONArray("list");
            for(int i = 0; i<jArray.length();i++){
                JSONObject jsonObject = jArray.getJSONObject(i);
                forecastlist.add(jsonToDayForecast(jsonObject));
            }

        }catch(JSONException e){
            Log.d(TAG, "Error in JSON parsing. Can't get list node: " + e.getMessage());
        }

        return forecastlist;
    }

    private DayForecast jsonToDayForecast(JSONObject jsonObject){
        DayForecast dayForecast = new DayForecast();
        try {
            // TODO Terminar los demas campos
            dayForecast.setDate(new Date((long) jsonObject.getInt("dt")));
            JSONObject temp = jsonObject.getJSONObject("temp");
            dayForecast.setTmax(temp.getDouble("max"));
        }catch (JSONException e){
            Log.d(TAG, e.getMessage());
        }
        return dayForecast;
    }

    private ForecastLogic(Context context) {
        this.context = context;

        requestQueue = getRequestQueue();
    }

    public synchronized static ForecastLogic getInstance(Context context){
        if(forecastLogic == null){
            forecastLogic = new ForecastLogic(context);
        }

        return forecastLogic;
    }

    public synchronized static ForecastLogic getInstance(){
        if(forecastLogic == null){
            throw new IllegalStateException(TAG +
                    " is not initialized, call getInstance(...) first");
        }
        return forecastLogic;
    }

    private RequestQueue getRequestQueue() {
        if (requestQueue==null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public interface ForecastListener<T>
    {
        void onResult(T object);
    }
}
