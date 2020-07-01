package com.shoppingapp.activities;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.beans.CarBean;
import com.shoppingapp.beans.GoodsBean;
import com.shoppingapp.db.DbHelper;

public class GoodsDetileActivity extends Activity implements View.OnClickListener {

    private GoodsBean goodsBean;
    private ImageView ivBack;
    private ImageView ivGoods;
    private TextView tvTitle;
    private TextView tvPrice;
    private TextView tvType;
    private TextView tvIntroduce;
    private Button btnSub;
    private Button btnAdd;
    private TextView tvNum;
    private Button btnBuy;
    private Button btnAddToCar;
    private DbHelper dbHelper;

    private int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detile);
        dbHelper = new DbHelper(this, null, null, 1);
        goodsBean = (GoodsBean) getIntent().getSerializableExtra("goodsdetail");
        initView();
    }

    private void initView() {

        ivBack = findViewById(R.id.iv_back);
        ivGoods = findViewById(R.id.iv_detile_goods);
        tvTitle = findViewById(R.id.tv_detile_title);
        tvPrice = findViewById(R.id.tv_detile_price);
        tvType = findViewById(R.id.tv_detile_category);
        tvIntroduce = findViewById(R.id.tv_detile_introduce);
        btnSub = findViewById(R.id.btn_detile_sub);
        btnAdd = findViewById(R.id.btn_detile_add);
        tvNum = findViewById(R.id.tv_detile_num);
        btnBuy = findViewById(R.id.btn_buy_now);
        btnAddToCar = findViewById(R.id.btn_addtocar);

        String photo = goodsBean.getPhoto();
        String title = goodsBean.getTitle();
        String type = goodsBean.getType();
        String introduce = goodsBean.getIntroduce();
        int price = goodsBean.getPrice();

        Glide.with(this).load(photo).into(ivGoods);
        tvTitle.setText(title);
        tvPrice.setText("￥" + price);
        tvType.setText(type);
        tvIntroduce.setText(introduce);

        ivBack.setOnClickListener(this);
        btnSub.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        btnBuy.setOnClickListener(this);
        btnAddToCar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_detile_add:
                num += 1;
                tvNum.setText(num + "");
                break;
            case R.id.btn_detile_sub:
                if (num > 1)
                    num -= 1;
                break;
            case R.id.btn_buy_now:
                Toast.makeText(this, "请先加入购物车", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_addtocar:
                SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                if (sharedPreferences.getString("username", null) != null
                        && sharedPreferences.getString("password", null) != null) {
                    MyApplication application = (MyApplication) getApplication();
                    int userid = application.getUserId();
                    int goodsid = goodsBean.getGoodsId();
                    String goodsname = goodsBean.getTitle();
                    String photo = goodsBean.getPhoto();
                    int price = goodsBean.getPrice();
                    int quantity = num;
                    String checked = "false";
                    CarBean carBean = new CarBean(userid, goodsid, goodsname, photo, price, quantity, checked);
                    dbHelper.addCar(carBean);
                    Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
