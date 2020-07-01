package com.shoppingapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.adapters.GoodsListAdapter;
import com.shoppingapp.beans.GoodsBean;
import com.shoppingapp.db.DbHelper;

import java.io.Serializable;
import java.util.List;

public class GoodsInfoListActivity extends Activity {
    private String TAG = GoodsInfoListActivity.class.getSimpleName();
    private ListView lvGoodInfo;
    private List<GoodsBean> goodsBeanList;
    private ImageView ivNull;
    private ImageView ivBack;
    private DbHelper dbHelper;
    private String issearch;
    private String searchstr;
    private String category;
    private MyApplication myApplication;
    private EditText etSearch;
    private ImageView ivSearch;

    private GoodsListAdapter goodsListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info_list);
        initView();
        Intent intent = getIntent();
        issearch = intent.getStringExtra("issearch");
        searchstr = intent.getStringExtra("searchstr");
        category = intent.getStringExtra("category");

        dbHelper = new DbHelper(this, null, null, 1);
        myApplication = (MyApplication) getApplication();


        if(category.equals("")){
            if(searchstr.equals("")){
                goodsBeanList = dbHelper.getAllGoods();
            }else {
                goodsBeanList = dbHelper.searchGoods(searchstr);
            }
        }else{
            goodsBeanList = dbHelper.getCategoryGoods(category);
        }


        if(goodsBeanList == null || goodsBeanList.size() == 0){
            ivNull.setVisibility(View.VISIBLE);
            lvGoodInfo.setVisibility(View.GONE);
        }else{
            goodsListAdapter = new GoodsListAdapter(this,goodsBeanList,0);
            lvGoodInfo.setAdapter(goodsListAdapter);

            lvGoodInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(GoodsInfoListActivity.this,GoodsDetileActivity.class);
                    intent.putExtra("goodsdetail",goodsBeanList.get(position));

                    dbHelper.addFoot(myApplication.getUserName(), goodsBeanList.get(position));

                    startActivity(intent);
                }
            });

        }


        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = etSearch.getText().toString();
                if(TextUtils.isEmpty(str)){
                    Toast.makeText(GoodsInfoListActivity.this,"请输入要查找的商品",Toast.LENGTH_SHORT).show();

                }else {
                    goodsBeanList = dbHelper.searchGoods(str);
                    if(goodsBeanList == null || goodsBeanList.size() == 0){
                        ivNull.setVisibility(View.VISIBLE);
                        lvGoodInfo.setVisibility(View.GONE);
                    }else{
                        goodsListAdapter = new GoodsListAdapter(GoodsInfoListActivity.this,goodsBeanList,0);
                        goodsListAdapter.notifyDataSetChanged();
                        lvGoodInfo.setAdapter(goodsListAdapter);
                    }
                }
            }
        });

    }

    private void initView() {
        lvGoodInfo = findViewById(R.id.lv_goods);
        ivBack = findViewById(R.id.iv_back);
        etSearch = findViewById(R.id.et_search);
        ivSearch = findViewById(R.id.iv_search);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivNull = findViewById(R.id.iv_searchnull);


    }
}
