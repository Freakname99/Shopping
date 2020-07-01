package com.shoppingapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.beans.GoodsBean;
import com.shoppingapp.db.DbHelper;
import com.shoppingapp.db.DbManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    private GridView gvCategory;
    private ImageView ivWeather;
    private ImageView ivCar;
    private ImageView ivMine;
    private DbHelper dbHelper;
    private EditText etSearch;
    private ImageView ivSearch;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        MyApplication myApplication = (MyApplication) this.getApplication();

        if (sharedPreferences.getString("username", null) != null
                && sharedPreferences.getString("password", null) != null) {
            myApplication.setUserId(sharedPreferences.getInt("userid", -1));
            myApplication.setUserName(sharedPreferences.getString("username", null));
            myApplication.setPassWord(sharedPreferences.getString("password", null));
        }

        dbHelper = new DbHelper(this, null, null, 1);


    }

    /**
     * 初始化视图
     */
    private void initView() {
        gvCategory = findViewById(R.id.gv_category);
        ivWeather = findViewById(R.id.iv_wheather);
        ivCar = findViewById(R.id.iv_car);
        ivMine = findViewById(R.id.iv_mine);
        etSearch = findViewById(R.id.et_search);
        ivSearch = findViewById(R.id.iv_search);

        ivWeather.setOnClickListener(this);
        ivCar.setOnClickListener(this);
        ivMine.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        /**
         * 创建主视图下的四个分类
         */

        initCateGory();
    }

    /**
     * 初始化分类
     */
    private void initCateGory() {
        List<Map<String, Object>> data_list = new ArrayList<Map<String, Object>>();

        int[] icon = {R.drawable.pic1, R.drawable.pic2,
                R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.pic6};

        for (int i = 0; i < icon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            data_list.add(map);
        }

        String[] from = {"image"};
        int[] to = {R.id.iv_category};
        final Intent intent = new Intent(MainActivity.this, GoodsInfoListActivity.class);
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data_list, R.layout.item_category, from, to);

        gvCategory.setAdapter(simpleAdapter);

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                        case 0:
                        intent.putExtra("category", "食品区");
                        intent.putExtra("issearch", "false");
                        startActivity(intent);
                        break;
                    case 1:
                        intent.putExtra("category", "妇婴区");
                        intent.putExtra("issearch", "false");
                        startActivity(intent);
                        break;
                    case 2:
                        intent.putExtra("category", "护肤品");
                        intent.putExtra("issearch", "false");

                        startActivity(intent);
                        break;
                    case 3:
                        intent.putExtra("category", "纺织区");
                        intent.putExtra("issearch", "false");

                        startActivity(intent);
                        break;
                    case 4:
                        intent.putExtra("category", "零食区");
                        intent.putExtra("issearch", "false");

                        startActivity(intent);
                        break;
                    case 5:
                        intent.putExtra("category", "日用品");
                        intent.putExtra("issearch", "false");
                        startActivity(intent);
                        break;
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_wheather:
                Intent intent = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_car:
                Intent intent1 = new Intent(MainActivity.this, CarActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_mine:
                Intent intent2 = new Intent(MainActivity.this, MineActivity.class);
                startActivity(intent2);
                break;
            case R.id.gv_category:
                Intent intent3 = new Intent(MainActivity.this, GoodsInfoListActivity.class);
                startActivity(intent3);
                break;

            case R.id.iv_search:
                String str = etSearch.getText().toString();
                Intent intent4 = new Intent(MainActivity.this, GoodsInfoListActivity.class);
                if(TextUtils.isEmpty(str)){
                    intent4.putExtra("searchstr", "");
                }else {
                    intent4.putExtra("searchstr", str);
                }
                intent4.putExtra("category", "");
                intent4.putExtra("issearch", "true");
                startActivity(intent4);
                break;
        }
    }
}
