package com.example.movemate.utils;

import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.movemate.models.DailyWeather;
import com.example.movemate.models.GeoData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OpenMeteoUtil {
    private static final String GEOCODING_URL = "https://geocoding-api.open-meteo.com/v1/search";
    private static final String FORECAST_URL = "https://api.open-meteo.com/v1/forecast";

    private final RequestQueue requestQueue;

    public OpenMeteoUtil(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public interface GeoDataCallback {
        void onSuccess(GeoData geoData);
        void onError(String message);
    }

    public interface WeatherDataCallback {
        void onSuccess(List<DailyWeather> weather);
        void onError(String message);
    }

    public void getGeoData(String city, GeoDataCallback callback) {
        if (city == null || city.trim().length() < 2) {
            callback.onError("Enter a valid city");
            return;
        }

        Uri requestUri = Uri.parse(GEOCODING_URL).buildUpon()
                .appendQueryParameter("name", city.trim())
                .appendQueryParameter("count", "1")
                .appendQueryParameter("language", "en")
                .appendQueryParameter("format", "json")
                .build();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                requestUri.toString(),
                null,
                response -> {
                    try {
                        JSONArray results = response.optJSONArray("results");
                        if (results == null || results.length() == 0) {
                            callback.onError("City not found");
                            return;
                        }

                        JSONObject result = results.getJSONObject(0);
                        callback.onSuccess(new GeoData(
                                result.optString("name", city.trim()),
                                result.optString("country", ""),
                                result.getDouble("latitude"),
                                result.getDouble("longitude")
                        ));
                    } catch (JSONException exception) {
                        callback.onError("Unable to read location data");
                    }
                },
                error -> callback.onError(getErrorMessage(error))
        );
        requestQueue.add(request);
    }

    public void getWeatherData(double latitude, double longitude, WeatherDataCallback callback) {
        Uri requestUri = Uri.parse(FORECAST_URL).buildUpon()
                .appendQueryParameter("latitude", String.valueOf(latitude))
                .appendQueryParameter("longitude", String.valueOf(longitude))
                .appendQueryParameter("daily", "weather_code,temperature_2m_min,temperature_2m_max")
                .appendQueryParameter("forecast_days", "7")
                .appendQueryParameter("timezone", "auto")
                .build();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                requestUri.toString(),
                null,
                response -> {
                    try {
                        JSONObject daily = response.getJSONObject("daily");
                        JSONArray dates = daily.getJSONArray("time");
                        JSONArray codes = daily.getJSONArray("weather_code");
                        JSONArray minimums = daily.getJSONArray("temperature_2m_min");
                        JSONArray maximums = daily.getJSONArray("temperature_2m_max");
                        int count = Math.min(7, dates.length());
                        List<DailyWeather> weather = new ArrayList<>(count);

                        for (int index = 0; index < count; index++) {
                            weather.add(new DailyWeather(
                                    dates.getString(index),
                                    codes.getInt(index),
                                    minimums.getDouble(index),
                                    maximums.getDouble(index)
                            ));
                        }
                        callback.onSuccess(weather);
                    } catch (JSONException exception) {
                        callback.onError("Unable to read weather data");
                    }
                },
                error -> callback.onError(getErrorMessage(error))
        );
        requestQueue.add(request);
    }

    public void cancelAll() {
        requestQueue.cancelAll(request -> true);
    }

    @NonNull
    private String getErrorMessage(com.android.volley.VolleyError error) {
        return error.getMessage() == null ? "Weather request failed" : error.getMessage();
    }
}
