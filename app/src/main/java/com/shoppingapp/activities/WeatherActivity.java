package com.shoppingapp.activities;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shoppingapp.R;
import com.shoppingapp.beans.WeatherBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;
import okhttp3.Request;

public class WeatherActivity extends Activity {

    private WeatherBean weatherBean;
    private final String TAG = WeatherActivity.class.getSimpleName();

    private TextView tvCityName;
    private TextView tvType;
    private TextView tvTemp;
    private TextView tvPollution;
    private TextView tvWind;
    private EditText et;
    private ImageView ivBack;
    private ImageView ivSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initVeiw();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = et.getText().toString();
                if(!TextUtils.isEmpty(cityName)){
                    getDataFromNet(cityName);



                }else {
                    Toast.makeText(WeatherActivity.this,"请输入要查找的城市",Toast.LENGTH_SHORT).show();
                }


            }
        });



    }

    private void initVeiw() {
        tvCityName = findViewById(R.id.tv_city);
        tvType = findViewById(R.id.tv_weather_type);
        tvTemp = findViewById(R.id.tv_weather_temp);
        tvPollution = findViewById(R.id.tv_weather_pollution);
        tvWind = findViewById(R.id.tv_weather_wind);
        et = findViewById(R.id.et_search_weather);
        ivBack = findViewById(R.id.iv_back);
        ivSearch = findViewById(R.id.iv_searchweather);
    }
/**获取天气信息*/
    private void getDataFromNet(String cityName) {
        String url = "http://apis.juhe.cn/simpleWeather/query";
        OkHttpUtils
                .get()
                .url(url)
                .addParams("city", cityName)
                .addParams("key", "4547344eb4def925ed40ac5d067dcab0")
                .build()
                .execute(new StringCallback()
                {
                    @Override
                    public void onError(Call call, Exception e) {
                        Log.e(TAG, "onError: 请求天气失败" );
                        Toast.makeText(WeatherActivity.this,"网络连接错误",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response)
                    {
                        Gson gson = new Gson();
                        weatherBean = gson.fromJson(response,WeatherBean.class);


                        if(weatherBean.getError_code()!=0){
                            Toast.makeText(WeatherActivity.this,weatherBean.getReason(),Toast.LENGTH_SHORT).show();
                        }else {
                            String type = weatherBean.getResult().getRealtime().getInfo();
                            String temp = weatherBean.getResult().getRealtime().getTemperature()+"℃";
                            String pollution = "污染指数"+weatherBean.getResult().getRealtime().getAqi();
                            String wind = weatherBean.getResult().getRealtime().getDirect()+":"+weatherBean.getResult().getRealtime().getPower();

                            tvCityName.setText(weatherBean.getResult().getCity());
                            tvPollution.setText(pollution);
                            tvTemp.setText(temp);
                            tvType.setText(type);
                            tvWind.setText(wind);
                        }

                        Log.e(TAG, "onResponse: "+response );
                        Log.e(TAG, "onResponse: bean ==="+weatherBean.toString() );
                    }
                });
    }
}
