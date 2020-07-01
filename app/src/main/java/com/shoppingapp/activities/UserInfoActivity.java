package com.shoppingapp.activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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

public class UserInfoActivity extends Activity implements View.OnClickListener {
    private ImageView ivBack;
    private Button btnUpdate;
    private TextView tvToHome;
    private EditText etUserName;
    private EditText etPsd;
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

    private UserInfoBean userInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        dbHelper = new DbHelper(this, null, null, 1);
        initView();
    }

    private void initView() {
        ivBack = findViewById(R.id.iv_back);
        btnUpdate = findViewById(R.id.btn_update);
        tvToHome = findViewById(R.id.tv_tohome);
        etUserName = findViewById(R.id.et_username);
        etPsd = findViewById(R.id.et_psd);
        rgSex = findViewById(R.id.rg_sex);
        rbM = findViewById(R.id.rb_m);
        rbF = findViewById(R.id.rb_f);
        etAge = findViewById(R.id.et_age);
        etPhone = findViewById(R.id.et_phone);
        etAddress = findViewById(R.id.et_address);

        Intent intent = getIntent();
        userInfoBean = (UserInfoBean) intent.getSerializableExtra("userinfo");

        etUserName.setText(userInfoBean.getUsername());
        etPsd.setText(userInfoBean.getPassword());
        etAddress.setText(userInfoBean.getAddress());
        etAge.setText(userInfoBean.getAge());
        etPhone.setText(userInfoBean.getPhone());

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
        btnUpdate.setOnClickListener(this);
        tvToHome.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.tv_tohome:
                finish();
                break;

        }
    }
/**update更新用户信息*/
    private void update() {
        userName = etUserName.getText().toString();
        password = etPsd.getText().toString();
        age = etAge.getText().toString();
        phone = etPhone.getText().toString();
        address = etAddress.getText().toString();

        UserInfoBean userInfoBean = new UserInfoBean(userName, password, sex, age, phone, address);

        dbHelper.updateUserInfo(userInfoBean);

        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }
}
