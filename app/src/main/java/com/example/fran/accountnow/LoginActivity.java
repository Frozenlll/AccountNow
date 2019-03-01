package com.example.fran.accountnow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private TextView account;
    private EditText password;

    private SharedPreferences setting;
    private String account_s;
    private String password_s;
    private String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AndPermission.with(getParent()).permission(Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE)
                .onGranted(
                        new Action() {
                            @Override
                            public void onAction(List<String> permissions) {

                            }
                        }
                )
                .onDenied(
                        new Action() {
                            @Override
                            public void onAction(List<String> permissions) {

                            }
                        }
                );

        setContentView(R.layout.activity_login);
        setTitle(R.string.login);

        //获取登录状态
        setting = getSharedPreferences("settings", MODE_PRIVATE);
        pwd = setting.getString("pwd", null);
        if (pwd == null)
            Signup();
        account_s = setting.getString("account", "");
        password_s = setting.getString("password", "");
        pwd = setting.getString("pwd", "");
        if (setting.getBoolean("autologin", false))
            Login();

        account = (TextView) findViewById(R.id.account);
        password = (EditText) findViewById(R.id.password);
        Button login = (Button) findViewById(R.id.login);

        account.setText(account_s);

        login.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        account_s = account.getText().toString();
                        password_s = password.getText().toString();
                        Login();
                    }
                }
        );
    }

    public void Login() {
        if (password_s.equals(pwd)) {
            setting.edit().putString("account", account_s).commit();
            setting.edit().putString("password", password_s).commit();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        } else
            Toast.makeText(getApplicationContext(), "密码错误", Toast.LENGTH_SHORT).show();
    }

    public void Signup() {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        finish();
    }
}
