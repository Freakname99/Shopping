package com.shoppingapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.shoppingapp.R;
import com.shoppingapp.beans.UserInfoBean;
import com.shoppingapp.db.DbHelper;

public class RegisterActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private Button btnReg;
    private TextView tvToLogin;
    private EditText etUserName;
    private EditText etPsd;
    private EditText etRePsd;
    private RadioGroup rgSex;
    private RadioButton rbM;
    private RadioButton rbF;
    private EditText etAge;
    private EditText etPhone;
    private EditText etAddress;

    private String userName;
    private String password;
    private String repassword;
    private String sex = "男";
    private String age;
    private String phone;
    private String address;

    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbHelper = new DbHelper(this, null, null, 1);
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        btnReg = findViewById(R.id.btn_reg);
        tvToLogin = findViewById(R.id.tv_tologin);
        etUserName = findViewById(R.id.et_username);
        etPsd = findViewById(R.id.et_psd);
        etRePsd = findViewById(R.id.et_re_psd);
        rgSex = findViewById(R.id.rg_sex);
        rbM = findViewById(R.id.rb_m);
        rbF = findViewById(R.id.rb_f);
        etAge = findViewById(R.id.et_age);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);

        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_m:
                        sex = "男";
                        break;
                    case R.id.rb_f:
                        sex = "女";
                        break;
                }
            }
        });

        ivBack.setOnClickListener(this);
        btnReg.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_reg:
                register();
                break;
            case R.id.tv_tologin:
                finish();
                break;

        }
    }

    private void register() {
        userName = etUserName.getText().toString();
        password = etPsd.getText().toString();
        repassword = etRePsd.getText().toString();
        age = etAge.getText().toString();
        phone = etPhone.getText().toString();
        address = etAddress.getText().toString();
/**register注册信息时的方法*/

        UserInfoBean userInfoBean = new UserInfoBean(userName, password, sex, age, phone, address);

        if (!password.equals(repassword)) {
            Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(userName)
                || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(repassword)
                || TextUtils.isEmpty(age)
                || TextUtils.isEmpty(phone)
                || TextUtils.isEmpty(address)) {
            Toast.makeText(this, "请输入完整信息", Toast.LENGTH_SHORT).show();
        } else if (dbHelper.isUserExit(userName) != null || dbHelper.isUserExit(userName, password) != null) {
            Toast.makeText(this, "用户已存在", Toast.LENGTH_SHORT).show();
        } else {
            dbHelper.registerUser(userInfoBean);
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }


    }
}
