package com.shoppingapp.activities;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.adapters.OrderListAdapter;
import com.shoppingapp.beans.OrderBean;
import com.shoppingapp.beans.OrderDetailBean;
import com.shoppingapp.db.DbHelper;

import java.util.List;

public class MyOrderActivity extends AppCompatActivity {

    private ImageView ivBack;
    private ListView lvOrder;
    private OrderListAdapter orderListAdapter;
    private List<OrderBean> orderBeanList;
    private List<OrderDetailBean> orderDetailBeanList;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        MyApplication myApplication = (MyApplication) getApplication();
        dbHelper = new DbHelper(this, null, null, 1);
        ivBack = findViewById(R.id.iv_back);
        lvOrder = findViewById(R.id.lv_order);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderBeanList = dbHelper.getAllOrder(myApplication.getUserId());

        if (orderBeanList.size() != 0 && orderBeanList != null){
            orderListAdapter = new OrderListAdapter(this, orderBeanList);
            lvOrder.setAdapter(orderListAdapter);
        }else {
            lvOrder.setVisibility(View.GONE);
            Toast.makeText(this,"您还没有创建订单",Toast.LENGTH_SHORT).show();
        }

        lvOrder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showAlterDialog(position);
            }
        });


    }


    private void showAlterDialog(final int position){
        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(MyOrderActivity.this);
        alterDiaglog.setMessage("确认删除吗");
        alterDiaglog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(orderBeanList.size()!=0){
                    dbHelper.deleteOrder(orderBeanList.get(position).getOrderId());
                    orderBeanList.remove(position);
                    orderListAdapter.notifyDataSetChanged();
                    Toast.makeText(MyOrderActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MyOrderActivity.this,"没有订单",Toast.LENGTH_SHORT).show();

                }
            }
        });
        alterDiaglog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alterDiaglog.show();
    }

}
