package com.shoppingapp.activities;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppingapp.R;
import com.shoppingapp.adapters.CarListAdapter;
import com.shoppingapp.beans.CarBean;
import com.shoppingapp.beans.OrderBean;
import com.shoppingapp.beans.OrderDetailBean;
import com.shoppingapp.db.DbHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CarActivity extends Activity implements View.OnClickListener, CarListAdapter.DeleteCallback
        , CarListAdapter.CheckCallBack {
    private static final String TAG = CarActivity.class.getSimpleName();
    private ImageView ivBack;
    private Button btnEdit;
    private Button btnPay;
    private CheckBox cbAllCheck;
    private ListView lvCar;
    private ImageView ivEmptyCar;
    private TextView tvAllPrice;
    private DbHelper dbHelper;
    private List<CarBean> carBeanList;
    private int allPrice = 0;
    private CarListAdapter carListAdapter;

    private OrderBean orderBean;
    private List<OrderDetailBean> orderDetailBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        dbHelper = new DbHelper(this, null, null, 1);
        initView();
        initData();


        cbAllCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                OrderDetailBean orderDetailBean = new OrderDetailBean();
                if (isChecked) {
                    for (int i = 0; i < carBeanList.size(); i++) {
                        carBeanList.get(i).setChecked("true");
                        dbHelper.updateCar(carBeanList.get(i));
                        orderDetailBean.setGoodsPrice(carBeanList.get(i).getPrice() * carBeanList.get(i).getQuantity());
                        orderDetailBean.setGoodsName(carBeanList.get(i).getGoodsname());
                        orderDetailBean.setGoodsId(carBeanList.get(i).getGoodsid());
                        orderDetailBeanList.add(orderDetailBean);
                    }

                    tvAllPrice.setText("合计：" + carListAdapter.getPrice());
                    btnPay.setText("结算（" + carListAdapter.getPrice() + ")");
                    carListAdapter.notifyDataSetChanged();
                    lvCar.setAdapter(carListAdapter);

                } else {
                    for (int i = 0; i < carBeanList.size(); i++) {
                        carBeanList.get(i).setChecked("false");
                        dbHelper.updateCar(carBeanList.get(i));
                        orderDetailBean.setGoodsPrice(carBeanList.get(i).getPrice() * carBeanList.get(i).getQuantity());
                        orderDetailBean.setGoodsName(carBeanList.get(i).getGoodsname());
                       orderDetailBean.setGoodsId(carBeanList.get(i).getGoodsid());
                        orderDetailBeanList.add(orderDetailBean);
                    }
                    tvAllPrice.setText("合计：0");
                    btnPay.setText("结算（0）");
                    orderDetailBeanList.clear();
                    carListAdapter.notifyDataSetChanged();
                    lvCar.setAdapter(carListAdapter);
                }
            }
        });
    }

    private void initData() {
        carBeanList = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        int userid = sharedPreferences.getInt("userid", -1);
        carBeanList = dbHelper.getAllCarGoods(userid);


        if (carBeanList.size() != 0 && carBeanList != null) {
            carListAdapter = new CarListAdapter(CarActivity.this, carBeanList, false);
            carListAdapter.setDeleteCallback(this);
            carListAdapter.setCheckCallBack(this);
           // carListAdapter.setUnCheckCallBack(this);
            for (int i = 0; i < carListAdapter.getCount(); i++) {
                Log.e(TAG, "Adapter" + carListAdapter.getItem(i).toString());
            }

            allPrice = carListAdapter.getPrice();

            tvAllPrice.setText("合计：" + allPrice);
            btnPay.setText("结算（" + allPrice + ")");

            lvCar.setAdapter(carListAdapter);
        } else {
            lvCar.setVisibility(View.GONE);
            ivEmptyCar.setVisibility(View.VISIBLE);

            tvAllPrice.setText("合计：0");
            btnPay.setText("结算（0）");
        }
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        btnEdit = findViewById(R.id.btn_edit);
        btnPay = findViewById(R.id.btn_pay);
        cbAllCheck = findViewById(R.id.cb_all_check);
        lvCar = findViewById(R.id.lv_car);
        tvAllPrice = findViewById(R.id.tv_allprice);
        ivEmptyCar = findViewById(R.id.iv_emptycar);

        ivBack.setOnClickListener(this);
        btnEdit.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_edit:
                editCar();
                break;
            case R.id.btn_pay:
                if(carBeanList.size()!=0){
                    pay();
                }else {
                    Toast.makeText(this, "购物车为空", Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }

    private void pay() {
        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        int userid = sharedPreferences.getInt("userid", -1);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        String date = year + "-" + (month + 1) + "-" + day + " " + hour + ":" + minute + ":" + second;

        allPrice = carListAdapter.getPrice();

        int num = dbHelper.getAllOrder(userid).size();
        orderBean = new OrderBean(num + 1, userid, date, allPrice);


        for (int i = 0; i < carBeanList.size(); i++) {
            if (carBeanList.get(i).getChecked().equals("true")) {
                OrderDetailBean orderDetailBean = new OrderDetailBean();
                orderDetailBean.setGoodsId(carBeanList.get(i).getGoodsid());
                orderDetailBean.setGoodsName(carBeanList.get(i).getGoodsname());
                orderDetailBean.setGoodsPrice(carBeanList.get(i).getPrice() * carBeanList.get(i).getQuantity());
                orderDetailBean.setOderFid(num + 1);
                orderDetailBeanList.add(orderDetailBean);

                dbHelper.deleteCar(carBeanList.get(i).getCarid());

            }
        }
        dbHelper.addOrder(orderBean, orderDetailBeanList);
        carBeanList = dbHelper.getAllCarGoods(userid);
        orderDetailBeanList.clear();
        lvCar.setVisibility(View.GONE);
        ivEmptyCar.setVisibility(View.VISIBLE);
        carListAdapter.notifyDataSetChanged();
        initData();
    }


    private void editCar() {
        if (btnEdit.getText().toString().equals("编辑")) {
            carListAdapter.editeable(true);

            carListAdapter.notifyDataSetChanged();
            lvCar.setAdapter(carListAdapter);
            btnEdit.setText("完成");
        } else {
            for (int i = 0; i < carListAdapter.getCount(); i++) {
                dbHelper.updateCar((CarBean) carListAdapter.getItem(i));
            }

            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            int userid = sharedPreferences.getInt("userid", -1);
            carBeanList = dbHelper.getAllCarGoods(userid);
            carListAdapter.editeable(false);
            carListAdapter.notifyDataSetChanged();

            lvCar.setAdapter(carListAdapter);
            btnEdit.setText("编辑");
        }
    }

    @Override
    public void deletePosition(int saveposition) {
        allPrice -= carBeanList.get(saveposition).getPrice() * carBeanList.get(saveposition).getQuantity();

        Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
        int i = saveposition;
        Log.e(TAG, "deletePosition: " + saveposition + " CarBeasize" + carBeanList.size());
       if (carBeanList.size() == 1) {
           dbHelper.deleteCar(carBeanList.get(i).getCarid());

       } else {
        if (carBeanList.size() != 0) {
            carBeanList.remove(i);

        } else {
            lvCar.setVisibility(View.GONE);
            ivEmptyCar.setVisibility(View.VISIBLE);
        }

           dbHelper.deleteCar(carBeanList.get(i).getCarid());
           carListAdapter.setData(carBeanList);
            carListAdapter.editeable(true);
        allPrice = carListAdapter.getPrice();
        carListAdapter.notifyDataSetChanged();
        }
        tvAllPrice.setText("合计：" + allPrice);
        btnPay.setText("结算（" + allPrice + ")");
    }

    @Override
    public void checkPosition(int saveposition) {
        allPrice = carListAdapter.getPrice();
        tvAllPrice.setText("合计：" + allPrice);
        btnPay.setText("结算（" + allPrice + ")");
        carListAdapter.notifyDataSetChanged();
        Log.e(TAG, "checkPosition: =================" + allPrice);

    }

}
