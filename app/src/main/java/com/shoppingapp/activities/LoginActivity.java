package com.shoppingapp.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppingapp.MyApplication;
import com.shoppingapp.R;
import com.shoppingapp.beans.UserInfoBean;
import com.shoppingapp.db.DbHelper;

public class LoginActivity extends Activity implements View.OnClickListener {

    private ImageView ivBack;
    private Button btnLogin;
    private EditText etUserName;
    private EditText etPassword;
    private TextView tvToReg;
    private DbHelper dbHelper;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new DbHelper(this, null, null, 1);
        myApplication = (MyApplication) getApplication();
        initVeiw();

    }

    private void initVeiw() {
        ivBack = findViewById(R.id.iv_back);
        btnLogin = findViewById(R.id.btn_login);
        etUserName = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_psd);
        tvToReg = findViewById(R.id.tv_toregister);

        ivBack.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        tvToReg.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_toregister:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void login() {
        String userName = etUserName.getText().toString();
        String password = etPassword.getText().toString();

        if(TextUtils.isEmpty(userName) || TextUtils.isEmpty(userName)){
            Toast.makeText(this,"请输入用户名及密码",Toast.LENGTH_SHORT).show();
        }

        UserInfoBean userInfoBean = dbHelper.isUserExit(userName, password);

        if (userInfoBean != null) {
            Intent intent = new Intent(LoginActivity.this,MineActivity.class);
            SharedPreferences sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            sharedPreferences.edit()
                    .putInt("userid", userInfoBean.getUserId())
                    .putString("username", userName)
                    .putString("password", password)
                    .apply();
           myApplication.setUserId(userInfoBean.getUserId());
           myApplication.setPassWord(password);
           myApplication.setUserName(userName);
            startActivity(intent);
            finish();
            Toast.makeText(this,"登录成功！",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"用户名不存在或密码错误",Toast.LENGTH_SHORT).show();
        }

    }
}
