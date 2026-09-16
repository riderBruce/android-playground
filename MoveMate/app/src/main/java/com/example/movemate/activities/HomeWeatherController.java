package com.example.movemate.activities;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movemate.R;
import com.example.movemate.models.DailyWeather;
import com.example.movemate.models.GeoData;
import com.example.movemate.utils.OpenMeteoUtil;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class HomeWeatherController {
    private static final String PROFILE_PREFERENCES = "profile_preferences";
    private static final String CITY_KEY = "city";
    private static final String DEFAULT_CITY = "New Westminster";

    private final View view;
    private final OpenMeteoUtil openMeteoUtil;
    private boolean released;

    public HomeWeatherController(View view, Context context) {
        this.view = view;
        openMeteoUtil = new OpenMeteoUtil(context);
    }

    public void load() {
        String city = getSavedCity();
        showCityName(city);

        if (city.isEmpty() || released) {
            return;
        }

        openMeteoUtil.getGeoData(city, new OpenMeteoUtil.GeoDataCallback() {
            @Override
            public void onSuccess(GeoData geo) {
                if (released) {
                    return;
                }

                openMeteoUtil.getWeatherData(geo.getLatitude(), geo.getLongitude(),
                        new OpenMeteoUtil.WeatherDataCallback() {
                            @Override
                            public void onSuccess(List<DailyWeather> weather) {
                                if (!released) {
                                    display(weather);
                                }
                            }

                            @Override
                            public void onError(String message) {
                                // Weather data could not be loaded.
                            }
                        });
            }

            @Override
            public void onError(String message) { }
        });
    }

    private String getSavedCity() {
        String city = view.getContext()
                .getSharedPreferences(PROFILE_PREFERENCES, Context.MODE_PRIVATE)
                .getString(CITY_KEY, DEFAULT_CITY);

        if (city == null) {
            return "";
        }

        return city.trim();
    }

    private void showCityName(String city) {
        TextView cityText = view.findViewById(R.id.tvWeatherCity);
        if (city.isEmpty()) {
            cityText.setText(DEFAULT_CITY);
        } else {
            cityText.setText(city);
        }
    }

    private void display(List<DailyWeather> weather) {
        int[] days = {R.id.tvWeatherDay1,R.id.tvWeatherDay2,R.id.tvWeatherDay3,R.id.tvWeatherDay4,R.id.tvWeatherDay5,R.id.tvWeatherDay6,R.id.tvWeatherDay7};
        int[] icons = {R.id.imgWeatherDay1,R.id.imgWeatherDay2,R.id.imgWeatherDay3,R.id.imgWeatherDay4,R.id.imgWeatherDay5,R.id.imgWeatherDay6,R.id.imgWeatherDay7};
        int[] highs = {R.id.tvWeatherHigh1,R.id.tvWeatherHigh2,R.id.tvWeatherHigh3,R.id.tvWeatherHigh4,R.id.tvWeatherHigh5,R.id.tvWeatherHigh6,R.id.tvWeatherHigh7};
        int[] lows = {R.id.tvWeatherLow1,R.id.tvWeatherLow2,R.id.tvWeatherLow3,R.id.tvWeatherLow4,R.id.tvWeatherLow5,R.id.tvWeatherLow6,R.id.tvWeatherLow7};
        LocalDate today = LocalDate.now();

        for (int i = 0; i < days.length; i++) {
            TextView dayText = view.findViewById(days[i]);
            LocalDate date = today.plusDays(i);
            String dayName = date.getDayOfWeek()
                    .getDisplayName(TextStyle.NARROW, Locale.getDefault());
            dayText.setText(dayName);

            if (i < weather.size()) {
                DailyWeather dailyWeather = weather.get(i);
                int code = dailyWeather.getWeatherCode();
                ImageView icon = view.findViewById(icons[i]);
                icon.setImageResource(getIcon(code));
                icon.setContentDescription(getDescription(code));

                TextView highText = view.findViewById(highs[i]);
                highText.setText(formatTemperature(dailyWeather.getMaximumTemperature()));

                TextView lowText = view.findViewById(lows[i]);
                lowText.setText(formatTemperature(dailyWeather.getMinimumTemperature()));
            }
        }
    }

    private String formatTemperature(double temperature) {
        return String.format(Locale.getDefault(), "%.0f°", temperature);
    }
    private int getIcon(int code) {
        if (code == 0 || code == 1) return R.drawable.ic_weather_sun;
        if (code >= 71 && code <= 86) return R.drawable.ic_weather_snow;
        if ((code >= 51 && code <= 67) || (code >= 80 && code <= 82)) return R.drawable.ic_weather_rain;
        return R.drawable.ic_weather_cloud;
    }
    private String getDescription(int code) {
        if (code == 0 || code == 1) return "Sunny";
        if (code >= 71 && code <= 86) return "Snowy";
        if ((code >= 51 && code <= 67) || (code >= 80 && code <= 82)) return "Rainy";
        return "Cloudy";
    }
    public void release() {
        released = true;
        openMeteoUtil.cancelAll();
    }
}
