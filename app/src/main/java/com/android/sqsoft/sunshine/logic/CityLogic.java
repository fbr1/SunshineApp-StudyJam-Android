package com.android.sqsoft.sunshine.logic;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pedro on 18/04/2016.
 */
public class CityLogic extends Logic{
    private static final String TAG = CityLogic.class.getSimpleName();

    private static final String BASE_URL = "http://gd.geobytes.com/";
    private static final String CALL_TYPE_AUTO_COMPLETE_CITY = "AutoCompleteCity";
    private static final String CALL_TYPE_GET_CITY_DETAILS = "GetCityDetails";
    private static final String QUERY_STRING_AUTO_COMPLETE_CITY = "?callback=?&q=";
    private static final String QUERY_STRING_GET_CITY_DETAILS= "?callback=?&fqcn=";

    private static final String AUTO_COMPLETE_CITY_URL = BASE_URL + CALL_TYPE_AUTO_COMPLETE_CITY + QUERY_STRING_AUTO_COMPLETE_CITY;
    private static final String GET_CITY_DETAILS_URL = BASE_URL + CALL_TYPE_GET_CITY_DETAILS + QUERY_STRING_GET_CITY_DETAILS;

    private static CityLogic cityLogic;


    public CityLogic(Context context) {
        super(context);
    }

    public synchronized static CityLogic getInstance(Context context) {
        if (cityLogic == null) {
            cityLogic = new CityLogic(context);
        }
        return cityLogic;
    }

    public List<String> getCitiesMatched(final Listener listener, String pieceOfCityString) {
        String url = AUTO_COMPLETE_CITY_URL + pieceOfCityString;
        List<String> citiesMatched = new ArrayList<>();
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {



                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error in JSON response: " + error.getMessage());
                listener.onResult(null);
            }
        });

        addToRequestQueue(jsObjRequest);
        return citiesMatched;
    }

}
