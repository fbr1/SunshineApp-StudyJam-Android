package com.android.sqsoft.sunshine;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Pedro on 18/04/2016.
 */
public class Utility {

    public static String formatTemperature(Context context, double temperature) {
        return String.format(context.getString(R.string.format_temperature), temperature);
    }

    public static String formatDate(long dateInMilliseconds) {
        Date date = new Date(dateInMilliseconds);
        return DateFormat.getDateInstance().format(date);
    }

    public static int getIconResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.ic_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.ic_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.ic_rain;
        } else if (weatherId == 511) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.ic_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.ic_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.ic_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.ic_storm;
        } else if (weatherId == 800) {
            return R.drawable.ic_clear;
        } else if (weatherId == 801) {
            return R.drawable.ic_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.ic_cloudy;
        }
        return -1;
    }

    public static int getArtResourceForWeatherCondition(int weatherId) {
        // Based on weather code data found at:
        // http://bugs.openweathermap.org/projects/api/wiki/Weather_Condition_Codes
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.art_storm;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.art_light_rain;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.art_rain;
        } else if (weatherId == 511) {
            return R.drawable.art_snow;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.art_rain;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.art_snow;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.art_fog;
        } else if (weatherId == 761 || weatherId == 781) {
            return R.drawable.art_storm;
        } else if (weatherId == 800) {
            return R.drawable.art_clear;
        } else if (weatherId == 801) {
            return R.drawable.art_light_clouds;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.art_clouds;
        }
        return -1;
    }
    public static boolean isDebuggeable(Context context){
        return ( 0 != ( context.getApplicationInfo().flags &= ApplicationInfo.FLAG_DEBUGGABLE ) );
    }

    public static String getLocationName(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String defaultValue = context.getResources().getString(R.string.saved_location_default_name_value);
        return sharedPref.getString(context.getString(R.string.saved_location_name), defaultValue);
    }

    public static boolean isLocationSet(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getBoolean(context.getString(R.string.saved_location_status), false);
    }

    public static void saveLocation(Context context,String locationName,LatLng coordinates){
        SharedPreferences.Editor editor = getSharedPrefEditor(context);
        editor.putString(context.getString(R.string.saved_location_name),locationName);
        editor.putLong(context.getString(R.string.saved_location_lat),Double.doubleToRawLongBits(coordinates.latitude));
        editor.putLong(context.getString(R.string.saved_location_lon),Double.doubleToRawLongBits(coordinates.longitude));
        editor.putBoolean(context.getString(R.string.saved_location_status),true);
        editor.apply();
    }

    public static LatLng getLocationLatLng(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);

        // Get Default Values
        Long defaultLat = Long.valueOf(context.getResources().getString(R.string.saved_location_default_lat_value));
        Long defaultLon = Long.valueOf(context.getResources().getString(R.string.saved_location_default_lon_value));

        // Get Stored Values
        Double lat = Double.longBitsToDouble(sharedPref.getLong(context.getString(R.string.saved_location_lat),defaultLat));
        Double lon = Double.longBitsToDouble(sharedPref.getLong(context.getString(R.string.saved_location_lon),defaultLon));

        return new LatLng(lat,lon);
    }

    public static void clearLocation(Context context){
        SharedPreferences.Editor editor = getSharedPrefEditor(context);
        editor.remove(context.getString(R.string.saved_location_name));
        editor.remove(context.getString(R.string.saved_location_lat));
        editor.remove(context.getString(R.string.saved_location_lon));
        editor.remove(context.getString(R.string.saved_location_status));
        editor.apply();
    }

    private static SharedPreferences.Editor getSharedPrefEditor(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.edit();
    }
}
