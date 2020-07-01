package com.shoppingapp.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.adapters.GoodsListAdapter;
import com.shoppingapp.beans.GoodsBean;
import com.shoppingapp.db.DbHelper;

import java.util.ArrayList;
import java.util.List;

public class MyFootActivity extends Activity {
    private ImageView ivBack;
    private ListView lvFoot;
    private DbHelper dbHelper;
    private MyApplication myApplication;
    List<GoodsBean> goodsBeanList = new ArrayList<>();
    private GoodsListAdapter footListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_foot);

        ivBack = findViewById(R.id.iv_back);
        lvFoot = findViewById(R.id.lv_foot);

        dbHelper = new DbHelper(this, null, null, 1);
        myApplication = (MyApplication) getApplication();

        goodsBeanList = dbHelper.getFoot(myApplication.getUserName());
/**get方法*/
        if(goodsBeanList.size()!=0&&goodsBeanList !=null){
            footListAdapter = new GoodsListAdapter(this,goodsBeanList,1);
            lvFoot.setAdapter(footListAdapter);
        }else {
            Toast.makeText(MyFootActivity.this,"足迹为空",Toast.LENGTH_SHORT).show();

        }




        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        lvFoot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,  int position, long id) {
                showAlterDialog(position);
            }
        });

    }

    private void showAlterDialog(final  int position){
                        final AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(MyFootActivity.this);
                        alterDiaglog.setMessage("确认删除吗");
                        alterDiaglog.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(goodsBeanList.size()!=0){
                                    dbHelper.deleteFoot(myApplication.getUserName(),goodsBeanList.get(position).getGoodsId());
                                    goodsBeanList.remove(position);
                                    footListAdapter.notifyDataSetChanged();
                                    Toast.makeText(MyFootActivity.this,"删除成功",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(MyFootActivity.this,"没有足迹",Toast.LENGTH_SHORT).show();
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
