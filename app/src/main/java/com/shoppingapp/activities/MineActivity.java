package com.shoppingapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.beans.UserInfoBean;
import com.shoppingapp.db.DbHelper;

import java.io.Serializable;

public class MineActivity extends Activity implements View.OnClickListener {

    private RelativeLayout rlLogin;
    private RelativeLayout rlMyInfo;
    private RelativeLayout rlMyOder;
    private RelativeLayout rlMyFoot;
    private ImageView ivBack;
    private TextView tvUserName;
    private TextView tvExitLogin;

    private UserInfoBean userInfoBean;
    private DbHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);
        dbHelper = new DbHelper(this, null, null, 1);
        initView();
    }

    private void initView() {
        rlLogin = findViewById(R.id.rl_login);
        rlMyInfo = findViewById(R.id.rl_myinfo);
        rlMyOder = findViewById(R.id.rl_myoder);
        rlMyFoot = findViewById(R.id.rl_myfoot);
        ivBack = findViewById(R.id.iv_back);
        tvUserName = findViewById(R.id.tv_username);
        tvExitLogin = findViewById(R.id.tv_exit);

        rlLogin.setOnClickListener(this);
        rlMyInfo.setOnClickListener(this);
        rlMyOder.setOnClickListener(this);
        rlMyFoot.setOnClickListener(this);
        ivBack.setOnClickListener(this);
/**绑定监听器*/

        SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        MyApplication myApplication = (MyApplication) this.getApplication();

        if (sharedPreferences.getString("username", null) != null
                && sharedPreferences.getString("password", null) != null) {

            myApplication.setUserId(sharedPreferences.getInt("userid", -1));
            myApplication.setUserName(sharedPreferences.getString("username", null));
            myApplication.setPassWord(sharedPreferences.getString("password", null));
            tvExitLogin.setText("退出登录");
            rlMyFoot.setVisibility(View.VISIBLE);
            rlMyInfo.setVisibility(View.VISIBLE);
            rlMyOder.setVisibility(View.VISIBLE);
            tvUserName.setText(sharedPreferences.getString("username", null));
        }
    }
/**为空则退出*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_login:
                String type = tvExitLogin.getText().toString();
                if (type.equals("登录")) {
                    Intent intent = new Intent(MineActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
                    sharedPreferences.edit().remove("userid").remove("password").remove("username").apply();
                    tvExitLogin.setText("登录");
                    rlMyFoot.setVisibility(View.GONE);
                    rlMyInfo.setVisibility(View.GONE);
                    rlMyOder.setVisibility(View.GONE);
                    tvUserName.setText("未登录");
                }
                break;

            case R.id.rl_myinfo:
                userInfoBean = dbHelper.isUserExit(tvUserName.getText().toString());
                Intent intent = new Intent(MineActivity.this, UserInfoActivity.class);
                intent.putExtra("userinfo", (Serializable) userInfoBean);
                startActivity(intent);
                break;
            case R.id.rl_myoder:
                Intent intent1 = new Intent(MineActivity.this, MyOrderActivity.class);
                startActivity(intent1);
                break;
            case R.id.rl_myfoot:
                Intent intent2 = new Intent(MineActivity.this, MyFootActivity.class);
                startActivity(intent2);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
